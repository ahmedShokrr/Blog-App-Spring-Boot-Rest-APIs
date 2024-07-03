package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//No need for @Repository annotation because we extend JpaRepository which is already annotated with @Repository
//No need for @Transactional annotation because we extend JpaRepository which is already annotated with @Transactional
// This is the repository interface for the Post entity

public interface PostRepository extends JpaRepository<Post, Long> {

    // We will not Write code here cuz we get all the CRUD methods from extend JpaRepository

    // New method to find posts by category ID
    List<Post> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Post p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchPosts(String query);

//    @Query(value = "SELECT * FROM posts p WHERE " +
//            "p.title LIKE CONCAT('%':query, '%')" +
//            "p.description LIKE CONCAT('%',:query, '%')", nativeQuery = true)
//    List<Post> searchPostsSQL(String query);


}
