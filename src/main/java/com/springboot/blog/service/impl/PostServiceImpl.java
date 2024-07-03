package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private CategoryRepository categoryRepository;

    private ModelMapper mapper;


    @Autowired
    public PostServiceImpl(PostRepository postRepository,CategoryRepository categoryRepository,ModelMapper mapper ) {
        this.postRepository = postRepository;
        this.categoryRepository=categoryRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category=categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

//        //convert DTO to entity
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());

        Post post= mapToEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        // convert entity to DTO

        PostDto postResponse = mapToDTO(newPost);



        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy,String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //Create Pageable instance
        PageRequest pageable= PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts= posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(()-> new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        post.setCategory(category);
        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);

    }

    @Override
    public void deletePost(long id) {
        //get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map(post -> mapper.map(post, PostDto.class)).collect(Collectors.toList());

    }


    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Post", "query", query);
        }
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }



    //convert Entity to DTO
    private PostDto mapToDTO(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());

        return postDto;
    }

    //Convert DTO to Entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

//        Post post =new Post();
//        post.setTitle(postDto.getTitle());
//        post.setContent(postDto.getContent());
//        post.setDescription(postDto.getDescription());

        return post;
    }


}
