package com.blog.application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "post_title")
    private String title;
    private String content;
    private String imageName;
    private LocalDate addedDate;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoryId")
    private Category category;
    @ManyToOne
    @JsonBackReference
    private User user;
}
