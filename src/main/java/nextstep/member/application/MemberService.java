package nextstep.member.application;

import org.springframework.stereotype.Service;

import nextstep.exception.BadCredentialException;
import nextstep.exception.MemberNotFoundException;
import nextstep.member.application.dto.MemberRequest;
import nextstep.member.application.dto.MemberResponse;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        return MemberResponse.of(findById(id));
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(RuntimeException::new);
    }

    public void updateMember(Long id, MemberRequest param) {
        Member member = findById(id);
        member.update(param.toMember());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member authenticate(String email, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException(email));

        if (!member.checkPassword(password)) {
            throw new BadCredentialException();
        }

        return member;
    }

    public Member findOrCreateMember(String email) {
        return memberRepository.findByEmail(email)
            .orElseGet(() -> memberRepository.save(new Member(email)));
    }
}
