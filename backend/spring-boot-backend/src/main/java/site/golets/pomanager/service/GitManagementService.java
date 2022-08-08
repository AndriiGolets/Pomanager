package site.golets.pomanager.service;

import java.util.List;

public interface GitManagementService {

    String getBranchName(String gitRepositorySource);

    boolean checkoutAndPull(String gitRepositorySource, String branch, boolean hard);

    boolean checkoutAndPull(List<String> gitRepositorySources, String branch, boolean hard);

}
