package com.jalisco.seguridmap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jalisco.seguridmap.domain.User;
import com.jalisco.seguridmap.domain.UserReport;

import com.jalisco.seguridmap.domain.enumeration.EstadoReporte;
import com.jalisco.seguridmap.repository.UserReportRepository;
import com.jalisco.seguridmap.repository.UserRepository;
import com.jalisco.seguridmap.security.SecurityUtils;
import com.jalisco.seguridmap.web.rest.util.HeaderUtil;
import com.jalisco.seguridmap.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserReport.
 */
@RestController
@RequestMapping("/api")
public class UserReportResource {

    private final Logger log = LoggerFactory.getLogger(UserReportResource.class);

    @Inject
    private UserReportRepository userReportRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /user-reports : Create a new userReport.
     *
     * @param userReport the userReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userReport, or with status 400 (Bad Request) if the userReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-reports")
    @Timed
    public ResponseEntity<UserReport> createUserReport(@RequestBody UserReport userReport) throws URISyntaxException {
        log.debug("REST request to save UserReport : {}", userReport);

        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> currentUser = userRepository.findOneByLogin(login);
        if (currentUser.isPresent())
            userReport.setAutor(currentUser.get());

        userReport.setEstadoReporte(EstadoReporte.EsperaAtencion);

        if (userReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userReport", "idexists", "A new userReport cannot already have an ID")).body(null);
        }
        UserReport result = userReportRepository.save(userReport);
        return ResponseEntity.created(new URI("/api/user-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userReport", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-reports : Updates an existing userReport.
     *
     * @param userReport the userReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userReport,
     * or with status 400 (Bad Request) if the userReport is not valid,
     * or with status 500 (Internal Server Error) if the userReport couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-reports")
    @Timed
    public ResponseEntity<UserReport> updateUserReport(@RequestBody UserReport userReport) throws URISyntaxException {
        log.debug("REST request to update UserReport : {}", userReport);
        if (userReport.getId() == null) {
            return createUserReport(userReport);
        }
        if(userReport.getEstadoReporte() == null){
            userReport.setEstadoReporte(EstadoReporte.EsperaAtencion);
        }
        UserReport result = userReportRepository.save(userReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userReport", userReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-reports : get all the userReports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userReports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-reports")
    @Timed
    public ResponseEntity<List<UserReport>> getAllUserReports(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserReports");

        String login = SecurityUtils.getCurrentUserLogin();
        Optional<User> currentUser = userRepository.findOneByLogin(login);
        Page<UserReport> page = null;
        if (currentUser.isPresent()){
            if(SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")){
                page = userReportRepository.findAll(pageable);
            }else{
                page = userReportRepository.findByAutorIsCurrentUser(pageable);
            }
        }else{
            page = userReportRepository.findByAutorIsCurrentUser(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /current-user-reports : get all current user userReports.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userReports in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/current-user-reports")
    @Timed
    public ResponseEntity<List<UserReport>> getAllCurrentUserReports(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserReports");
        Page<UserReport> page = userReportRepository.findByAutorIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/current-user-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /user-reports/:id : get the "id" userReport.
     *
     * @param id the id of the userReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userReport, or with status 404 (Not Found)
     */
    @GetMapping("/user-reports/{id}")
    @Timed
    public ResponseEntity<UserReport> getUserReport(@PathVariable Long id) {
        log.debug("REST request to get UserReport : {}", id);
        UserReport userReport = userReportRepository.findOne(id);
        return Optional.ofNullable(userReport)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-reports/:id : delete the "id" userReport.
     *
     * @param id the id of the userReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserReport(@PathVariable Long id) {
        log.debug("REST request to delete UserReport : {}", id);
        userReportRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userReport", id.toString())).build();
    }

}
