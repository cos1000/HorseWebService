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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matt_
 */
@Entity
@Data
@Table(name = "goal_high_low", uniqueConstraints = {@UniqueConstraint(columnNames = { "ID" })})
public class GoalHighLow implements Serializable {
    
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

    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "detail_id")
    private List<GoalHighLowDetail> LINELIST = new ArrayList<>();
    
}
