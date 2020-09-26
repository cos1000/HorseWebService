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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author matt_
 */
@Controller
public class GeneralReportController {
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
        
        model.addAttribute("records", records);
        model.addAttribute("numberOfRecord", numberOfRecord);
        model.addAttribute("winNumberOfRecord", winNumberOfRecord);
        model.addAttribute("lostNumberOfRecord", lostNumberOfRecord);
        return "Suggest01";
    }
    
}
