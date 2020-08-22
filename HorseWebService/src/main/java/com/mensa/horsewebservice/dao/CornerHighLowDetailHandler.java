/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.CornerHighLowDetail;
import java.util.List;

/**
 *
 * @author matt_
 */
public class CornerHighLowDetailHandler  implements IHandler<CornerHighLowDetail> {
    private RecordHandler<CornerHighLowDetail> handler;
    
    public CornerHighLowDetailHandler() {
        handler = new RecordHandler<CornerHighLowDetail>(); 
    }
    
    @Override
    public boolean Exist(CornerHighLowDetail record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<CornerHighLowDetail> GetAll() {
        String sql = "select record from CornerHighLowDetail record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public CornerHighLowDetail Get(CornerHighLowDetail record) {
        String sql = "select record from CornerHighLowDetail record where record.idKey = :idKey";
        List<CornerHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", record.getIdKey()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CornerHighLowDetail GetById(long id) {
        String sql = "select record from CornerHighLowDetail record where record.idKey = :idKey";
        List<CornerHighLowDetail> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public CornerHighLowDetail CreateAndReturn(CornerHighLowDetail record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(CornerHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(CornerHighLowDetail record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(CornerHighLowDetail record) {
        return handler.Delete(record); 
    }

    
    
}
