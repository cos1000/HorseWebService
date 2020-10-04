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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matt_
 */
@Entity
@Data
@Table(name = "suggest_buy_record", uniqueConstraints = {@UniqueConstraint(columnNames = { "category", "subCategory", "matchID" })})
public class SuggestBuyRecord implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "idKey", updatable = false, nullable = false)
    private Long idKey;

    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 

    @Column(name = "category")
    private String category;

    @Column(name = "subCategory")
    private String subCategory;

    @Column(name = "description")
    private String description;
    
    @Column(name = "matchID")
    private String matchID;

    @Column(name = "matchIDinofficial")
    private String matchIDinofficial;
    
    @Column(name = "matchNum")
    private String matchNum;
    
    @Column(name = "matchDay")
    private String matchDay;
    
    @Column(name = "homeTeam")
    private String homeTeam;
    
    @Column(name = "awayTeam")
    private String awayTeam;
    
    @Column(name = "rule")
    private String rule;
    
    @Column(name = "odds")
    private BigDecimal odds; 
    
    @Column(name = "matchResult")
    private String matchResult;
    
    @Column(name = "winPrice")
    private BigDecimal winPrice; 
    
    @Transient 
    private BigDecimal accWinPrice; 
    
}

