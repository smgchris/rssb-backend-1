package com.rssb.backend1.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Data
public class UsersResponseType implements Serializable {
    @Autowired
    private static final long serialVersionUID = 1L;
    @JsonProperty(value="CODE")
    int code;
    @JsonProperty(value="DESCRIPTION")
    String description;
    @JsonProperty(value="OBJECT")
    Object object;
    @JsonProperty(value="PAGES")
    int numberOfPages;
    @JsonProperty(value="SIZE")
    long numberOfUsers;


}