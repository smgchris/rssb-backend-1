package com.rssb.backend1.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rssb.backend1.entity.FileUpload;
import com.rssb.backend1.entity.User;
import com.rssb.backend1.entity.UserFromFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserFromFileService {


    Page<UserFromFile> findByPaging(Integer pageNo, Integer pageSize);
    boolean saveUsersFromFile(Long id);
}
