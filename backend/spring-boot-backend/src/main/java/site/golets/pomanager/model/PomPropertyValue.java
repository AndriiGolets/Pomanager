package site.golets.pomanager.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PomPropertyValue {

    private String value;


}
