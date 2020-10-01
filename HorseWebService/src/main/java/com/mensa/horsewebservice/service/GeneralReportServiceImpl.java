/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.service;

import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.model.Football.FirstGoalHighLowDetail;
import com.mensa.horsewebservice.model.Football.Report.GeneralResult;
import com.mensa.horsewebservice.model.Football.Football; 
import com.mensa.horsewebservice.model.Football.GoalHighLowDetail;
import com.mensa.horsewebservice.model.Football.HandicapHomeAwayDraw;
import com.mensa.horsewebservice.model.Football.HomeAwayDraw;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
    private DecimalFormat decimalFormat = new DecimalFormat("000.00");
    
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
            if (record.getHilodds() == null) continue; 
            if (record.getHilodds().getLINELIST() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getTTG() == null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
            {
                try {
                    if (detail.getLINE() == null) continue; 
                    if (detail.getH() == null) continue; 
                    if (detail.getL() == null) continue; 
                    
                    BigDecimal highDefine = new BigDecimal(detail.getLINE().substring(4)); 
                    BigDecimal lowDefine = new BigDecimal(detail.getLINE().substring(0, 3)); 
                    BigDecimal highOdds = new BigDecimal(detail.getH().substring(4)); 
                    BigDecimal lowOdds = new BigDecimal(detail.getL().substring(4)); 
                    BigDecimal result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                    
                    highOdds = getRoundTo5(highOdds); 
                    lowOdds = getRoundTo5(lowOdds);  
                    
                    String key = highDefine.toString() + ": " + decimalFormat.format(highOdds); 
                    List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                    if (filteredAnswer.size() > 0)
                    {
                        newRecord = filteredAnswer.get(0); 
                    } else {
                        newRecord.setKey(key);
                        newRecord.setDescription(key);
                        answer.add(newRecord); 
                    }
                    
                    if (result.compareTo(highDefine) > 0)
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
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String define = "";
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
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
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getGoalLow() {
        log.info("Start Goal Low");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHilodds() == null) continue; 
            if (record.getHilodds().getLINELIST() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getTTG() == null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
            {
                try {
                    if (detail.getLINE() == null) continue; 
                    if (detail.getH() == null) continue; 
                    if (detail.getL() == null) continue; 
                    
                    BigDecimal highDefine = new BigDecimal(detail.getLINE().substring(4)); 
                    BigDecimal lowDefine = new BigDecimal(detail.getLINE().substring(0, 3)); 
                    BigDecimal highOdds = new BigDecimal(detail.getH().substring(4)); 
                    BigDecimal lowOdds = new BigDecimal(detail.getL().substring(4)); 
                    BigDecimal result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                    
                    highOdds = getRoundTo5(highOdds); 
                    lowOdds = getRoundTo5(lowOdds);  
                    
                    String key = highDefine.toString() + ": " + decimalFormat.format(lowOdds); 
                    List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                    if (filteredAnswer.size() > 0)
                    {
                        newRecord = filteredAnswer.get(0); 
                    } else {
                        newRecord.setKey(key);
                        newRecord.setDescription(key);
                        answer.add(newRecord); 
                    }
                    
                    if (result.compareTo(lowDefine) < 0)
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
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String define = "";
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
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
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }

    public List<GeneralResult> getFirstGoalHigh() {
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
            if (record.getFhlodds() == null) continue; 
            if (record.getFhlodds().getLINELIST() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getFCS()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            for(FirstGoalHighLowDetail detail : record.getFhlodds().getLINELIST())
            {
                try {
                    if (detail.getLINE() == null) continue; 
                    if (detail.getH() == null) continue; 
                    if (detail.getL() == null) continue; 
                    
                    BigDecimal highDefine = new BigDecimal(detail.getLINE().substring(4)); 
                    BigDecimal lowDefine = new BigDecimal(detail.getLINE().substring(0, 3)); 
                    BigDecimal highOdds = new BigDecimal(detail.getH().substring(4)); 
                    BigDecimal lowOdds = new BigDecimal(detail.getL().substring(4)); 
                    BigDecimal result = new BigDecimal(record.getResults().getFCS().substring(0, 2));
                    BigDecimal result2 = new BigDecimal(record.getResults().getFCS().substring(3));
                    result = result.add(result2); 
                    
                    highOdds = getRoundTo5(highOdds); 
                    lowOdds = getRoundTo5(lowOdds);  
                    
                    String key = highDefine.toString() + ": " + decimalFormat.format(highOdds); 
                    List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                    if (filteredAnswer.size() > 0)
                    {
                        newRecord = filteredAnswer.get(0); 
                    } else {
                        newRecord.setKey(key);
                        newRecord.setDescription(key);
                        answer.add(newRecord); 
                    }
                    
                    if (result.compareTo(highDefine) > 0)
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
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String define = "";
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
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
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getFirstGoalLow() {
        log.info("Start Goal Low");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getFhlodds() == null) continue; 
            if (record.getFhlodds().getLINELIST() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getFCS()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            for(FirstGoalHighLowDetail detail : record.getFhlodds().getLINELIST())
            {
                try {
                    if (detail.getLINE() == null) continue; 
                    if (detail.getH() == null) continue; 
                    if (detail.getL() == null) continue; 
                    
                    BigDecimal highDefine = new BigDecimal(detail.getLINE().substring(4)); 
                    BigDecimal lowDefine = new BigDecimal(detail.getLINE().substring(0, 3)); 
                    BigDecimal highOdds = new BigDecimal(detail.getH().substring(4)); 
                    BigDecimal lowOdds = new BigDecimal(detail.getL().substring(4)); 
                    BigDecimal result = new BigDecimal(record.getResults().getFCS().substring(0, 3));
                    BigDecimal result2 = new BigDecimal(record.getResults().getFCS().substring(3));
                    result = result.add(result2); 
                    
                    highOdds = getRoundTo5(highOdds); 
                    lowOdds = getRoundTo5(lowOdds);  
                    
                    String key = highDefine.toString() + ": " + decimalFormat.format(lowOdds); 
                    List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                    if (filteredAnswer.size() > 0)
                    {
                        newRecord = filteredAnswer.get(0); 
                    } else {
                        newRecord.setKey(key);
                        newRecord.setDescription(key);
                        answer.add(newRecord); 
                    }
                    
                    if (result.compareTo(lowDefine) < 0)
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
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String define = "";
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
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
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }

    public List<GeneralResult> getHomeAwayDraw(String checkingValue) {
        log.info("Start Home Away Draw");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHadodds() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getHAD()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds; 
                if ("H".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getFhaodds().getH().substring(4)); 
                } else if ("A".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getFhaodds().getA().substring(4)); 
                } else {
                    odds = new BigDecimal(record.getFhaodds().getD().substring(4)); 
                }
                String result = record.getResults().getHAD(); 

                odds = getRoundTo5(odds); 

                String key = decimalFormat.format(odds); 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key);
                    newRecord.setDescription(key);
                    answer.add(newRecord); 
                }

                if (checkingValue.equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getFirstHomeAwayDraw(String checkingValue) {
        log.info("Start First Home Away Draw");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getFhaodds()== null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getFHA()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds; 
                if ("H".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getFhaodds().getH().substring(4)); 
                } else if ("A".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getFhaodds().getA().substring(4)); 
                } else {
                    odds = new BigDecimal(record.getFhaodds().getD().substring(4)); 
                }
                String result = record.getResults().getFHA(); 

                odds = getRoundTo5(odds); 

                String key = decimalFormat.format(odds); 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key);
                    newRecord.setDescription(key);
                    answer.add(newRecord); 
                }

                if (checkingValue.equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getHandicapHomeAwayDraw(String checkingValue) {
        log.info("Start Handicap Home Away Draw");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHadodds() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getHAD()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds; 
                BigDecimal handicap; 
                String tempKey = ""; 
                
                if ("H".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getHhaodds().getH().substring(4)); 
                    handicap = new BigDecimal(record.getHhaodds().getHG().replace("+", "")); 
                    odds = getRoundTo5(odds); 
                    tempKey = decimalFormat.format(odds) + " (" + handicap.toString() + ")"; 
                } else if ("A".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getHhaodds().getA().substring(4)); 
                    handicap = new BigDecimal(record.getHhaodds().getAG().replace("+", "")); 
                    odds = getRoundTo5(odds); 
                    tempKey = decimalFormat.format(odds) + " (" + handicap.toString() + ")"; 
                } else {
                    odds = new BigDecimal(record.getHhaodds().getD().substring(4)); 
                    handicap = new BigDecimal(record.getHhaodds().getHG().replace("+", "")); 
                    odds = getRoundTo5(odds); 
                    tempKey = decimalFormat.format(odds) + " (" + handicap.toString() + ")"; 
                }
                String result = record.getResults().getCRS(); 
                int indexOfResult = result.indexOf(":"); 
                BigDecimal homeResult = BigDecimal.ZERO; 
                BigDecimal awayResult = BigDecimal.ZERO; 
                try {
                    homeResult = new BigDecimal(result.substring(0, indexOfResult)); 
                } catch (Exception ex) {
                    log.error("Exchange Home Result Error", ex);
                    log.error("result: " + result); 
                    log.error("indexOfResult: " + indexOfResult); 
                    continue; 
                }
                try {
                    awayResult = new BigDecimal(result.substring(indexOfResult+1)); 
                } catch (Exception ex) {
                    log.error("Exchange Away Result Error", ex);
                    log.error("result: " + result); 
                    log.error("indexOfResult: " + indexOfResult); 
                    continue; 
                }

                String handicapResult = "";
                if ("H".equals(checkingValue))
                {
                    homeResult = homeResult.add(handicap); 
                    handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                } else if ("A".equals(checkingValue))
                {
                    awayResult = awayResult.add(handicap); 
                    handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                } else {
                    homeResult = homeResult.add(handicap); 
                    handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
               }
                
                String key = tempKey; 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key);
                    newRecord.setDescription(key);
                    answer.add(newRecord); 
                }

                if (checkingValue.equals(handicapResult))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getHandicap(String checkingValue) {
        log.info("Start Handicap");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHadodds() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getHAD()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds; 
                BigDecimal handicap1; 
                BigDecimal handicap2; 
                String tempKey = ""; 
                
                if ("H".equals(checkingValue))
                {
                    odds = new BigDecimal(record.getHdcodds().getH().substring(4)); 
                    int indexOfHandicap = record.getHdcodds().getHG().indexOf("/"); 
                    handicap1 = new BigDecimal(record.getHdcodds().getHG().substring(0, indexOfHandicap).replace("+", "")); 
                    handicap2 = new BigDecimal(record.getHdcodds().getHG().substring(indexOfHandicap+1).replace("+", "")); 
                    odds = getRoundTo5(odds); 
                    tempKey = decimalFormat.format(odds) + " (" + record.getHdcodds().getHG() + ")"; 
                } else 
                {
                    odds = new BigDecimal(record.getHdcodds().getA().substring(4)); 
                    int indexOfHandicap = record.getHdcodds().getAG().indexOf("/"); 
                    handicap1 = new BigDecimal(record.getHdcodds().getAG().substring(0, indexOfHandicap).replace("+", "")); 
                    handicap2 = new BigDecimal(record.getHdcodds().getAG().substring(indexOfHandicap+1).replace("+", "")); 
                    odds = getRoundTo5(odds); 
                    tempKey = decimalFormat.format(odds) + " (" + record.getHdcodds().getHG() + ")"; 
                }
                String result = record.getResults().getCRS(); 
                int indexOfResult = result.indexOf(":"); 
                BigDecimal homeResult = BigDecimal.ZERO; 
                BigDecimal awayResult = BigDecimal.ZERO; 
                try {
                    homeResult = new BigDecimal(result.substring(0, indexOfResult)); 
                } catch (Exception ex) {
                    log.error("Exchange Home Result Error", ex);
                    log.error("result: " + result); 
                    log.error("indexOfResult: " + indexOfResult); 
                    continue; 
                }
                try {
                    awayResult = new BigDecimal(result.substring(indexOfResult+1)); 
                } catch (Exception ex) {
                    log.error("Exchange Away Result Error", ex);
                    log.error("result: " + result); 
                    log.error("indexOfResult: " + indexOfResult); 
                    continue; 
                }

                String handicapResult = "";
                if ("H".equals(checkingValue))
                {
                    homeResult = homeResult.add(handicap1); 
                    handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                    if ("D".equals(handicapResult))
                    {
                        homeResult = homeResult.add(handicap2); 
                        handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                    }
                } else 
                {
                    awayResult = awayResult.add(handicap1); 
                    handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                    if ("D".equals(handicapResult))
                    {
                        awayResult = awayResult.add(handicap2); 
                        handicapResult = (homeResult.compareTo(awayResult) > 0) ? "H" : ((homeResult.compareTo(awayResult) < 0) ? "A" : "D"); 
                    }
               }
                
                
                String key = tempKey; 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key);
                    newRecord.setDescription(key);
                    answer.add(newRecord); 
                }

                if (checkingValue.equals(handicapResult))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    public List<GeneralResult> getTotalGoal() {
        log.info("Start Total Goal");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHadodds() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getHAD()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds01 = new BigDecimal(record.getTtgodds().getP1().substring(4)); ; 
                BigDecimal odds02 = new BigDecimal(record.getTtgodds().getP2().substring(4)); ; 
                BigDecimal odds03 = new BigDecimal(record.getTtgodds().getP3().substring(4)); ; 
                BigDecimal odds04 = new BigDecimal(record.getTtgodds().getP4().substring(4)); ; 
                BigDecimal odds05 = new BigDecimal(record.getTtgodds().getP5().substring(4)); ; 
                BigDecimal odds06 = new BigDecimal(record.getTtgodds().getP6().substring(4)); ; 
                BigDecimal odds07 = new BigDecimal(record.getTtgodds().getM7().substring(4)); ; 
                BigDecimal result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));

                odds01 = getRoundTo5(odds01); 
                odds02 = getRoundTo5(odds02); 
                odds03 = getRoundTo5(odds03); 
                odds04 = getRoundTo5(odds04); 
                odds05 = getRoundTo5(odds05); 
                odds06 = getRoundTo5(odds06); 
                odds07 = getRoundTo5(odds07); 

                // <editor-fold desc="01">
                String key01 = "01: " + decimalFormat.format(odds01); 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key01)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key01);
                    newRecord.setDescription(key01);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(1).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="02">
                String key02 = "02: " + decimalFormat.format(odds02); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key02)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key02);
                    newRecord.setDescription(key02);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(2).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="03">
                String key03 = "03: " + decimalFormat.format(odds03); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key03)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key03);
                    newRecord.setDescription(key03);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(3).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="04">
                String key04 = "04: " + decimalFormat.format(odds04); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key04)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key04);
                    newRecord.setDescription(key04);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(4).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="05">
                String key05 = "05: " + decimalFormat.format(odds05); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key05)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key05);
                    newRecord.setDescription(key05);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(5).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="06">
                String key06 = "06: " + decimalFormat.format(odds06); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key06)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key06);
                    newRecord.setDescription(key06);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(6).equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                // <editor-fold desc="07">
                String key07 = "07: " + decimalFormat.format(odds07); 
                filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key07)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key07);
                    newRecord.setDescription(key07);
                    answer.add(newRecord); 
                }

                if (BigDecimal.valueOf(7).compareTo(result) < 0)
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

                // </editor-fold>
                
                
                
            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";
            String define = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                if (!record.getKey().substring(0, 2).equals(define))
                {
                    numberOfRecord = 0; 
                    winNumberOfRecord = 0; 
                    lostNumberOfRecord = 0; 
                    define = record.getKey().substring(0, 2);
                }
                
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
    private BigDecimal getCorrectScoreOdds(String checkingValue, Football record)
    {
        if ("00:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0000().substring(4)); 
        if ("01:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0101().substring(4)); 
        if ("02:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0202().substring(4)); 
        if ("03:03".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0303().substring(4)); 
        if ("01:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0100().substring(4)); 
        if ("02:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0200().substring(4)); 
        if ("03:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0300().substring(4)); 
        if ("04:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0400().substring(4)); 
        if ("05:00".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0500().substring(4)); 
        if ("02:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0201().substring(4)); 
        if ("03:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0301().substring(4)); 
        if ("04:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0401().substring(4)); 
        if ("05:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0501().substring(4)); 
        if ("03:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0302().substring(4)); 
        if ("04:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0402().substring(4)); 
        if ("05:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0502().substring(4)); 
        if ("00:01".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0001().substring(4)); 
        if ("00:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0002().substring(4)); 
        if ("00:03".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0003().substring(4)); 
        if ("00:04".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0004().substring(4)); 
        if ("00:05".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0005().substring(4)); 
        if ("01:02".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0102().substring(4)); 
        if ("01:03".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0103().substring(4)); 
        if ("01:04".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0104().substring(4)); 
        if ("01:05".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0105().substring(4)); 
        if ("02:03".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0203().substring(4)); 
        if ("02:04".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0204().substring(4)); 
        if ("02:05".equals(checkingValue)) return new BigDecimal(record.getCrsodds().getS0205().substring(4)); 
        return BigDecimal.ZERO; 
    }
    
    public List<GeneralResult> getCorrectScore(String checkingValue) {
        log.info("Start Correct Score");
        
        List<Football> records = recordHandler.GetAll(); 
        //records = records.stream()
        //        .sorted(Comparator.comparing(Football::getMatchIDinofficial))
        //        .collect(Collectors.toList()); 
        List<GeneralResult> answer = new ArrayList<>(); 
        
        //log.info("Records: " + records); 
        //log.info("No of Records: " + records.size()); 
        
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            if (record.getHadodds() == null) continue; 
            if (record.getResults() == null) continue; 
            if (record.getResults().getHAD()== null) continue; 
            GeneralResult newRecord = new GeneralResult();
            newRecord.setWinNumberOfRecord(0);
            newRecord.setLostNumberOfRecord(0);
            newRecord.setAccWinNumberOfRecord(0);
            newRecord.setAccLostNumberOfRecord(0);
            
            try {
                BigDecimal odds = getCorrectScoreOdds(checkingValue, record); 
                String result = record.getResults().getCRS(); 

                odds = getRoundTo5(odds); 

                String key = decimalFormat.format(odds); 
                List<GeneralResult> filteredAnswer = answer.stream().filter(func -> func.getKey().equals(key)).collect(Collectors.toList());
                if (filteredAnswer.size() > 0)
                {
                    newRecord = filteredAnswer.get(0); 
                } else {
                    newRecord.setKey(key);
                    newRecord.setDescription(key);
                    answer.add(newRecord); 
                }

                if (checkingValue.equals(result))
                {
                    newRecord.setWinNumberOfRecord(newRecord.getWinNumberOfRecord() + 1);
                } else {
                    newRecord.setLostNumberOfRecord(newRecord.getLostNumberOfRecord() + 1);
                }

            } catch (Exception ex) {
                log.error("Exchange Error", ex);
            }
        }
        
        try {
            int numberOfRecord = 0; 
            int winNumberOfRecord = 0; 
            int lostNumberOfRecord = 0; 
            String key = "";

            List<GeneralResult> finalAnswer = new ArrayList<>(); 
            answer = answer.stream()
                    .sorted(Comparator.comparing(GeneralResult::getKey))
                    .collect(Collectors.toList()); 
            for(GeneralResult record : answer) {
                if (key.equals(record.getKey())) continue; 
                key = record.getKey(); 
                finalAnswer.add(record); 
                
                numberOfRecord += record.getWinNumberOfRecord() + record.getLostNumberOfRecord(); 
                winNumberOfRecord += record.getWinNumberOfRecord(); 
                lostNumberOfRecord += record.getLostNumberOfRecord(); 
                record.setNumberOfRecord(record.getWinNumberOfRecord() + record.getLostNumberOfRecord());
                record.setAccNumberOfRecord(numberOfRecord);
                record.setAccWinNumberOfRecord(winNumberOfRecord);
                record.setAccLostNumberOfRecord(lostNumberOfRecord);
                record.setWinPercentage(100*record.getWinNumberOfRecord()/record.getNumberOfRecord());
                record.setLostPercentage(100*record.getLostNumberOfRecord()/record.getNumberOfRecord());
                record.setAccWinPercentage(100*record.getAccWinNumberOfRecord()/record.getAccNumberOfRecord());
                record.setAccLostPercentage(100*record.getAccLostNumberOfRecord()/record.getAccNumberOfRecord());
            }
            answer = finalAnswer.stream().sorted(Comparator.comparing(GeneralResult::getKey)).collect(Collectors.toList()); 
        } catch (Exception ex) {
            log.error("Set total Error", ex); 
        }
        
        return answer; 
    }
    
}
