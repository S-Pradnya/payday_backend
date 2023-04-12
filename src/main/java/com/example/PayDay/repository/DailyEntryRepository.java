package com.example.PayDay.repository;

import com.example.PayDay.entity.DailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyEntryRepository extends JpaRepository<DailyEntry,Long> {

}
