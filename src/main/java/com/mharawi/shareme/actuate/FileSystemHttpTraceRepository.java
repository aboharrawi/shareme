package com.mharawi.shareme.actuate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FileSystemHttpTraceRepository implements HttpTraceRepository {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private final File destination;

    private final Predicate<HttpTrace> filter;

    private final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

    public FileSystemHttpTraceRepository(File destination) {
        this(httpTrace -> true, destination);
    }

    public FileSystemHttpTraceRepository(Predicate<HttpTrace> filter, File destination) {
        this.filter = filter;
        this.destination = destination;
    }


    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {
        if (filter.test(trace)) {
            try (Writer writer = getWriter(trace.getTimestamp().toString() + ".msg")) {
                writer.write(this.objectMapper.writeValueAsString(trace));
            } catch (IOException e) {
                //
            }
        }
    }

    private Writer getWriter(String fileName) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.destination.toPath().resolve(fileName).toFile()), Charset.defaultCharset()),
                DEFAULT_BUFFER_SIZE);
    }
}
