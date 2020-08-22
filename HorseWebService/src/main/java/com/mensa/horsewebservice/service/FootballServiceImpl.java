/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.service;

import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.model.Football.Football;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matt_
 */
public class FootballServiceImpl implements IService<Football> {
    
    private static Logger log = LoggerFactory.getLogger(FootballServiceImpl.class);
    private FootballHandler recordHandler;

    public void setFootballHandler(FootballHandler handler)
    {
        this.recordHandler = handler; 
    }
    
    @Override
    public boolean add(Football record) {
        return recordHandler.Create(record); 
    }

    @Override
    public boolean update(Football record) {
        return recordHandler.Update(record); 
    }

    @Override
    public List<Football> list() {
        return recordHandler.GetAll(); 
    }

    @Override
    public Football get(Football record) {
        return recordHandler.Get(record); 
    }

    @Override
    public Football getById(int id) {
        return recordHandler.GetById(id); 
    }

    @Override
    public boolean delete(int id) {
        return recordHandler.Delete(getById(id)); 
    }
    
}
