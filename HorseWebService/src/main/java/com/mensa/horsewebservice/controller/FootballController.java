/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.controller;

import com.mensa.horsewebservice.model.Football.Football;
import com.mensa.horsewebservice.service.FootballServiceImpl;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author matt_
 */
@Controller
public class FootballController implements IController<Football> {
    private static Logger log = LoggerFactory.getLogger(FootballController.class);
    private FootballServiceImpl service;
    
    @Autowired(required=true)
    @Qualifier(value="footballService")
    public void setFootballService(FootballServiceImpl value){
        this.service = value;
    }
    
    @Override
    @RequestMapping(value= "/football/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("record") Football record) {
        log.info("Start Adding" + record);
        log.info("Id: " + record.getIdKey()); 
        record.setCreated_at(LocalDateTime.now()); 
        record.setUpdated_at(LocalDateTime.now());
        this.service.add(record);

        return "redirect:/football";

    }

    @Override
    @RequestMapping("/football/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("record", this.service.getById(id));
        model.addAttribute("records", this.service.list());
        return "football";
    }

    @Override
    @RequestMapping(value = "/football", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("record", new Football());
        model.addAttribute("records", this.service.list());
        return "football";
    }

    @Override
    @RequestMapping("/football/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        this.service.delete(id);
        return "redirect:/football";
    }
    
}
