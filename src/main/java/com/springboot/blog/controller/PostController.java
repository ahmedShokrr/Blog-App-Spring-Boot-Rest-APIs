package com.springboot.blog.controller;


import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@Tag(
        name="CRUD REST APIs for Post Resource"
)
public class PostController {

    private PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into datebase"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    //create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

    }
    //Get all posts
    //@PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to Fetch all posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS "
    )
    @GetMapping("/api/v1/posts")
    public ResponseEntity<PostResponse> getAllPosts(
                @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
                @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
                ){
        return new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //Get post by id
    @Operation(
            summary = "GEt Post By Id REST API",
            description = "Get Post By REST API is used to get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS"
    )
    @GetMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity <PostDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    //Update post by id
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post By REST API is used to update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS "
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //Delete post with id
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST API is used to delete a particular post from the database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 SUCCESS "
    )

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){

        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
        List<PostDto> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(posts);
    }


}
