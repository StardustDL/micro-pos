package org.micropos.products.controller;

import java.util.List;

import org.micropos.products.exception.ProductNotFoundException;
import org.micropos.products.job.Jobs;
import org.micropos.products.model.Product;
import org.micropos.products.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class ProductsController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Jobs jobs;

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> all() {
        return repository.all();
    }

    @GetMapping("/full")
    public Mono<List<String>> allFull() {
        return repository.all().collectList();
    }

    @GetMapping("/data/{name}")
    public Mono<String> importData(@PathVariable String name) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("file:data/" + name + ".json.gz");
        if (resource.exists()) {
            try {
                jobLauncher.run(jobs.importProducts(resource.getFile()), new JobParameters());
            } catch (Exception e) {
                return Mono.error(e);
            }
        }
        try {
            return Mono.just(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @GetMapping("/{id}")
    public Mono<Product> get(@PathVariable String id) throws ProductNotFoundException {
        return repository.get(id).switchIfEmpty(Mono.error(new ProductNotFoundException()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return repository.remove(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Mono<String> create(@RequestBody Product item) {
        return repository.create(item);
    }

    @PutMapping(value = "/{id}")
    public Mono<Void> update(@PathVariable("id") String id, @RequestBody Product item) {
        return repository.update(item.withId(id));
    }
}
