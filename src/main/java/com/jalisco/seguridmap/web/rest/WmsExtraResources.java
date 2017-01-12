package com.jalisco.seguridmap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jalisco.seguridmap.domain.Wms;
import com.jalisco.seguridmap.repository.WmsRepository;
import com.jalisco.seguridmap.repository.WmsRepositoryExtras;
import com.jalisco.seguridmap.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Wms.
 */
@RestController
@RequestMapping("/api")
public class WmsExtraResources {
    @Inject
    private WmsRepositoryExtras wmsRepository;

    /**
     * GET  /wms : get all the wms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wms-by-panel/{pid}")
    @Timed
    public ResponseEntity<List<Wms>> getAllWmsByPanel(@ApiParam Pageable pageable,@PathVariable Long pid)
        throws URISyntaxException {
        Page<Wms> page = wmsRepository.findAllByPanelId(pageable,pid);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wms/{pid}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}

