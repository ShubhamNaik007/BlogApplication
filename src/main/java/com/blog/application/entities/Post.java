package com.blog.application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();
}
