/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.dao;

import com.mensa.horsewebservice.model.Football.Coupon;
import java.util.List;

/**
 *
 * @author matt_
 */
public class CouponHandler implements IHandler<Coupon> {
    private RecordHandler<Coupon> handler;
    
    public CouponHandler() {
        handler = new RecordHandler<Coupon>(); 
    }
    
    @Override
    public boolean Exist(Coupon record) {
        return Get(record) != null; 
    }
    
    @Override
    public List<Coupon> GetAll() {
        String sql = "select record from Coupon record";
        return handler.GetQuery(sql).getResultList(); 
    }
    
    @Override
    public Coupon Get(Coupon record) {
        String sql = "select record from Coupon record where record.couponID = :CouponID";
        List<Coupon> answer = handler.GetQuery(sql).setParameter("CouponID", record.getCouponID()).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Coupon GetById(long id) {
        String sql = "select record from Coupon record where record.idKey = :idKey";
        List<Coupon> answer = handler.GetQuery(sql).setParameter("idKey", id).setMaxResults(1).getResultList(); 
        if (answer != null && answer.size() > 0) {
            return answer.get(0); 
        } else {
            return null; 
        }
    }
    
    @Override
    public Coupon CreateAndReturn(Coupon record) {
        if (Create(record)) {
            return Get(record); 
        } else {
            return null; 
        }
    }
    
    @Override
    public boolean Create(Coupon record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Update(Coupon record) {
        return handler.CreateOrUpdate(record); 
    }
    
    @Override
    public boolean Delete(Coupon record) {
        return handler.Delete(record); 
    }

    
}
