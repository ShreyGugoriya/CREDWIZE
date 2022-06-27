package com.quadcore.quadcore.Repo;

import com.quadcore.quadcore.Entities.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
}
