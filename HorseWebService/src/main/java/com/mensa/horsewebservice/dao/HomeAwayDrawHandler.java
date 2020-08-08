/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.HomeAwayDraw;
import java.util.List;

/**
 *
 * @author matt_
 */
public class HomeAwayDrawHandler implements IHandler<HomeAwayDraw> {
    private RecordHandler<HomeAwayDraw> handler;
    
    public HomeAwayDrawHandler() {
        handler = new RecordHandler<HomeAwayDraw>(); 
    }
    
    @Override
    public boolean Exist(HomeAwayDraw record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<HomeAwayDraw> GetAll() {
        String sql = "select record from HomeAwayDraw record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public HomeAwayDraw Get(HomeAwayDraw record) {
        String sql = "select record from HomeAwayDraw record where record.ID = :ID";
        List<HomeAwayDraw> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HomeAwayDraw GetById(long id) {
        String sql = "select record from HomeAwayDraw record where record.idKey = :idKey";
        List<HomeAwayDraw> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HomeAwayDraw CreateAndReturn(HomeAwayDraw record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(HomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(HomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(HomeAwayDraw record) {
        return handler.Delete(record); 
    }

    
}
