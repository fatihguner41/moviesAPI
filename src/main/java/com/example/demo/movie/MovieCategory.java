package com.example.demo.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("movies_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieCategory {
    @Column("category_id")
    private Long categoryId;
}
