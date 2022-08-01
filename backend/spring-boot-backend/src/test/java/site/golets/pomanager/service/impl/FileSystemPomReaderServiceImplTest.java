package site.golets.pomanager.service.impl;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class FileSystemPomReaderServiceImplTest {

    @Autowired
    private FileSystemPomReaderServiceImpl pomReaderService;

    @Test
    public void readPomFileTest() {
        Optional<Model> model = pomReaderService.readPomModel("pom.xml");

        if (model.isPresent()) {
            model.get().getProperties().entrySet().forEach(System.out::println);
        } else {
            System.out.println("Pom was not found");
        }
    }

}
