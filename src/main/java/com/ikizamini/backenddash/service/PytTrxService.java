package com.ikizamini.backenddash.service;

import com.ikizamini.backenddash.entity.PytTrx;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface PytTrxService
{

     PytTrx findByTrxId(String trxId);
     PytTrx findById (Integer id);
     List<PytTrx> findAll();
     Page<PytTrx> findByPaging(Integer pageNo, Integer pageSize);
     PytTrx save(PytTrx pytTrx);

}
