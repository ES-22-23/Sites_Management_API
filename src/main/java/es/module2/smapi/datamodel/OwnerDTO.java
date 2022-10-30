package es.module2.smapi.datamodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String name;

}
