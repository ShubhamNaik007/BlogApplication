package com.blog.application.payloads;

import com.blog.application.entities.Category;
import com.blog.application.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String title;
    private String content;
    private String imageName;
    private LocalDate addedDate;
    private CategoryDto category;
    private UserDto user;
}
