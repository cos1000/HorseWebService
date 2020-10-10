/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice;

import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.dao.SuggestBuyRecordHandler;
import com.mensa.horsewebservice.model.Football.FirstGoalHighLowDetail;
import com.mensa.horsewebservice.model.Football.Football;
import com.mensa.horsewebservice.model.Football.GoalHighLowDetail;
import com.mensa.horsewebservice.model.Football.SuggestBuyRecord;
import com.mensa.horsewebservice.service.GeneralReportServiceImpl;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class ImportSuggestBuyRecord02 {
    private static Logger log = LoggerFactory.getLogger(ImportSuggestBuyRecord01.class);
    private DecimalFormat decimalFormat = new DecimalFormat("000.00");
    private final String category = "Matt_suggest_02";
    private final BigDecimal basePrice = new BigDecimal("10"); 
    private final BigDecimal baseBigPrice = new BigDecimal("100"); 
    
    public void importSuggestBuyRecord()
    {
        FootballHandler recordHandler = new FootballHandler(); 
        SuggestBuyRecordHandler suggestHandler = new SuggestBuyRecordHandler(); 
        List<Football> records = recordHandler.GetAll(); 
        SuggestBuyRecord suggestBuyRecord = new SuggestBuyRecord(); 
        boolean isBreak = false; 
        for(Football record : records) {
            if ("202008".equals(record.getMatchIDinofficial().substring(0, 6))) continue; 
            
            // <editor-fold desc="High Low Goal - High 4.0 and odds <= 1.85">
            try {
                isBreak = false; 
                if (record.getHilodds() == null) isBreak = true; 
                if (record.getHilodds().getLINELIST() == null) isBreak = true; 

                if (!isBreak) {
                    for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
                    {
                        BigDecimal highDefine1 = BigDecimal.ZERO; 
                        BigDecimal highDefine2 = BigDecimal.ZERO; 

                        try {
                            highDefine1 = new BigDecimal(detail.getLINE()); 
                            highDefine2 = new BigDecimal(detail.getLINE()); 
                        } catch (Exception ex) {
                            highDefine1 = new BigDecimal(detail.getLINE().substring(0, 3)); 
                            highDefine2 = new BigDecimal(detail.getLINE().substring(4)); 
                        }
                        if (highDefine2.compareTo(new BigDecimal("4.0")) < 0) isBreak = true; 
                        if (highDefine2.compareTo(new BigDecimal("4.0")) > 0) isBreak = true; 

                        suggestBuyRecord = new SuggestBuyRecord(); 
                        suggestBuyRecord.setCategory(category);
                        suggestBuyRecord.setSubCategory("入球大: 球數=4.0和賠率<=1.85");
                        suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                        suggestBuyRecord.setMatchID(record.getMatchID());
                        suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                        suggestBuyRecord.setMatchDay(record.getMatchDay());
                        suggestBuyRecord.setMatchNum(record.getMatchNum());
                        suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                        suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                        suggestBuyRecord.setCreated_at(LocalDateTime.now());
                        suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                        SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                        if (tempSuggestBuyRecord != null) {
                            suggestBuyRecord = tempSuggestBuyRecord;
                        } else {
                            suggestBuyRecord.setRule(detail.getLINE());
                            suggestBuyRecord.setOdds(new BigDecimal(detail.getH().substring(4)));
                            if (suggestBuyRecord.getOdds().compareTo(new BigDecimal("1.85")) > 0) isBreak = true; 
                            suggestBuyRecord.setMatchResult("");
                            suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                            if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                        }


                        if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                        if (record.getResults() != null) {
                            if (record.getResults().getCRS() != null) {
                                suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                                if ("RFD".equals(record.getResults().getCRS())) {
                                    suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                                } else {
                                    BigDecimal result = BigDecimal.ZERO; 
                                    try {
                                        result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                                    } catch (Exception ex) {
                                         isBreak = true; 
                                    }
                                    if (result.compareTo(highDefine1) > 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                                    } else if (result.compareTo(highDefine2) == 0 && result.compareTo(highDefine1) > 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice).divide(new BigDecimal("2")));
                                    } else if (result.compareTo(highDefine1) < 0) {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                                    } else {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                                    }
                                }
                                if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("insert High Low Goal - High", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="High Low Goal - High 5.5">
            try {
                isBreak = false; 
                if (record.getHilodds() == null) isBreak = true; 
                if (record.getHilodds().getLINELIST() == null) isBreak = true; 

                if (!isBreak) {
                    for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
                    {
                        BigDecimal highDefine1 = BigDecimal.ZERO; 
                        BigDecimal highDefine2 = BigDecimal.ZERO; 

                        try {
                            highDefine1 = new BigDecimal(detail.getLINE()); 
                            highDefine2 = new BigDecimal(detail.getLINE()); 
                        } catch (Exception ex) {
                            highDefine1 = new BigDecimal(detail.getLINE().substring(0, 3)); 
                            highDefine2 = new BigDecimal(detail.getLINE().substring(4)); 
                        }
                        if (highDefine2.compareTo(new BigDecimal("5.5")) < 0) isBreak = true; 

                        suggestBuyRecord = new SuggestBuyRecord(); 
                        suggestBuyRecord.setCategory(category);
                        suggestBuyRecord.setSubCategory("入球大: 球數＞=5.5");
                        suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                        suggestBuyRecord.setMatchID(record.getMatchID());
                        suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                        suggestBuyRecord.setMatchDay(record.getMatchDay());
                        suggestBuyRecord.setMatchNum(record.getMatchNum());
                        suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                        suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                        suggestBuyRecord.setCreated_at(LocalDateTime.now());
                        suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                        SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                        if (tempSuggestBuyRecord != null) {
                            suggestBuyRecord = tempSuggestBuyRecord;
                        } else {
                            suggestBuyRecord.setRule(detail.getLINE());
                            suggestBuyRecord.setOdds(new BigDecimal(detail.getH().substring(4)));
                            suggestBuyRecord.setMatchResult("");
                            suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                            if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                        }

                        if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                        if (record.getResults() != null) {
                            if (record.getResults().getCRS() != null) {
                                suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                                if ("RFD".equals(record.getResults().getCRS())) {
                                    suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                                } else {
                                    BigDecimal result = BigDecimal.ZERO; 
                                    try {
                                        result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                                    } catch (Exception ex) {
                                         isBreak = true; 
                                    }
                                    if (result.compareTo(highDefine1) > 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                                    } else if (result.compareTo(highDefine2) == 0 && result.compareTo(highDefine1) > 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice).divide(new BigDecimal("2")));
                                    } else if (result.compareTo(highDefine1) < 0) {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                                    } else {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                                    }
                                }
                                if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("insert High Low Goal - High", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="High Low Goal - Low 2.0 and odds <= 1.55">
            try {
                isBreak = false; 
                if (record.getHilodds() == null) isBreak = true; 
                if (record.getHilodds().getLINELIST() == null) isBreak = true; 

                if (!isBreak) {
                    for(GoalHighLowDetail detail : record.getHilodds().getLINELIST())
                    {

                        BigDecimal lowDefine1 = BigDecimal.ZERO; 
                        BigDecimal lowDefine2 = BigDecimal.ZERO; 

                        try {
                            lowDefine1 = new BigDecimal(detail.getLINE()); 
                            lowDefine2 = new BigDecimal(detail.getLINE()); 
                        } catch (Exception ex) {
                            lowDefine1 = new BigDecimal(detail.getLINE().substring(0, 3)); 
                            lowDefine2 = new BigDecimal(detail.getLINE().substring(4)); 
                        }
                        if (lowDefine2.compareTo(new BigDecimal("1.5")) > 0) isBreak = true; 

                        suggestBuyRecord = new SuggestBuyRecord(); 
                        suggestBuyRecord.setCategory(category);
                        suggestBuyRecord.setSubCategory("入球細: 球數<=1.5");
                        suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                        suggestBuyRecord.setMatchID(record.getMatchID());
                        suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                        suggestBuyRecord.setMatchDay(record.getMatchDay());
                        suggestBuyRecord.setMatchNum(record.getMatchNum());
                        suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                        suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                        suggestBuyRecord.setCreated_at(LocalDateTime.now());
                        suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                        SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                        if (tempSuggestBuyRecord != null) {
                            suggestBuyRecord = tempSuggestBuyRecord;
                        } else {
                            suggestBuyRecord.setRule(detail.getLINE());
                            suggestBuyRecord.setOdds(new BigDecimal(detail.getL().substring(4)));
                            if (suggestBuyRecord.getOdds().compareTo(new BigDecimal("1.55")) > 0) isBreak = true; 
                            suggestBuyRecord.setMatchResult("");
                            suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                            if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                        }


                        if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                        if (record.getResults() != null) {
                            if (record.getResults().getCRS() != null) {
                                suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                                if ("RFD".equals(record.getResults().getCRS())) {
                                    suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                                } else {
                                    BigDecimal result = BigDecimal.ZERO; 
                                    try {
                                        result = new BigDecimal(record.getResults().getTTG().replace("+", "").replace("-", ""));
                                    } catch (Exception ex) {
                                         isBreak = true; 
                                    }
                                    if (result.compareTo(lowDefine2) < 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                                    } else if (result.compareTo(lowDefine2) == 0 && result.compareTo(lowDefine1) < 0) {
                                        suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice).divide(new BigDecimal("2")));
                                    } else if (result.compareTo(lowDefine2) > 0) {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                                    } else {
                                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                                    }
                                }
                                if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("insert High Low Goal - Low 2.0 and odds <= 1.55", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Home Away Draw - Home 1.2">
            try {
                isBreak = false; 
                if (record.getHadodds() == null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getHadodds().getH().substring(4)); 
                    if (odds.compareTo(new BigDecimal("1.2")) > 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("主客和 - 主: 賠率<=1.2");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(new BigDecimal(record.getHadodds().getH().substring(4)));
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getHAD(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("H".equals(result)) {
                                suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Home Away Draw - Home 1.2", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Home Away Draw - Away 1.25">
            try {
                isBreak = false; 
                if (record.getHadodds() == null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getHadodds().getA().substring(4)); 
                    if (odds.compareTo(new BigDecimal("1.25")) > 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("主客和 - 客: 賠率<=1.25");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(new BigDecimal(record.getHadodds().getA().substring(4)));
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getHAD(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("A".equals(result)) {
                                suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("insert Home Away Draw - Away 1.25", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="First Home Away Draw - Home 1.6">
            try {
                isBreak = false; 
                if (record.getFhaodds() == null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getFhaodds().getH().substring(4)); 
                    if (odds.compareTo(new BigDecimal("1.6")) > 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("半場主客和 - 主: 賠率<=1.6");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(new BigDecimal(record.getFhaodds().getH().substring(4)));
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getHAD(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("H".equals(result)) {
                                suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert First Home Away Draw - Home 1.6", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="First Home Away Draw - Away 1.55">
            try {
                isBreak = false; 
                if (record.getFhaodds() == null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getFhaodds().getA().substring(4)); 
                    if (odds.compareTo(new BigDecimal("1.55")) > 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("半場主客和 - 客: 賠率<=1.55");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(new BigDecimal(record.getFhaodds().getA().substring(4)));
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getHAD(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("A".equals(result)) {
                                suggestBuyRecord.setWinPrice(baseBigPrice.multiply(suggestBuyRecord.getOdds()).subtract(baseBigPrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(baseBigPrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }               
                }
            } catch (Exception ex) {
                log.error("insert First Home Away Draw - Away 1.55", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Correct Score - 00:00 12.5">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0000().substring(4)); 
                    if (odds.compareTo(new BigDecimal("12.5")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("12.5")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 00:00: 賠率=12.5");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("00:00".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score - 00:00 12.5", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Correct Score - 00:00 13.0">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0000().substring(4)); 
                    if (odds.compareTo(new BigDecimal("13.0")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("13.0")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 00:00: 賠率=12.5");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("00:00".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score - 00:00 13.0", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Correct Score - 04:01 30.0">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0401().substring(4)); 
                    if (odds.compareTo(new BigDecimal("30.0")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("30.0")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 04:01: 賠率=30.0");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("04:01".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Correct Score - 00:01 12.0">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0001().substring(4)); 
                    if (odds.compareTo(new BigDecimal("12.0")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("12.0")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 00:01: 賠率=12.0");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("00:01".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score", ex);
            }
            
            
            // </editor-fold>

            // <editor-fold desc="Correct Score - 00:02 10.0 - 12.0">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0002().substring(4)); 
                    if (odds.compareTo(new BigDecimal("12.0")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("10.0")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 00:02: 賠率=10.0 - 12.0");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("00:02".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score", ex);
            }
            
            
            // </editor-fold>
        
            // <editor-fold desc="Correct Score - 01:03 11.0 - 11.5">
            try {
                isBreak = false; 
                if (record.getCrsodds()== null) isBreak = true; 

                if (!isBreak) {
                    BigDecimal odds = new BigDecimal(record.getCrsodds().getS0103().substring(4)); 
                    if (odds.compareTo(new BigDecimal("11.5")) > 0) isBreak = true; 
                    if (odds.compareTo(new BigDecimal("11.0")) < 0) isBreak = true; 

                    suggestBuyRecord = new SuggestBuyRecord(); 
                    suggestBuyRecord.setCategory(category);
                    suggestBuyRecord.setSubCategory("波膽 - 01:03: 賠率=11.0 - 11.5");
                    suggestBuyRecord.setDescription(suggestBuyRecord.getSubCategory());
                    suggestBuyRecord.setMatchID(record.getMatchID());
                    suggestBuyRecord.setMatchIDinofficial(record.getMatchIDinofficial());
                    suggestBuyRecord.setMatchDay(record.getMatchDay());
                    suggestBuyRecord.setMatchNum(record.getMatchNum());
                    suggestBuyRecord.setHomeTeam(record.getHomeTeam().getTeamNameCH());
                    suggestBuyRecord.setAwayTeam(record.getAwayTeam().getTeamNameCH());
                    suggestBuyRecord.setCreated_at(LocalDateTime.now());
                    suggestBuyRecord.setUpdated_at(LocalDateTime.now());

                    SuggestBuyRecord tempSuggestBuyRecord = suggestHandler.Get(suggestBuyRecord); 
                    if (tempSuggestBuyRecord != null) {
                        suggestBuyRecord = tempSuggestBuyRecord;
                    } else {
                        suggestBuyRecord.setRule("");
                        suggestBuyRecord.setOdds(odds);
                        suggestBuyRecord.setMatchResult("");
                        suggestBuyRecord.setWinPrice(BigDecimal.ZERO);
                        if (!isBreak) suggestBuyRecord = suggestHandler.CreateAndReturn(suggestBuyRecord); 
                    }

                    if (suggestBuyRecord.getMatchResult() != "") isBreak = true; 
                    if (record.getResults() != null) {
                        if (record.getResults().getCRS() != null) {
                            suggestBuyRecord.setMatchResult(record.getResults().getCRS());

                            String result = ""; 
                            try {
                                result = record.getResults().getCRS(); 
                            } catch (Exception ex) {
                                 isBreak = true; 
                            }
                            if ("RFD".equals(result)) {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO); 
                            } else if ("01:03".equals(result)) {
                                suggestBuyRecord.setWinPrice(basePrice.multiply(suggestBuyRecord.getOdds()).subtract(basePrice));
                            } else {
                                suggestBuyRecord.setWinPrice(BigDecimal.ZERO.subtract(basePrice)); 
                            }
                            if (!isBreak) suggestHandler.Update(suggestBuyRecord); 
                        }
                    }                
                }
            } catch (Exception ex) {
                log.error("insert Correct Score", ex);
            }
            
            
            // </editor-fold>
        
        }
    }
    
}
