package org.sopt.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.common.dto.ErrorMessage;
import org.sopt.demo.controller.dto.PostCreateRequest;
import org.sopt.demo.domain.Blog;
import org.sopt.demo.domain.Post;
import org.sopt.demo.exception.NotFoundException;
import org.sopt.demo.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final BlogService blogService;

    @Transactional
    public String create(Long blogId, PostCreateRequest postCreateRequest){
        Blog blog = blogService.findById(blogId);

        if (!blogId.equals(blog.getMember().getId())) {
            throw new NotFoundException(ErrorMessage.NOT_BLOG_OWNER);
        }

        Post post = new Post(postCreateRequest.title(), postCreateRequest.content(), blog);
        post = postRepository.save(post);
        return post.getId().toString();
    }

}
