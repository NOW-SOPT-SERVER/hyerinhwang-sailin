package org.sopt.demo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.controller.dto.MemberCreateDto;
import org.sopt.demo.controller.dto.MemberFindDto;
import org.sopt.demo.domain.Member;
import org.sopt.demo.common.dto.ErrorMessage;
import org.sopt.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(
            final MemberCreateDto memberCreateDto
    ){
        Member member = Member.create(memberCreateDto.name(), memberCreateDto.part(), memberCreateDto.age());
            memberRepository.save(member);
            return member.getId().toString();
    }

    public MemberFindDto findMemberById(
            Long memberId
    ){
        return MemberFindDto.of(memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다.")
        ));
    }

    public List<MemberFindDto> findAllMember(){
        return memberRepository.findAll().stream()
                .map(member -> new MemberFindDto(member.getName(), member.getPart(), member.getAge()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        memberRepository.delete(member);
    }

    public Member findById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(ErrorMessage.MEMBER_NOT_FOUND_BY_ID_EXCEPTION))
        );
    }

}
