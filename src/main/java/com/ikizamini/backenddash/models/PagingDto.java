package com.ikizamini.backenddash.models;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class PagingDto {

    private int page=0;
    private int size=3;

}
