package com.ikizamini.backenddash.models;

//import javax.validation.constraints.NotEmpty;

import com.ikizamini.backenddash.entity.User;
import com.ikizamini.backenddash.entity.User2;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
public class SendMoneyDto
{

    private String reason;
    private String amount;
    private String initiator;
    private String recipient;
    private String pytMethod;
}
