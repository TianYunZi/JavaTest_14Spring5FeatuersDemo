package chapter01.practise.org.reactive.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Restaurant {

    @Id
    public String id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String telephone;

    public Restaurant(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }
}
