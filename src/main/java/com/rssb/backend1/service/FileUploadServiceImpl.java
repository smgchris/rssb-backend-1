package com.rssb.backend1.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.rssb.backend1.config.BucketName;
import com.rssb.backend1.entity.FileUpload;
import com.rssb.backend1.entity.UserFromFile;
import com.rssb.backend1.models.UserCsv;
import com.rssb.backend1.repository.FileUploadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    
        private final FileStore fileStore;
        private final FileUploadRepository repository;
        private final DataValidation dataValidation;

        @Override
        public FileUpload saveFile(String title, String description, MultipartFile file) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
            //check if the file is empty
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot upload empty file");
            }
            Long start=new Date().getTime();

            //Check if the file is a csv file
//            if (!Arrays.asList() {
//                throw new IllegalStateException("FIle uploaded is not a csv");
//            }
            //verify content of the file for validation
            String TEMP_PATH = "./output-file.csv";
            File file1= (File) dataValidation.validateAndModifyCsv(file,TEMP_PATH);
            InputStream targetStream = new FileInputStream(file1);

            Long end=new Date().getTime();

            //get file metadata
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            //Save file in S3 and then save FileUpload in the database
            String path = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(), UUID.randomUUID());
            String fileName = String.format("%s", file.getOriginalFilename());

            fileStore.upload(path, fileName, Optional.of(metadata), targetStream);

            Path path2 = Paths.get(TEMP_PATH);
            Files.deleteIfExists(path2);

            FileUpload fileUpload = FileUpload.builder()
                    .description(description)
                    .title(title)
                    .imagePath(path)
                    .imageFileName(fileName)
                    .build();
            repository.save(fileUpload);
            return repository.save(fileUpload);
        }

        @Override
        public Object downloadFile(Long id) {
            FileUpload file = repository.findById(id).get();
            return fileStore.download(file.getImagePath(), file.getImageFileName());
        }

    @Override
    public List<UserCsv> findFromFileByPaging(Long fileId,Integer pageNo, Integer pageSize) {
        FileUpload fileUpload = repository.findById(fileId).get();
        Object file= fileStore.download(fileUpload.getImagePath(), fileUpload.getImageFileName());

        // create csv bean reader
        Reader reader = new BufferedReader(new InputStreamReader((InputStream) file));
        CsvToBean<UserCsv> csvToBean = new CsvToBeanBuilder(reader)
                .withType(UserCsv.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        Long start=new Date().getTime();
        // convert `CsvToBean` object to list of users
        List<UserCsv> users = csvToBean.parse();


        List<UserCsv> usersPaged=new ArrayList<>();
        int max = Math.min(pageNo * pageSize, users.size());
        int min= Math.min((pageNo - 1) * pageSize, users.size());


        for(int i=min;i<max; i++)  //preferred this for loop over the enhanced one for performance reasons
        {
            UserCsv user=UserCsv.builder()
                    .names(users.get(i).getNames())
                    .nid(users.get(i).getNid())
                    .phone(users.get(i).getPhone())
                    .email(users.get(i).getEmail())
                    .gender(users.get(i).getGender())
                    .isNidValid(users.get(i).isNidValid())
                    .isPhoneValid(users.get(i).isPhoneValid())
                    .isEmailValid(users.get(i).isEmailValid())
                    .build();
            usersPaged.add(user);

        }
        Long end=new Date().getTime();

        System.out.println("parsing duration"+(end-start));

        return usersPaged;
    }

    @Override
        public List<FileUpload> getAllUploads() {
        return repository.findAll();
    }

    @Override
    public void generateDummyCsv() throws IOException {
        File file1= new File("./dummy-file.csv");
        FileWriter outputFile = new FileWriter(file1);
        CSVWriter csvWriter = new CSVWriter(outputFile,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        String[] newHeaderRecord = {"names", "nid", "phone", "gender","email"};
        csvWriter.writeNext(newHeaderRecord);
        String[] names= new String[]{"James","Dunia","Akimana","Hakizimana","Itangishaka"};
        String[] gender=new String[]{"male","female"};

        Long start=new Date().getTime();

        for(int i=0;i<50000;i++)
        {
            csvWriter.writeNext(new String[]{names[(int) (Math.random()*4)],"1199"+new Date().getTime(),"+25078"+(""+new Date().getTime()).substring(4,12),gender[(int) (Math.random() * 2)],"email@test.com"});
        }
        csvWriter.close();

        Long end=new Date().getTime();

        System.out.println("duration is: "+(end-start));
    }
}
