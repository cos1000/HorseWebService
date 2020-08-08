/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.TotalGoal;
import java.util.List;

/**
 *
 * @author matt_
 */
public class TotalGoalHandler implements IHandler<TotalGoal> {
    private RecordHandler<TotalGoal> handler;
    
    public TotalGoalHandler() {
        handler = new RecordHandler<TotalGoal>(); 
    }
    
    @Override
    public boolean Exist(TotalGoal record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<TotalGoal> GetAll() {
        String sql = "select record from TotalGoal record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public TotalGoal Get(TotalGoal record) {
        String sql = "select record from TotalGoal record where record.ID = :ID";
        List<TotalGoal> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TotalGoal GetById(long id) {
        String sql = "select record from TotalGoal record where record.idKey = :idKey";
        List<TotalGoal> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public TotalGoal CreateAndReturn(TotalGoal record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(TotalGoal record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(TotalGoal record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(TotalGoal record) {
        return handler.Delete(record); 
    }

}
