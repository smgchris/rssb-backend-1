package com.rssb.backend1.controller;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rssb.backend1.entity.FileUpload;
import com.rssb.backend1.models.UserCsv;
import com.rssb.backend1.service.FileUploadService;
import com.rssb.backend1.service.UserFromFileService;
import com.rssb.backend1.util.Msg;
import com.rssb.backend1.util.ResponseType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class FileController {
    FileUploadService fileUploadService;
    UserFromFileService userFromFileService;

    @GetMapping(value = "get-files")
    public ResponseEntity<Object> getFiles() {
        ResponseType response= new ResponseType();
        try {
            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("");
            response.setObject(fileUploadService.getAllUploads());

        }catch (RuntimeException e){
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("error occured during fetching files");
            response.setObject(null);
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(
            value = "upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> saveUpload(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file") MultipartFile file) {
        ResponseType response= new ResponseType();
        try{
            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("");
            response.setObject(fileUploadService.saveFile(title, description, file));
        }
        catch (RuntimeException | IOException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("uploading failed");
            response.setObject(null);
        } catch (CsvRequiredFieldEmptyException |CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/save-to-db")
    public ResponseEntity<Object> saveFileToDB(@PathVariable("id") Long id) {
        ResponseType response= new ResponseType();

        if(userFromFileService.saveUsersFromFile(id)){
            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("saved all users from the file to the DB");
        }else{
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to save");
        }
        response.setObject(null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="display/{id}/{pageNo}/{pageSize}")
    public ResponseEntity<Object> diplayUploaded(@PathVariable("id") Long id,@PathVariable("pageNo") int pageNo,
                                                 @PathVariable("pageSize") int pageSize){
        ResponseType response= new ResponseType();
        try{

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("Paged users retrieved successfully ");
            response.setObject(fileUploadService.findFromFileByPaging(id,pageNo,pageSize));
        }
        catch (RuntimeException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to retrieve");
            response.setObject(null);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "generate-dummy-csv")
    public ResponseEntity<Object> generateDummy(){
        ResponseType response= new ResponseType();
        try{
            fileUploadService.generateDummyCsv();
            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("generated ");

        }
        catch (RuntimeException | IOException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to generate");

        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping(value = "get-all-uploads")
    public ResponseEntity<Object> getUploads(){
        ResponseType response= new ResponseType();
        try{

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("All uploaded files");
            response.setObject(fileUploadService.getAllUploads());
        }
        catch (RuntimeException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to retrieve");
            response.setObject(null);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get saved users from the db by paging
    @GetMapping(value = "get-saved-users/{pageNo}/{pageSize}")
    public ResponseEntity<Object> getSavedUsers(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize){
        ResponseType response= new ResponseType();
        try{

            response.setCode(Msg.SUCCESS_CODE);
            response.setDescription("All got all users at Page "+pageNo+" with page size of "+pageSize);
            response.setObject(userFromFileService.findByPaging(pageNo,pageSize));
        }
        catch (RuntimeException e){
            e.printStackTrace();
            response.setCode(Msg.ERROR_CODE);
            response.setDescription("failed to retrieve users");
            response.setObject(null);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

