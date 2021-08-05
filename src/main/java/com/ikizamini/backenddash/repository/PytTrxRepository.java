package com.ikizamini.backenddash.repository;

import com.ikizamini.backenddash.entity.PytTrx;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PytTrxRepository extends JpaRepository<PytTrx, Integer>
{
    @Override
    Page<PytTrx> findAll(Pageable pageable);
    PytTrx findByTrxId(String trxId);

}