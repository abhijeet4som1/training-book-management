package com.training.bookmanagement.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Getter
@Setter
class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty(value = "id")
    private Long id;

    @Column(name = "title")
    @JsonProperty(value = "t")
    @NotBlank(message = "Title is required.")
    private String title;

    @Column(name = "img_url")
    @JsonProperty(value = "img")
    private String imgUrl;

    @Column(name = "company")
    @JsonProperty(value = "c")
    @NotBlank(message = "Company is required.")
    private String company;

    @Column(name = "description")
    @JsonProperty(value = "d")
    private String description;

    @Column(name = "quantity")
    @JsonProperty(value = "q")
    @NotNull(message = "Quantity is required.")
    private Integer quantity;

    @Column(name = "price")
    @JsonProperty(value = "p")
    @NotNull(message = "Price is required")
    private Float price;

}
