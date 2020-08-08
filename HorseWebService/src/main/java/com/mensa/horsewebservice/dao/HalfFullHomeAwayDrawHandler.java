/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.HalfFullHomeAwayDraw;
import java.util.List;

/**
 *
 * @author matt_
 */
public class HalfFullHomeAwayDrawHandler implements IHandler<HalfFullHomeAwayDraw> {
    private RecordHandler<HalfFullHomeAwayDraw> handler;
    
    public HalfFullHomeAwayDrawHandler() {
        handler = new RecordHandler<HalfFullHomeAwayDraw>(); 
    }
    
    @Override
    public boolean Exist(HalfFullHomeAwayDraw record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<HalfFullHomeAwayDraw> GetAll() {
        String sql = "select record from HalfFullHomeAwayDraw record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public HalfFullHomeAwayDraw Get(HalfFullHomeAwayDraw record) {
        String sql = "select record from HalfFullHomeAwayDraw record where record.ID = :ID";
        List<HalfFullHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HalfFullHomeAwayDraw GetById(long id) {
        String sql = "select record from HalfFullHomeAwayDraw record where record.idKey = :idKey";
        List<HalfFullHomeAwayDraw> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public HalfFullHomeAwayDraw CreateAndReturn(HalfFullHomeAwayDraw record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(HalfFullHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(HalfFullHomeAwayDraw record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(HalfFullHomeAwayDraw record) {
        return handler.Delete(record); 
    }

    
}
