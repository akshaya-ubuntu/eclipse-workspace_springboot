package com.example.crud.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PRODUCTS")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq")
    @SequenceGenerator(name = "prod_seq", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Double price;
    
    private Integer stock;

	
    
}