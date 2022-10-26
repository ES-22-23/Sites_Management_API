package es.module2.smapi.datamodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropertyDTO {

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private Owner owner;
}
