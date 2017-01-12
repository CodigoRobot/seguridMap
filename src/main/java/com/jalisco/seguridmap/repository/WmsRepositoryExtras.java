package com.jalisco.seguridmap.repository;

import com.jalisco.seguridmap.domain.Wms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;

public interface WmsRepositoryExtras extends WmsRepository {

    Page<Wms> findAllByPanelId(Pageable pageable, Long id);


}
