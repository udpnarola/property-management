package com.property.management.entity;


import com.property.management.constant.PropertyType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@ToString
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2)
    private Integer type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer bedroom;

    @Column(nullable = false)
    private Integer bathroom;

    @Column(nullable = false)
    private Double rent;

    @Column(nullable = false)
    private Boolean isApproved = false;

    @Column(nullable = false)
    private Boolean isFurnished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
