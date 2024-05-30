package org.sopt.demo.service;

import ch.qos.logback.core.spi.ErrorCodes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.controller.dto.BlogCreateRequest;
import org.sopt.demo.controller.dto.BlogTitleUpdateRequest;
import org.sopt.demo.domain.Blog;
import org.sopt.demo.domain.Member;
import org.sopt.demo.exception.NotFoundException;
import org.sopt.demo.repository.BlogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final MemberService memberService;

    public String create(Long memberId, BlogCreateRequest blogCreateRequest){
        //member찾기
        Member member = memberService.findById(memberId);
        Blog blog = blogRepository.save(Blog.create(member, blogCreateRequest));
        return blog.getId().toString();
    }

    @Transactional
    public void updateTitle(Long blogId, BlogTitleUpdateRequest blogTitleUpdateRequest){
        Blog blog = findById(blogId);
        blog.updateTitle(blogTitleUpdateRequest.title());
    }

    public Blog findById(Long blogId){
        return blogRepository.findById(blogId).orElseThrow();
    }
}
