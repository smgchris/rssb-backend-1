package com.rssb.backend1.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rssb.backend1.config.BucketName;
import com.rssb.backend1.entity.FileUpload;
import com.rssb.backend1.entity.User;
import com.rssb.backend1.entity.UserFromFile;
import com.rssb.backend1.models.UserCsv;
import com.rssb.backend1.repository.FileUploadRepository;
import com.rssb.backend1.repository.UserFromFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class UserFromFileServiceImpl implements UserFromFileService {
    
    private final UserFromFileRepository userFromFileRepository;
    private final FileStore fileStore;
    private final FileUploadRepository repository;

    @Override
    public Page<UserFromFile> findByPaging(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo-1, pageSize, Sort.by("id"));

        Page<UserFromFile> pagedResult = userFromFileRepository.findAll(paging);

        if (pagedResult.hasContent())
        {
            return pagedResult;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean saveUsersFromFile(Long id) {
        FileUpload fileUpload = repository.findById(id).get();
        Object file= fileStore.download(fileUpload.getImagePath(), fileUpload.getImageFileName());

        // create csv bean reader
        Reader reader = new BufferedReader(new InputStreamReader((InputStream) file));
        CsvToBean<UserCsv> csvToBean = new CsvToBeanBuilder(reader)
                .withType(UserCsv.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        // convert `CsvToBean` object to list of users
        List<UserCsv> users = csvToBean.parse();
        List<UserFromFile> usersFromFile=new ArrayList<>();

        for(int i=0;i<users.size(); i++)  //preferred this for loop over the enhanced one for performance reasons
        {
            UserFromFile user=UserFromFile.builder()
                    .name(users.get(i).getNames())
                    .nid(users.get(i).getNid())
                    .phone(users.get(i).getPhone())
                    .email(users.get(i).getEmail())
                    .gender(users.get(i).getGender())
                    .isNidValid(users.get(i).isNidValid())
                    .isPhoneValid(users.get(i).isPhoneValid())
                    .isEmailValid(users.get(i).isEmailValid())
                    .createdDate(new Date().getTime())
                    .build();
            usersFromFile.add(user);

        }
        try {
            userFromFileRepository.saveAll(usersFromFile);
            return true;
        }
        catch (RuntimeException e){
            e.printStackTrace();
            return false;
        }

    }
}
