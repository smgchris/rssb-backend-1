package com.rssb.backend1.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rssb.backend1.entity.FileUpload;
import com.rssb.backend1.entity.UserFromFile;
import com.rssb.backend1.models.UserCsv;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {
    FileUpload saveFile(String title, String description, MultipartFile file) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

    Object downloadFile(Long id);

    List<FileUpload> getAllUploads();

    List<UserCsv> findFromFileByPaging(Long fileId,Integer pageNo, Integer pageSize);

    void generateDummyCsv() throws IOException;
}
