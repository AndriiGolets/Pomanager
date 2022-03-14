package site.golets.pomanager.service;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PomReaderService {

    public Model readPomModel(String path) {
        Model model;
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            MavenXpp3Reader reader = new MavenXpp3Reader();
            model = reader.read(in);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + " Pom file read Error path: " + path);
        }
        return model;
    }

    public List<Model> scanFileSystem(String rootPath) throws IOException {

        Path dir = Paths.get("/path/to/dir");
       // Files.walk(dir).forEach(path -> showFile(path.toFile()));
return null;
    }

}
