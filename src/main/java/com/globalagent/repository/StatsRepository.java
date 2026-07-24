package com.globalagent.repository;

import com.globalagent.model.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    Optional<Stats> findByUserId(Long userId);

    @Query("SELECT s FROM Stats s JOIN FETCH s.user u ORDER BY s.xp DESC")
    List<Stats> findAllWithUsersOrderByXpDesc();
}
