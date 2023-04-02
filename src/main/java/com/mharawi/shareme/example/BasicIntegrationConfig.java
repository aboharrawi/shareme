package com.mharawi.shareme.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;

import java.io.File;

@Configuration
@EnableIntegration
public class BasicIntegrationConfig {
    public String INPUT_DIR = "the_source_dir";
    public String OUTPUT_DIR = "the_dest_dir";
    public String FILE_PATTERN = "*.msg";

    @Bean
    public MessageChannel inFileChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outFileChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "inFileChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource sourceReader = new FileReadingMessageSource();
        sourceReader.setDirectory(new File(INPUT_DIR));
        sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
        return sourceReader;
    }

    @Bean
    @ServiceActivator(inputChannel = "outFileChannel")
    public MessageHandler fileModifyingMessageHandled() {
        return new AbstractReplyProducingMessageHandler() {
            @Override
            protected Object handleRequestMessage(Message<?> requestMessage) {
                MessageHeaders headers = requestMessage.getHeaders();
                if (headers.containsKey("file_originalFile")) {
                    ((File) headers.get("file_originalFile")).delete();
                }
                return null;
            }
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "inFileChannel")
    public MessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
        handler.setExpectReply(true);
        handler.setOutputChannelName("outFileChannel");
        return handler;
    }
}