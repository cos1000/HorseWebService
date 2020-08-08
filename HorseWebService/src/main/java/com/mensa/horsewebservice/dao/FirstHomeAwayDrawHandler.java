/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FirstHomeAwayDraw;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FirstHomeAwayDrawHandler implements IHandler<FirstHomeAwayDraw> {
    private RecordHandler<FirstHomeAwayDraw> handler;
    
    public FirstHomeAwayDrawHandler() {
        handler = new RecordHandler<FirstHomeAwayDraw>(); 
    }
    
    @Override
    public boolean Exist(FirstHomeAwayDraw record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FirstHomeAwayDraw> GetAll() {
        String sql = "select record from FirstHomeAwayDraw record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FirstHomeAwayDraw Get(FirstHomeAwayDraw record) {
        String sql = "select record from FirstHomeAwayDraw record where record.ID = :ID";
        List<FirstHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstHomeAwayDraw GetById(long id) {
        String sql = "select record from FirstHomeAwayDraw record where record.idKey = :idKey";
        List<FirstHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstHomeAwayDraw CreateAndReturn(FirstHomeAwayDraw record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FirstHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FirstHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FirstHomeAwayDraw record) {
        return handler.Delete(record); 
    }

    
}
