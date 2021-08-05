package com.ikizamini.backenddash.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;



@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "trxid"), name = "trxpyt")
public class PytTrx {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Integer id;
    @Column(name="trxid")
    private String trxId;
    @Column(name="type")
    private int type;  //0: sent, 1:received
    @Column(name="status")
    private int status; //0: initiated, 1: pending, 2:success, 3:failed
    @Column(name = "reason")
    private String reason;
    @Column(name = "trxtime")
    private long  trxTime;
    @Column(name = "callbacktime")
    private long  callbackTime;
    @Column(name="callbackmsg")
    private String callbackMsg;
    private String gwRef;
    private Double amount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initiator_id", referencedColumnName = "id")
    private User2  initiator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "user_id")
    private User  customer;

    private String recipient;



    public PytTrx() {
    }

}
