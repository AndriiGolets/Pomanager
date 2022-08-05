package site.golets.pomanager.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.golets.pomanager.model.PomPackage;

@Component
@Slf4j
public class PomPackageFactory {

    private static final String DEFAULT_BRANCH_NAME = "Unknown";

    @Autowired
    private GitManagementService gitManagementService;

    public PomPackage create(Model model) {
        PomPackage pomPackage = new PomPackage();

        pomPackage.setName(model.getArtifactId());
        pomPackage.setVersion(model.getVersion());
        pomPackage.setModel(model);

        String projectDirPath = model.getProjectDirectory().getAbsolutePath();
        try {
            String branchName = gitManagementService.getBranchName(projectDirPath);
            pomPackage.setGitBranch(branchName);
        } catch (Exception e) {
            log.warn("Git branch name couldn't be retrieved from path [{}]: ", projectDirPath, e);
            pomPackage.setGitBranch(DEFAULT_BRANCH_NAME);
        }

        return pomPackage;
    }

}
