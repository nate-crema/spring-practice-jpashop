package jpabook.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.domain.Member;
import jpabook.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@Controller @ResponseBody
// ==
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * @deprecated API DTO 미적용으로 인한 deprecate -> v2로 update
     * @return
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result( collect );
    }

    @Data
    @AllArgsConstructor
    private class Result<T extends List> {
        public Result( T data ) {
            this.count = data.size();
            this.data = data;
        }

        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    /**
     * @deprecated API DTO 미적용으로 인한 deprecate -> v2로 update
     * @param member
     * @return
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(
            @RequestBody @Valid Member member
    ) {
        Long id = memberService.join(member);
        return new CreateMemberResponse( id );
        /*
        v1의 문제점: API전용 DTO없이 Entity에 API가 의존되도록 설계
        => Entity의 수정이 API 명세에 영향을 줄 수 있으므로, API 명세를 위해 사용하는 DTO 필요
         */
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(
            @RequestBody @Valid CreateMemberRequest request
    ) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse( id );
    }

    @Data
    static class CreateMemberResponse {

        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty // Validation은 DTO에 적용
        private String name;
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update( id, request.getName() );
        Member findMember = memberService.findOne(id); // update()에서 수정"명령"과 조회"요청"을 동시에 수행하지 않도록 하기 위한 설계
        return new UpdateMemberResponse( findMember.getId(), findMember.getName() );
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
