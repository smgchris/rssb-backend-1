package com.rssb.backend1.repository;

import com.rssb.backend1.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>
{
    FileUpload findByTitle(String title);


}