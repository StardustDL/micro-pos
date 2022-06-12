package org.micropos.products.job;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.micropos.products.db.ProductDb;
import org.micropos.products.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Jobs {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    ProductDb productDb;

    ObjectMapper mapper = new ObjectMapper();

    public Tasklet decompressTasklet(File file) {
        return new GzipDecompressTasklet().setResource(new FileSystemResource(file));
    }

    public Step decompressStep(File file) {
        return stepBuilderFactory.get("decompress")
                .tasklet(decompressTasklet(file))
                .allowStartIfComplete(true)
                .build();
    }

    public ItemReader<JsonNode> jsonReader(File file) {
        return new FlatFileItemReaderBuilder<JsonNode>().lineMapper((line, n) -> {
            return mapper.readTree(line);
        }).name("jsonReader").resource(new FileSystemResource(file)).build();
    }

    public ItemProcessor<JsonNode, Product> jsonProcesser() {
        Random rand = new Random();
        return new ItemProcessor<JsonNode, Product>() {
            @Override
            public Product process(JsonNode item) throws Exception {
                Product result = new Product();
                result.setId(UUID.randomUUID().toString());
                if (item.hasNonNull("price")) {
                    double price = item.get("price").asDouble();
                    if (price == 0.0) {
                        price = rand.nextDouble() * 10;
                    }
                    result.setPrice(price);
                }
                if (item.hasNonNull("title")) {
                    result.setName(item.get("title").asText());
                }
                if (item.hasNonNull("description")) {
                    JsonNode sub = item.get("description");
                    if (sub.isArray()) {
                        String description = "";
                        for (JsonNode node : sub) {
                            description += node.asText();
                        }
                        result.setDescription(description);
                    }
                }
                if (item.hasNonNull("imageUrl")) {
                    JsonNode sub = item.get("imageUrl");
                    if (sub.isArray()) {
                        if (sub.hasNonNull(0)) {
                            result.setImage(sub.get(0).asText());
                        }
                    }
                }
                return result;
            }
        };
    }

    public ItemWriter<Product> productWriter() {
        return new ItemWriter<Product>() {
            @Override
            public void write(List<? extends Product> items) throws Exception {
                productDb.saveAll(items).then().block();
            }
        };
    }

    public Step processProducts(File file) {
        return stepBuilderFactory.get("processProducts")
                .<JsonNode, Product>chunk(100)
                .reader(jsonReader(file))
                .processor(jsonProcesser())
                .writer(productWriter())
                .allowStartIfComplete(true)
                .build();
    }

    public Job importProducts(File file) {
        return jobBuilderFactory.get("importProducts")
                .start(decompressStep(file))
                .next(processProducts(new File(file.getParentFile(), file.getName().replace(".gz", ""))))
                .build();
    }
}

@Component
class JobConfig extends DefaultBatchConfigurer {

    @Override
    public JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

}