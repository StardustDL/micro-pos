package org.micropos.products.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;

public class GzipDecompressTasklet implements Tasklet {

    FileSystemResource resource;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File file = resource.getFile();
        Assert.state(file.isFile(), "File must be a file");

        File decompressedFile = new File(file.getParentFile(), file.getName().replace(".gz", ""));
        if (decompressedFile.exists()) {
            decompressedFile.delete();
        }
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(file));
                FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile)) {
            gzipInputStream.transferTo(fileOutputStream);
        }

        return RepeatStatus.FINISHED;
    }

    public GzipDecompressTasklet setResource(FileSystemResource resource) {
        this.resource = resource;
        return this;
    }

}
