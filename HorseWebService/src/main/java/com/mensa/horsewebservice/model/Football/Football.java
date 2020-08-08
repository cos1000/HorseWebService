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
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author matt_
 */

@Entity
@Data
@Table(name = "football", uniqueConstraints = {@UniqueConstraint(columnNames = { "matchID" })})
public class Football implements Serializable {
    
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "idKey", updatable = false, nullable = false)
    private Long idKey;

    @Column(name = "created_at")
    private LocalDateTime created_at; 
    @Column(name = "updated_at")
    private LocalDateTime updated_at; 
    
    @Column(name = "matchID")
    private String matchID;
    
    @Column(name = "matchIDinofficial")
    private String matchIDinofficial;
    
    @Column(name = "matchNum")
    private String matchNum;
    
    @Column(name = "matchDate")
    private String matchDate;
    
    @Column(name = "matchDay")
    private String matchDay;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "couponID", referencedColumnName = "idKey")
    private Coupon coupon;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leagueID", referencedColumnName = "idKey")
    private League league;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "homeTeamID", referencedColumnName = "idKey")
    private Team homeTeam;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "awayTeamID", referencedColumnName = "idKey")
    private Team awayTeam;
    
    @Column(name = "matchStatus")
    private String matchStatus;
    
    @Column(name = "matchTime")
    private String matchTime;
    
    @Column(name = "statuslastupdated")
    private String statuslastupdated;
    
    @Column(name = "inplaydelay")
    private String inplaydelay;
    
    @Column(name = "Cur")
    private String Cur;
    
    @Column(name = "hasWebTV")
    private Boolean hasWebTV;
    
    @Column(name = "hasOdds")
    private Boolean hasOdds;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hadoddsID", referencedColumnName = "idKey")
    private HomeAwayDraw hadodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fhaoddsID", referencedColumnName = "idKey")
    private FirstHomeAwayDraw fhaodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "crsoddsID", referencedColumnName = "idKey")
    private CorrectScore crsodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fcsoddsID", referencedColumnName = "idKey")
    private FirstCorrectScore fcsodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ftsoddsID", referencedColumnName = "idKey")
    private FirstTeamToScore ftsodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ooeoddsID", referencedColumnName = "idKey")
    private OddEven ooeodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ttgoddsID", referencedColumnName = "idKey")
    private TotalGoal ttgodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hftoddsID", referencedColumnName = "idKey")
    private HalfFullHomeAwayDraw hftodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hhaoddsID", referencedColumnName = "idKey")
    private HandicapHomeAwayDraw hhaodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hdcoddsID", referencedColumnName = "idKey")
    private Handicap hdcodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hiloddsID", referencedColumnName = "idKey")
    private GoalHighLow hilodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fhloddsID", referencedColumnName = "idKey")
    private FirstGoalHighLow fhlodds;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chloddsID", referencedColumnName = "idKey")
    private CornerHighLow chlodds;
    
    @Column(name = "hasExtraTimePools")
    private Boolean hasExtraTimePools;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resultsID", referencedColumnName = "idKey")
    private FootballResult results;
    
    @Column(name = "cornerresult")
    private String cornerresult;

}
