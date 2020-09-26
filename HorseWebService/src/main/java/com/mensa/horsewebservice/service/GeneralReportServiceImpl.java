/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.service;

import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.model.Football.Report.GeneralResult;
import com.mensa.horsewebservice.model.Football.Football; 
import com.mensa.horsewebservice.model.Football.GoalHighLowDetail;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author matt_
 */
@Service
public class GeneralReportServiceImpl {
    private static Logger log = LoggerFactory.getLogger(GeneralReportServiceImpl.class);
    private FootballHandler recordHandler;
    
    public void setFootballHandler(FootballHandler handler)
    {
        this.recordHandler = handler; 
    }
    
    private BigDecimal getRoundTo5(BigDecimal value) 
    {
        return BigDecimal.valueOf(((double) (long) (value.doubleValue() * 20 + 0.5)) / 20);
    }
    
    public List<GeneralResult> getGoalHigh() {
        log.info("Start Goal High");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
            {
                try {
                    BigDecimal highDefine = new BigDecimal(detail.getLINE().substring(4)); 
                    BigDecimal lowDefine = new BigDecimal(detail.getLINE().substring(0, 3)); 
                    BigDecimal highOdds = new BigDecimal(detail.getH().substring(4)); 
                    BigDecimal lowOdds = new BigDecimal(detail.getL().substring(4)); 
                    BigDecimal result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                    
                    highOdds = getRoundTo5(highOdds); 
                    lowOdds = getRoundTo5(lowOdds);  
                    
                    String key = highDefine.toString() + ": " + highOdds.toString(); 
                    List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                    if (filteredAnswer.size() > 0)
                    {
                        newRecord = filteredAnswer.get(0); 
                    } else {
                        newRecord.setKey(key);
                        newRecord.setDescription(key);
                        answer.add(newRecord); 
                    }
                    
                    if (result.compareTo(highDefine) >= 0)
                    {
                        newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                    } else {
                        newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                    }
                    
                } catch (Exception ex) {
                    log.error("Exchange Error", ex);
                }
            }
        }
        
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        String define = "";
                
        answer = answer.stream()
                .sorted(Comparator.comparing(GeneralResult::getKey))
                .collect(Collectors.toList()); 
        for(GeneralResult record : answer) {
            if (!record.getKey().substring(0, 3).equals(define))
            {
                numberOfRecord = 0; 
                winNumberOfRecord = 0; 
                lostNumberOfRecord = 0; 
                define = record.getKey().substring(0, 3);
            }
            numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
            record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
            record.setAccNumberOfRecord(numberOfRecord);
            record.setAccWinNumberOfRecord(winNumberOfRecord);
            record.setAccLostNumberOfRecord(lostNumberOfRecord);
            record.setWinPercentage(record.getWinNumberOfRecord()/record.getNumberOfRecord());
            record.setLostPercentage(record.getLostNumberOfRecord()/record.getNumberOfRecord());
            record.setAccWinPercentage(record.getWinNumberOfRecord()/record.getNumberOfRecord());
            record.setAccWinPercentage(record.getWinNumberOfRecord()/record.getNumberOfRecord());
        }
        
        return answer; 
    }

    
}
