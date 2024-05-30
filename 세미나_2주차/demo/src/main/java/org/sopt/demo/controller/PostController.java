package org.sopt.demo.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.demo.common.dto.SuccessMessage;
import org.sopt.demo.common.dto.SuccessStatusResponse;
import org.sopt.demo.controller.dto.PostCreateRequest;
import org.sopt.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<SuccessStatusResponse> createPost(
            @RequestHeader Long blogId,
            @RequestBody PostCreateRequest postCreateRequest
            ){
        return ResponseEntity.status(HttpStatus.CREATED).header(
                "Location",
                postService.create(blogId, postCreateRequest)
        ).body(SuccessStatusResponse.of(SuccessMessage.POST_CREATE_SUCCESS));
    }

}
