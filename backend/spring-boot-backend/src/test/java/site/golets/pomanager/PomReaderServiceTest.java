package site.golets.pomanager;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.golets.pomanager.service.PomReaderService;

import java.util.Optional;

@SpringBootTest
public class PomReaderServiceTest {

    @Autowired
    private PomReaderService pomReaderService;

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
