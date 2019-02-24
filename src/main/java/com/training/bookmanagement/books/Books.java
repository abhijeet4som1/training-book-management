package com.training.bookmanagement.books;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "books")
@Entity
@Getter
@Setter
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty(value = "id")
    private Integer bookId;

    @Column(name = "name")
    @JsonProperty(value = "n")
    private String name;

    @Column(name = "author")
    @JsonProperty(value = "an")
    private String authorName;
}
