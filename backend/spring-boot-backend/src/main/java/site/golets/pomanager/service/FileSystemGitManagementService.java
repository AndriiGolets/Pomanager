package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileSystemGitManagementService implements GitManagementService {

    @Override
    public String getBranch(String gitRepositoryPath) {
        Path path = Paths.get(gitRepositoryPath);
        if (!path.toFile().exists()) {

        }
        return null;
    }

}
