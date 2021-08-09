package com.rssb.backend1.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserValidated {

    private String names;
    private String nid;
    private String phone;
    private String gender;
    private String email;
    private boolean isNidValid;
    private boolean isPhoneValid;
    private boolean isEmailValid;


}
