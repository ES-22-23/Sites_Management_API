package es.module2.smapi.datamodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {


    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String ownerUsername;

}
