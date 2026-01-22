package com.example.nplusone.repository;

import com.example.nplusone.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @DisplayName("팀 저장 및 조회가 정상적으로 동작해야 한다")
    void saveAndFindTeam() {
        // given
        Team team = new Team("New Team");

        // when
        Team savedTeam = teamRepository.save(team);
        Optional<Team> foundTeam = teamRepository.findById(savedTeam.getId());

        // then
        assertThat(foundTeam).isPresent();
        assertThat(foundTeam.get().getName()).isEqualTo("New Team");
    }

    @Test
    @DisplayName("데이터 SQL로 초기화된 팀 목록을 조회할 수 있어야 한다")
    void findAllTeamsFromSql() {
        // when
        List<Team> teams = teamRepository.findAll();

        // then
        // data.sql에서 10개의 팀을 삽입했으므로 사이즈는 10이어야 함
        assertThat(teams).hasSize(10);
        assertThat(teams).extracting(Team::getName)
                .contains("Team A", "Team J");
    }
}
