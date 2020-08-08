/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.model.Football;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matt_
 */
@Entity
@Data
@Table(name = "first_correct_score", uniqueConstraints = {@UniqueConstraint(columnNames = { "ID" })})
public class FirstCorrectScore implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "idKey", updatable = false, nullable = false)
    private Long idKey;

    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 

    @Column(name = "ID")
    private String ID;

    @Column(name = "POOLSTATUS")
    private String POOLSTATUS;

    @Column(name = "ET")
    private String ET;

    @Column(name = "INPLAY")
    private String INPLAY;

    @Column(name = "ALLUP")
    private String ALLUP;

    @Column(name = "Cur")
    private String Cur;

    @Column(name = "S0000")
    private String S0000;

    @Column(name = "S0101")
    private String S0101;

    @Column(name = "S0202")
    private String S0202;

    @Column(name = "S0303")
    private String S0303;

    @Column(name = "S0100")
    private String S0100;

    @Column(name = "S0200")
    private String S0200;

    @Column(name = "S0201")
    private String S0201;

    @Column(name = "S0300")
    private String S0300;

    @Column(name = "S0301")
    private String S0301;

    @Column(name = "S0302")
    private String S0302;

    @Column(name = "S0400")
    private String S0400;

    @Column(name = "S0401")
    private String S0401;

    @Column(name = "S0402")
    private String S0402;

    @Column(name = "S0500")
    private String S0500;

    @Column(name = "S0501")
    private String S0501;

    @Column(name = "S0502")
    private String S0502;

    @Column(name = "S0001")
    private String S0001;

    @Column(name = "S0002")
    private String S0002;

    @Column(name = "S0102")
    private String S0102;

    @Column(name = "S0003")
    private String S0003;

    @Column(name = "S0103")
    private String S0103;

    @Column(name = "S0203")
    private String S0203;

    @Column(name = "S0004")
    private String S0004;

    @Column(name = "S0104")
    private String S0104;

    @Column(name = "S0204")
    private String S0204;

    @Column(name = "S0005")
    private String S0005;

    @Column(name = "S0105")
    private String S0105;

    @Column(name = "S0205")
    private String S0205;

    @Column(name = "SM1MH")
    private String SM1MH;

    @Column(name = "SM1MA")
    private String SM1MA;

    @Column(name = "SM1MD")
    private String SM1MD;

}
