package com.jalisco.seguridmap.repository;

import com.jalisco.seguridmap.domain.UserReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserReport entity.
 */
@SuppressWarnings("unused")
public interface UserReportRepository extends JpaRepository<UserReport,Long> {

    @Query("select userReport from UserReport userReport where userReport.autor.login = ?#{principal.username}")
    List<UserReport> findByAutorIsCurrentUser();


    @Query("select userReport from UserReport userReport where userReport.autor.login = ?#{principal.username}")
    Page<UserReport> findByAutorIsCurrentUser(Pageable pageable);
}
