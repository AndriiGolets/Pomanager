package site.golets.pomanager.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PomProperty {

    private String name;

}
