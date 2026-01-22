package com.example.nplusone.repository;

import com.example.nplusone.domain.Member;
import com.example.nplusone.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("멤버 저장 및 조회가 정상적으로 동작해야 한다")
    void saveAndFindMember() {
        // given
        Team team = teamRepository.save(new Team("Test Team"));
        Member member = new Member("New Member", team);

        // when
        Member savedMember = memberRepository.save(member);
        Optional<Member> foundMember = memberRepository.findById(savedMember.getId());

        // then
        assertThat(foundMember).isPresent();
        assertThat(foundMember.get().getName()).isEqualTo("New Member");
        assertThat(foundMember.get().getTeam().getName()).isEqualTo("Test Team");
    }

    @Test
    @DisplayName("데이터 SQL로 초기화된 멤버 목록을 조회할 수 있어야 한다")
    void findAllMembersFromSql() {
        // when
        List<Member> members = memberRepository.findAll();

        // then
        // data.sql에서 10명의 멤버를 삽입했으므로 사이즈는 10이어야 함
        assertThat(members).hasSize(10);
        assertThat(members).extracting(Member::getName)
                .contains("Member 1", "Member 10");
    }

    @Test
    @DisplayName("멤버 조회 시 연관된 팀 정보가 정상적으로 포함되어야 한다")
    void memberShouldHaveTeam() {
        // when
        List<Member> members = memberRepository.findAll();

        // then
        for (Member member : members) {
            assertThat(member.getTeam()).isNotNull();
            assertThat(member.getTeam().getName()).startsWith("Team ");
        }
    }
}
