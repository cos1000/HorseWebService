/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.FirstGoalHighLowDetail;
import java.util.List;

/**
 *
 * @author matt_
 */
public class FirstGoalHighLowDetailHandler implements IHandler<FirstGoalHighLowDetail> {
    private RecordHandler<FirstGoalHighLowDetail> handler;
    
    public FirstGoalHighLowDetailHandler() {
        handler = new RecordHandler<FirstGoalHighLowDetail>(); 
    }
    
    @Override
    public boolean Exist(FirstGoalHighLowDetail record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<FirstGoalHighLowDetail> GetAll() {
        String sql = "select record from FirstGoalHighLowDetail record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public FirstGoalHighLowDetail Get(FirstGoalHighLowDetail record) {
        String sql = "select record from FirstGoalHighLowDetail record where record.idKey = :idKey";
        List<FirstGoalHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", record.getIdKey()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstGoalHighLowDetail GetById(long id) {
        String sql = "select record from FirstGoalHighLowDetail record where record.idKey = :idKey";
        List<FirstGoalHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public FirstGoalHighLowDetail CreateAndReturn(FirstGoalHighLowDetail record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(FirstGoalHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(FirstGoalHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(FirstGoalHighLowDetail record) {
        return handler.Delete(record); 
    }

    
}
