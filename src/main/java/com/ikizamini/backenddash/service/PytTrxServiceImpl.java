package com.ikizamini.backenddash.service;


import com.ikizamini.backenddash.entity.PytTrx;
import com.ikizamini.backenddash.entity.User2;
import com.ikizamini.backenddash.repository.PytTrxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PytTrxServiceImpl implements PytTrxService
{

    @Autowired
    private PytTrxRepository pytTrxRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public PytTrx findByTrxId(String trxId) {
        try{
            return pytTrxRepository.findByTrxId(trxId);
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PytTrx findById(Integer id) {
        try{
            return pytTrxRepository.findById(id).get();
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PytTrx> findAll() {
        try{
            return pytTrxRepository.findAll();
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<PytTrx> findByPaging(Integer pageNo, Integer pageSize) {
        try{
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id"));

            Page<PytTrx> pagedResult = pytTrxRepository.findAll(paging);

            if (pagedResult.hasContent())
            {
                return pagedResult;
            }
            else
            {
                return null;
            }
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PytTrx save(PytTrx pytTrx) {
        return pytTrxRepository.save(pytTrx);
    }
}
