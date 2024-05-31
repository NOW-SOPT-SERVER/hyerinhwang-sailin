package org.sopt.demo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.sopt.demo.auth.UserAuthentication;
import org.sopt.demo.auth.redis.domain.Token;
import org.sopt.demo.auth.redis.repository.RedisTokenRepository;
import org.sopt.demo.auth.redis.service.RedisTokenService;
import org.sopt.demo.common.jwt.JwtTokenProvider;
import org.sopt.demo.controller.dto.MemberCreateDto;
import org.sopt.demo.controller.dto.MemberFindDto;
import org.sopt.demo.domain.Member;
import org.sopt.demo.common.dto.ErrorMessage;
import org.sopt.demo.repository.MemberRepository;
import org.sopt.demo.service.dto.UserJoinResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenRepository redisTokenRepository;
//    private final RedisTokenService redisTokenService;

    @Transactional
    public UserJoinResponse createMember(
            MemberCreateDto memberCreateDto
    ) {
        Member member = memberRepository.save(
                Member.create(memberCreateDto.name(), memberCreateDto.part(), memberCreateDto.age())
        );
        Long memberId = member.getId();
        UserAuthentication userAuthentication = UserAuthentication.createUserAuthentication(memberId);
        String accessToken = jwtTokenProvider.issueAccessToken(userAuthentication);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
//        redisTokenService.saveRefreshToken(memberId, refreshToken);
        Token refreshTokenEntity = new Token(memberId, refreshToken);
        redisTokenRepository.save(refreshTokenEntity);
        return UserJoinResponse.of(accessToken, refreshToken, memberId.toString());
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
