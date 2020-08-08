/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.GoalHighLow;
import java.util.List;

/**
 *
 * @author matt_
 */
public class GoalHighLowHandler implements IHandler<GoalHighLow> {
    private RecordHandler<GoalHighLow> handler;
    
    public GoalHighLowHandler() {
        handler = new RecordHandler<GoalHighLow>(); 
    }
    
    @Override
    public boolean Exist(GoalHighLow record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<GoalHighLow> GetAll() {
        String sql = "select record from GoalHighLow record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public GoalHighLow Get(GoalHighLow record) {
        String sql = "select record from GoalHighLow record where record.ID = :ID";
        List<GoalHighLow> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public GoalHighLow GetById(long id) {
        String sql = "select record from GoalHighLow record where record.idKey = :idKey";
        List<GoalHighLow> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public GoalHighLow CreateAndReturn(GoalHighLow record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(GoalHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(GoalHighLow record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(GoalHighLow record) {
        return handler.Delete(record); 
    }

    
}
