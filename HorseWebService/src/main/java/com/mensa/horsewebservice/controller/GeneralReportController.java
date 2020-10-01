/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.controller;

import com.mensa.horsewebservice.service.GeneralReportServiceImpl;
import com.mensa.horsewebservice.model.Football.Report.GeneralResult; 
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author matt_
 */
@Controller
public class GeneralReportController {
    private static Logger log = LoggerFactory.getLogger(GeneralReportController.class);
    private GeneralReportServiceImpl service;
    
    @Autowired(required=true)
    @Qualifier(value="generalReportService")
    public void setGeneralReportService(GeneralReportServiceImpl value){
        this.service = value;
    }
    
    @RequestMapping(value = "/report/goalHigh", method = RequestMethod.GET)
    public String goalHigh(Model model) {
        List<GeneralResult> records = this.service.getGoalHigh(); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "入球大的報表");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
        
    @RequestMapping(value = "/report/goalLow", method = RequestMethod.GET)
    public String goalLow(Model model) {
        List<GeneralResult> records = this.service.getGoalLow(); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "入球細的報表");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/firstGoalHigh", method = RequestMethod.GET)
    public String firstGoalHigh(Model model) {
        List<GeneralResult> records = this.service.getFirstGoalHigh(); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "半場入球大的報表");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
        
    @RequestMapping(value = "/report/firstGoalLow", method = RequestMethod.GET)
    public String firstGoalLow(Model model) {
        List<GeneralResult> records = this.service.getGoalLow(); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "半場入球細的報表");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/homeAwayDrawHome", method = RequestMethod.GET)
    public String homeAwayDrawHome(Model model) {
        List<GeneralResult> records = this.service.getHomeAwayDraw("H"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "主客和- 主");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/homeAwayDrawAway", method = RequestMethod.GET)
    public String homeAwayDrawAway(Model model) {
        List<GeneralResult> records = this.service.getHomeAwayDraw("A"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "主客和 - 客");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/homeAwayDrawDraw", method = RequestMethod.GET)
    public String homeAwayDrawDraw(Model model) {
        List<GeneralResult> records = this.service.getHomeAwayDraw("D"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "主客和 - 和");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/firstHomeAwayDrawHome", method = RequestMethod.GET)
    public String firstHomeAwayDrawHome(Model model) {
        List<GeneralResult> records = this.service.getFirstHomeAwayDraw("H"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "半場主客和- 主");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/firstHomeAwayDrawAway", method = RequestMethod.GET)
    public String firstHomeAwayDrawAway(Model model) {
        List<GeneralResult> records = this.service.getFirstHomeAwayDraw("A"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "半場主客和 - 客");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/firstHomeAwayDrawDraw", method = RequestMethod.GET)
    public String firstHomeAwayDrawDraw(Model model) {
        List<GeneralResult> records = this.service.getFirstHomeAwayDraw("D"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "半場主客和 - 和");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/handicapHomeAwayDrawHome", method = RequestMethod.GET)
    public String handicapHomeAwayDrawHome(Model model) {
        List<GeneralResult> records = this.service.getHandicapHomeAwayDraw("H"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "讓球主客和- 主");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/handicapHomeAwayDrawAway", method = RequestMethod.GET)
    public String handicapHomeAwayDrawAway(Model model) {
        List<GeneralResult> records = this.service.getHandicapHomeAwayDraw("A"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "讓球主客和 - 客");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/handicapHomeAwayDrawDraw", method = RequestMethod.GET)
    public String handicapHomeAwayDrawDraw(Model model) {
        List<GeneralResult> records = this.service.getHandicapHomeAwayDraw("D"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "讓球主客和 - 和");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/handicapHome", method = RequestMethod.GET)
    public String handicapHome(Model model) {
        List<GeneralResult> records = this.service.getHandicap("H"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "讓球- 主");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/handicapAway", method = RequestMethod.GET)
    public String handicapAway(Model model) {
        List<GeneralResult> records = this.service.getHandicap("A"); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "讓球 - 客");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping(value = "/report/totalGoal", method = RequestMethod.GET)
    public String totalGoal(Model model) {
        List<GeneralResult> records = this.service.getTotalGoal(); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "總入球");
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }
    
    @RequestMapping("/report/correctScore/{checkingValue}")
    public String correctScore(@PathVariable String checkingValue, Model model) {
        List<GeneralResult> records = this.service.getCorrectScore(checkingValue); 
        int numberOfRecord = 0; 
        int winNumberOfRecord = 0; 
        int lostNumberOfRecord = 0; 
        
        for(GeneralResult record : records) {
            numberOfRecord += record.getNumberOfRecord(); 
            winNumberOfRecord += record.getWinNumberOfRecord(); 
            lostNumberOfRecord += record.getLostNumberOfRecord(); 
        }
        model.addAttribute("title", "波膽 - " + checkingValue);
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "generalReport";
    }

    
}
