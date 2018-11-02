package ru.cnpo.akvs3.service;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.utils.SourceRoot;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class ParserService {

    @Value("${srcDir}")
    String srcDir;
    @Value("${xmlDir}")
    String xmlDir;

    public void parseDirectory() throws IOException {
        // Parse all source files
        SourceRoot sourceRoot = new SourceRoot(Paths.get(getSrcDir()));
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_8);
        sourceRoot.setParserConfiguration(parserConfiguration);

        List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse("");

        List<CompilationUnit> allCus = parseResults.stream()
                .filter(ParseResult::isSuccessful)
                .map(r -> r.getResult().get())
                .collect(Collectors.toList());

        AtomicInteger runCount = new AtomicInteger();
        allCus.stream()
                .forEach(cu -> saveFile(cu, runCount.getAndIncrement()));
    }

    private void saveFile(CompilationUnit cu, int count) {
        XmlPrinter xmlPrinter = new XmlPrinter(true);
        String content = xmlPrinter.output(cu);
        log.info("content: ", content);
        String xmlFile = new StringBuilder(getXmlDir())
                .append(count).append(".xml").toString();
        try {
            Files.write(Paths.get(xmlFile), content.getBytes());
        } catch (IOException ex) {
            log.error("Error in save file: {}", ex.getMessage());
        }
        log.info("{} file saved", xmlFile);
    }

}
