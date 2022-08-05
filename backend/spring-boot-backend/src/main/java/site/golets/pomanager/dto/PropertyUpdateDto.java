package site.golets.pomanager.dto;

import lombok.Value;

@Value
public class PropertyUpdateDto {

    String packageName;

    String propertyName;

    String newValue;

}
