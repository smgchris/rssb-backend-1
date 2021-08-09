package com.rssb.backend1.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import com.rssb.backend1.models.UserValidated;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class DataValidation {

    //validate

        public Object validateAndModifyCsv(MultipartFile file,String outPath) throws IOException, CsvDataTypeMismatchException,
                CsvRequiredFieldEmptyException {
            try {
                //check if the file is empty
                if (file.isEmpty()) {
                    throw new IllegalStateException("Cannot upload empty file");
                }
                File file1= new File(outPath);
                FileWriter outputFile = new FileWriter(file1);

                Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                CSVReader csvReader = new CSVReader(reader);

                CSVWriter csvWriter = new CSVWriter(outputFile,
                        CSVWriter.DEFAULT_SEPARATOR,
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
                String[] newHeaderRecord = {"names", "nid", "phone", "gender","email", "isNidValid","isPhoneValid","isEmailValid"};
                csvWriter.writeNext(newHeaderRecord);

                // Reading Records One by One in a String array
                String[] nextRecord;

                Long start=new Date().getTime();

                int size=0;
                while ((nextRecord = csvReader.readNext()) != null) {

                    if(size>0){
                        csvWriter.writeNext(new String[]{nextRecord[0], nextRecord[1], nextRecord[2], nextRecord[3],nextRecord[4], String.valueOf(validateNid(nextRecord[1])), String.valueOf(validateNid(nextRecord[2])), String.valueOf(validateEmail(nextRecord[4]))});
                    }
                    size++;
                }
                csvWriter.close();
                Long end= new Date().getTime();

                System.out.println("create new file in milliseconds:"+(end-start));
                return file1;
            }
            catch (RuntimeException | CsvValidationException e){
                e.printStackTrace();
                return null;
            }

        }

        //validate Nid
    private  static boolean  validateNid(String nid){
            //assume it's the rwandan nid with 16 digits only.
        if(Pattern.matches("\\d{16}",nid)){
            //proceed with online validation using NIDA APIs
            return true;
        }
        return false;
    }

    // validate phone
    private static boolean validatePhone(String phone){
         //assume a number must be a rwandan line with no country code prefix
        return Pattern.matches("[0][7][2389][0-9]{7}",phone);
    }

    // validate
    public static boolean validateEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

}
