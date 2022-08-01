package site.golets.pomanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.golets.pomanager.service.GitManagementService;
import site.golets.pomanager.utils.PathUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@Slf4j
public class FileSystemGitManagementServiceImpl implements GitManagementService {

    private static final String GIT_DIR_NAME = ".git";

    private final Runtime runtime = Runtime.getRuntime();

    @Override
    public String getBranchName(String gitRepositoryPath) {
        PathUtils.existsFailFast(gitRepositoryPath);

        File repositoryDir = Paths.get(gitRepositoryPath).toFile();

        if (!isGitRepository(repositoryDir)) {
            throw new IllegalStateException("Provided path is not a Git repository.");
        }

        try {
            Process process = runtime.exec("git rev-parse --abbrev-ref HEAD", null, repositoryDir);
            process.waitFor();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            return reader.readLine();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isGitRepository(File repositoryDir) {
        if (!repositoryDir.isDirectory()) {
            return false;
        }

        String[] directoryList = repositoryDir.list();

        if (directoryList == null) {
            throw new IllegalStateException("Provided directory is empty.");
        }

        return Arrays.stream(directoryList)
                .anyMatch(n -> n.endsWith(GIT_DIR_NAME));
    }

}
