package ru.cnpo.akvs3;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.cnpo.akvs3.service.ParserService;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class JavaParserApplication implements CommandLineRunner {

    @Autowired
    ParserService parserService;

    private static Logger LOG = LoggerFactory.getLogger(JavaParserApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(JavaParserApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("parserService.getSrcDir(): {}", parserService.getSrcDir());
        try {
            parserService.parseDirectory();
        } catch (IOException ex) {
            log.error("Error in parse file: {}", ex.getMessage());
        }
    }
}
