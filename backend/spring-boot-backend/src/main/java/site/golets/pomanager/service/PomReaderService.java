package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class PomReaderService {

    private final MavenXpp3Reader pomReader = new MavenXpp3Reader();

    public Optional<Model> readPomModel(String path) {
        try (Stream<Path> pathStream = Files.find(Paths.get(path), 3, (p, a) -> p.endsWith("pom.xml"))) {
            Optional<Path> pomPath = pathStream.findFirst();
            return pomPath.map(this::pathToModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Model pathToModel(Path pomPath) {
        try {
            return pomReader.read(new FileReader(pomPath.toFile()));
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Model> scanFileSystem(String rootPath) {
        File root = Paths.get(rootPath).toFile();

        if (!root.exists()) {
            throw new RuntimeException("Path doesn't exists.");
        }

        if (root.isFile()) {
            log.info("Root path must be a directory. Parent directory of a file will be used instead.");
            root = root.getParentFile();
        }

        Optional<File[]> files = Optional.ofNullable(root.listFiles());
        return Arrays.stream(files.orElse(new File[] {}))
                .map(File::getAbsolutePath)
                .map(this::readPomModel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
