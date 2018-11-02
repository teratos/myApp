package ru.cnpo.akvs3;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.Pair;
import com.github.javaparser.utils.SourceZip;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class JavaParserApplicationTests {

    @Test
    public void parseJar() {
        SourceZip sourceZip = new SourceZip(Paths.get("/opt/echelon/parser/jar/javaparser-0.0.1-SNAPSHOT.jar"));
        try {
            List<Pair<Path, ParseResult<CompilationUnit>>> parse = sourceZip.parse();

            log.info("parse: ", parse);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
