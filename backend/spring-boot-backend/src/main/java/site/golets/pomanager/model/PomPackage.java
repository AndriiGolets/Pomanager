package site.golets.pomanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.maven.model.Model;

@Data
@Accessors(chain = true)
@ToString(exclude = {"model"})
public class PomPackage {

    private String name;

    private String version;

    private String gitBranch;

    private int branchStatus;

    @JsonIgnore
    private Model model;

}
