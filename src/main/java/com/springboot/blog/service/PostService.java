package com.springboot.blog.service;


import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy,String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePost(long id);

    // New method to get posts by category
    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto> searchPosts(String query); // Change return type to List<PostDto>

}
