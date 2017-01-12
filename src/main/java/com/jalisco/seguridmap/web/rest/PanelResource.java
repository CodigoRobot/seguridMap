package com.jalisco.seguridmap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jalisco.seguridmap.domain.Panel;

import com.jalisco.seguridmap.repository.PanelRepository;
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
 * REST controller for managing Panel.
 */
@RestController
@RequestMapping("/api")
public class PanelResource {

    private final Logger log = LoggerFactory.getLogger(PanelResource.class);
        
    @Inject
    private PanelRepository panelRepository;

    /**
     * POST  /panels : Create a new panel.
     *
     * @param panel the panel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new panel, or with status 400 (Bad Request) if the panel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/panels")
    @Timed
    public ResponseEntity<Panel> createPanel(@RequestBody Panel panel) throws URISyntaxException {
        log.debug("REST request to save Panel : {}", panel);
        if (panel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("panel", "idexists", "A new panel cannot already have an ID")).body(null);
        }
        Panel result = panelRepository.save(panel);
        return ResponseEntity.created(new URI("/api/panels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("panel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /panels : Updates an existing panel.
     *
     * @param panel the panel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated panel,
     * or with status 400 (Bad Request) if the panel is not valid,
     * or with status 500 (Internal Server Error) if the panel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/panels")
    @Timed
    public ResponseEntity<Panel> updatePanel(@RequestBody Panel panel) throws URISyntaxException {
        log.debug("REST request to update Panel : {}", panel);
        if (panel.getId() == null) {
            return createPanel(panel);
        }
        Panel result = panelRepository.save(panel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("panel", panel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /panels : get all the panels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of panels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/panels")
    @Timed
    public ResponseEntity<List<Panel>> getAllPanels(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Panels");
        Page<Panel> page = panelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/panels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /panels/:id : get the "id" panel.
     *
     * @param id the id of the panel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the panel, or with status 404 (Not Found)
     */
    @GetMapping("/panels/{id}")
    @Timed
    public ResponseEntity<Panel> getPanel(@PathVariable Long id) {
        log.debug("REST request to get Panel : {}", id);
        Panel panel = panelRepository.findOne(id);
        return Optional.ofNullable(panel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /panels/:id : delete the "id" panel.
     *
     * @param id the id of the panel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/panels/{id}")
    @Timed
    public ResponseEntity<Void> deletePanel(@PathVariable Long id) {
        log.debug("REST request to delete Panel : {}", id);
        panelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("panel", id.toString())).build();
    }

}
