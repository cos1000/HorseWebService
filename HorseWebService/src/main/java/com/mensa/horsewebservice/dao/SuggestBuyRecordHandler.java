/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.SuggestBuyRecord;
import java.util.List;

/**
 *
 * @author matt_
 */
public class SuggestBuyRecordHandler implements IHandler<SuggestBuyRecord> {
    private RecordHandler<SuggestBuyRecord> handler;
    
    public SuggestBuyRecordHandler() {
        handler = new RecordHandler<SuggestBuyRecord>(); 
    }
    
    @Override
    public boolean Exist(SuggestBuyRecord record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<SuggestBuyRecord> GetAll() {
        String sql = "select record from SuggestBuyRecord record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    public List<SuggestBuyRecord> GetAll(String category) {
        String sql = "select record from SuggestBuyRecord record where record.category = :category ";
        return handler.GetQuery(sql)
                .setParameter("category", category)
                .getResultList(); 
    }
    
    public List<SuggestBuyRecord> GetAll(String category, String subCategory) {
        String sql = "select record from SuggestBuyRecord record where record.category = :category and record.subCategory = :subCategory ";
        return handler.GetQuery(sql)
                .setParameter("category", category)
                .setParameter("subCategory", subCategory)
                .getResultList(); 
    }
    
    @Override
    public SuggestBuyRecord Get(SuggestBuyRecord record) {
        String sql = "select record from SuggestBuyRecord record where record.category = :category and record.subCategory = :subCategory and record.matchID = :matchID";
        List<SuggestBuyRecord> answer = handler.GetQuery(sql)
                .setParameter("category", record.getCategory())
                .setParameter("subCategory", record.getSubCategory())
                .setParameter("matchID", record.getMatchID())
                .setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public SuggestBuyRecord GetById(long id) {
        String sql = "select record from SuggestBuyRecord record where record.idKey = :idKey";
        List<SuggestBuyRecord> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public SuggestBuyRecord CreateAndReturn(SuggestBuyRecord record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(SuggestBuyRecord record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(SuggestBuyRecord record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(SuggestBuyRecord record) {
        return handler.Delete(record); 
    }

}
