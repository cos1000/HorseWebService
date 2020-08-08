/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.GoalHighLowDetail;
import java.util.List;

/**
 *
 * @author matt_
 */
public class GoalHighLowDetailHandler implements IHandler<GoalHighLowDetail> {
    private RecordHandler<GoalHighLowDetail> handler;
    
    public GoalHighLowDetailHandler() {
        handler = new RecordHandler<GoalHighLowDetail>(); 
    }
    
    @Override
    public boolean Exist(GoalHighLowDetail record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<GoalHighLowDetail> GetAll() {
        String sql = "select record from GoalHighLowDetail record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public GoalHighLowDetail Get(GoalHighLowDetail record) {
        String sql = "select record from GoalHighLowDetail record where record.idKey = :idKey";
        List<GoalHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", record.getIdKey()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public GoalHighLowDetail GetById(long id) {
        String sql = "select record from GoalHighLowDetail record where record.idKey = :idKey";
        List<GoalHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public GoalHighLowDetail CreateAndReturn(GoalHighLowDetail record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(GoalHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(GoalHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(GoalHighLowDetail record) {
        return handler.Delete(record); 
    }

    
}
