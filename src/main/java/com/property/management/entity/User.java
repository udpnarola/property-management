package com.property.management.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class User {

    @Id
    @Column(length = 50)
    private String apiKey;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties = new ArrayList<>();

    public void addProperty(Property property) {
        properties.add(property);
        property.setUser(this);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        property.setUser(null);
    }
}
