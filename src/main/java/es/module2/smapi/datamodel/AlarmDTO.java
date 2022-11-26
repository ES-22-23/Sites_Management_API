package es.module2.smapi.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDTO {
    
    @NonNull
    private long id;

    @NonNull
    private String propertyName;

    @NonNull
    private String propertyAddress;
}
