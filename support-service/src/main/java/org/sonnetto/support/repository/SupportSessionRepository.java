package org.sonnetto.support.repository;

import org.sonnetto.support.entity.SupportSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportSessionRepository extends JpaRepository<SupportSession, Long> {
}
