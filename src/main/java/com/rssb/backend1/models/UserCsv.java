package com.rssb.backend1.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCsv {

    @CsvBindByName
    private String names;
    @CsvBindByName
    private String nid;
    @CsvBindByName
    private String phone;
    @CsvBindByName
    private String gender;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private boolean isNidValid;
    @CsvBindByName
    private boolean isPhoneValid;
    @CsvBindByName
    private boolean isEmailValid;

}
