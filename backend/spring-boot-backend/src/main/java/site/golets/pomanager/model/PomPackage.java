package site.golets.pomanager.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PomPackage {

    private String name;

    private String version;

    private String gitBranch;

}
