/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice.model.Football.Report;

import java.math.BigDecimal;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *
 * @author matt_
 */
@Data
public class GeneralResult {
    private String key; 
    private String description; 
    private int numberOfRecord; 
    private int winNumberOfRecord; 
    private int lostNumberOfRecord; 
    private int winPercentage; 
    private int lostPercentage; 
    private BigDecimal winPrice; 
    private int accNumberOfRecord; 
    private int accWinNumberOfRecord; 
    private int accLostNumberOfRecord; 
    private int accWinPercentage; 
    private int accLostPercentage; 
    private BigDecimal accWinPrice; 
}
