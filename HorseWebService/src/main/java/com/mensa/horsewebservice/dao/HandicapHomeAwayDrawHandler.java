/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.HandicapHomeAwayDraw;
import java.util.List;

/**
 *
 * @author matt_
 */
public class HandicapHomeAwayDrawHandler implements IHandler<HandicapHomeAwayDraw> {
    private RecordHandler<HandicapHomeAwayDraw> handler;
    
    public HandicapHomeAwayDrawHandler() {
        handler = new RecordHandler<HandicapHomeAwayDraw>(); 
    }
    
    @Override
    public boolean Exist(HandicapHomeAwayDraw record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<HandicapHomeAwayDraw> GetAll() {
        String sql = "select record from HandicapHomeAwayDraw record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public HandicapHomeAwayDraw Get(HandicapHomeAwayDraw record) {
        String sql = "select record from HandicapHomeAwayDraw record where record.ID = :ID";
        List<HandicapHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HandicapHomeAwayDraw GetById(long id) {
        String sql = "select record from HandicapHomeAwayDraw record where record.idKey = :idKey";
        List<HandicapHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HandicapHomeAwayDraw CreateAndReturn(HandicapHomeAwayDraw record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(HandicapHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(HandicapHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(HandicapHomeAwayDraw record) {
        return handler.Delete(record); 
    }

    
}
