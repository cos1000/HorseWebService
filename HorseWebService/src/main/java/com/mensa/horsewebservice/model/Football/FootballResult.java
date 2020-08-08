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
@Table(name = "football_result")
public class FootballResult implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "idKey", updatable = false, nullable = false)
    private Long idKey;

    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 

    @Column(name = "OOE")
    private String OOE;

    @Column(name = "FHA")
    private String FHA;

    @Column(name = "CRS")
    private String CRS;

    @Column(name = "HFT")
    private String HFT;

    @Column(name = "TTG")
    private String TTG;

    @Column(name = "FCS")
    private String FCS;

    @Column(name = "HAD")
    private String HAD;
}
