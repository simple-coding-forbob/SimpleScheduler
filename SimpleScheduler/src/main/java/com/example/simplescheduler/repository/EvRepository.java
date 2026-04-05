package com.example.simplescheduler.repository;

import com.example.simplescheduler.entity.Ev;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TB_EV Repository
 */
public interface EvRepository extends JpaRepository<Ev, Long> {
}
