package site.golets.pomanager;

import org.apache.maven.model.Model;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import site.golets.pomanager.service.PomReaderService;

import java.util.Properties;

@SpringBootTest
public class PomReaderServiceTest {

    private PomReaderService pomReaderService = new PomReaderService();

    @Test
    public void readPomFileTest(){

        Model model = pomReaderService.readPomModel("pom.xml");

        Properties properties = model.getProperties();

        properties.entrySet().forEach(System.out::println);

    }

}
