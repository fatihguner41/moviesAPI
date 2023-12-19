package com.example.demo.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Table("movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    private Long id;
    private String name;
    private Date date;
    private Double imdbScore;
    private Integer income;
    private String description;

    @MappedCollection(idColumn = "movie_id")
    private Set<MovieCategory> categories = new HashSet<>();




}
