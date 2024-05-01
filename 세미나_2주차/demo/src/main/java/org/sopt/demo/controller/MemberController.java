package org.sopt.demo.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.demo.controller.dto.MemberCreateDto;
import org.sopt.demo.controller.dto.MemberFindDto;
import org.sopt.demo.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity postMember(
            @RequestBody MemberCreateDto memberCreate
    ) {
        return ResponseEntity.created(URI.create(memberService.createMember(memberCreate))).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberFindDto> findMemberById(@PathVariable Long memberId){
        return ResponseEntity.ok((memberService.findMemberById(memberId)));
    }

    @GetMapping("/member-list")```
    public ResponseEntity<List<MemberFindDto>> findAllMember(){
        return ResponseEntity.ok(memberService.findAllMember());
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMemberById(@PathVariable Long memberId){
        memberService.deleteMemberById(memberId);
        return ResponseEntity.noContent().build();
    }

}