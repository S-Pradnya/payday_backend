package com.example.PayDay.repository;

import com.example.PayDay.entity.DailyEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DailyEntryPaginatedRepository extends PagingAndSortingRepository<DailyEntry, Long> {

    Page<DailyEntry> findByDeDateEqualsAndDeEmployeeId(Date deDate, Long deEmployeeId, Pageable pageable);

    Page<DailyEntry> findByDeDateLessThanEqualAndDeDateGreaterThanEqualAndDeEmployeeId(Date endDate, Date deDate, Long deEmployeeId, Pageable pageable);
}
