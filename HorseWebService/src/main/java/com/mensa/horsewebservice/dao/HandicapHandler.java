/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.Handicap;
import java.util.List;

/**
 *
 * @author matt_
 */
public class HandicapHandler implements IHandler<Handicap> {
    private RecordHandler<Handicap> handler;
    
    public HandicapHandler() {
        handler = new RecordHandler<Handicap>(); 
    }
    
    @Override
    public boolean Exist(Handicap record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<Handicap> GetAll() {
        String sql = "select record from Handicap record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public Handicap Get(Handicap record) {
        String sql = "select record from Handicap record where record.ID = :ID";
        List<Handicap> answer = handler.GetQuery(sql).setParameter("ID", record.getID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Handicap GetById(long id) {
        String sql = "select record from Handicap record where record.idKey = :idKey";
        List<Handicap> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Handicap CreateAndReturn(Handicap record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(Handicap record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(Handicap record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(Handicap record) {
        return handler.Delete(record); 
    }

    
}
