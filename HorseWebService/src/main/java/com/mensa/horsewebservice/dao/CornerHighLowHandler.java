/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.CornerHighLow;
import java.util.List;

/**
 *
 * @author matt_
 */
public class CornerHighLowHandler implements IHandler<CornerHighLow> {
    private RecordHandler<CornerHighLow> handler;
    
    public CornerHighLowHandler() {
        handler = new RecordHandler<CornerHighLow>(); 
    }
    
    @Override
    public boolean Exist(CornerHighLow record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<CornerHighLow> GetAll() {
        String sql = "select record from CornerHighLow record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public CornerHighLow Get(CornerHighLow record) {
        String sql = "select record from CornerHighLow record where record.ID = :ID";
        List<CornerHighLow> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CornerHighLow GetById(long id) {
        String sql = "select record from CornerHighLow record where record.idKey = :idKey";
        List<CornerHighLow> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CornerHighLow CreateAndReturn(CornerHighLow record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(CornerHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(CornerHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(CornerHighLow record) {
        return handler.Delete(record); 
    }

    
}
