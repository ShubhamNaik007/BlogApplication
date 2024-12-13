package com.blog.application.repositories;

import com.blog.application.entities.Category;
import com.blog.application.entities.Post;
import com.blog.application.entities.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
