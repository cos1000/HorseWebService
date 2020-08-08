/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FirstGoalHighLow;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FirstGoalHighLowHandler implements IHandler<FirstGoalHighLow> {
    private RecordHandler<FirstGoalHighLow> handler;
    
    public FirstGoalHighLowHandler() {
        handler = new RecordHandler<FirstGoalHighLow>(); 
    }
    
    @Override
    public boolean Exist(FirstGoalHighLow record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FirstGoalHighLow> GetAll() {
        String sql = "select record from FirstGoalHighLow record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FirstGoalHighLow Get(FirstGoalHighLow record) {
        String sql = "select record from FirstGoalHighLow record where record.ID = :ID";
        List<FirstGoalHighLow> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstGoalHighLow GetById(long id) {
        String sql = "select record from FirstGoalHighLow record where record.idKey = :idKey";
        List<FirstGoalHighLow> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstGoalHighLow CreateAndReturn(FirstGoalHighLow record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FirstGoalHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FirstGoalHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FirstGoalHighLow record) {
        return handler.Delete(record); 
    }

    
}
