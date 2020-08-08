/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.OddEven;
import java.util.List;

/**
 *
 * @author matt_
 */
public class OddEvenHandler implements IHandler<OddEven> {
    private RecordHandler<OddEven> handler;
    
    public OddEvenHandler() {
        handler = new RecordHandler<OddEven>(); 
    }
    
    @Override
    public boolean Exist(OddEven record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<OddEven> GetAll() {
        String sql = "select record from OddEven record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public OddEven Get(OddEven record) {
        String sql = "select record from OddEven record where record.ID = :ID";
        List<OddEven> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public OddEven GetById(long id) {
        String sql = "select record from OddEven record where record.idKey = :idKey";
        List<OddEven> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public OddEven CreateAndReturn(OddEven record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(OddEven record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(OddEven record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(OddEven record) {
        return handler.Delete(record); 
    }

    
}
