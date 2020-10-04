/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice;

import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.model.Football.Football;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author matt_
 */
public class Exporter {
    private static Logger log = LogManager.getLogger(Exporter.class);
    
    public boolean export() {
        try {
            try (Workbook workbook = new XSSFWorkbook()) {
                int rowNum = 0;
                CreationHelper createHelper = workbook.getCreationHelper();
                Sheet sheet = workbook.createSheet("Result");
                Row headerRow = sheet.createRow(rowNum++);
                Row headerRow2 = sheet.createRow(rowNum++);
                exportHeader(headerRow, headerRow2); 
                
                FootballHandler footballHandler = new FootballHandler(); 
                List<Football> footballList = footballHandler.GetAll();
                
                int cellCounter = 0;
                int lastCellCounter = 0; 
                for(int counter = 0; counter < footballList.size(); counter++) {
                    Row currentRow = sheet.createRow(rowNum++);
                    cellCounter = 0;
                    
                    currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getMatchDay() + footballList.get(counter).getMatchNum());
                    currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getLeague().getLeagueNameCH());
                    currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getMatchTime());
                    currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHomeTeam().getTeamNameCH());
                    currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getAwayTeam().getTeamNameCH());

                    if (footballList.get(counter).getResults() != null) {
                        if (footballList.get(counter).getResults().getHAD() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getHAD().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getFHA() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getFHA().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getCRS() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getCRS().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getFCS() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getFCS().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getHFT() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getHFT().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getTTG() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getTTG().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getCornerresult() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCornerresult().replace("100@", "")); else cellCounter++; 
                        if (footballList.get(counter).getResults().getOOE() != null) currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getResults().getOOE().replace("100@", "")); else cellCounter++; 
                    }
                    
                    if (footballList.get(counter).getHadodds() != null) {
//log.info("Export Step 01");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHadodds().getH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHadodds().getA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHadodds().getD().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getFhaodds() != null) {
//log.info("Export Step 02");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhaodds().getH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhaodds().getA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhaodds().getD().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getHhaodds() != null) {
//log.info("Export Step 03");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHhaodds().getHG().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHhaodds().getAG().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHhaodds().getH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHhaodds().getA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHhaodds().getD().replace("100@", ""));
                    } else {
                        cellCounter += 5; 
                    }

                    if (footballList.get(counter).getHdcodds() != null) {
//log.info("Export Step 04");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHdcodds().getHG().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHdcodds().getAG().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHdcodds().getH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHdcodds().getA().replace("100@", ""));
                    } else {
                        cellCounter += 4; 
                    }

                    if (footballList.get(counter).getHilodds() != null && footballList.get(counter).getHilodds().getLINELIST() != null && footballList.get(counter).getHilodds().getLINELIST().size() >= 3) {
//log.info("Export Step 05");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(0).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(0).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(0).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getHilodds() != null && footballList.get(counter).getHilodds().getLINELIST() != null && footballList.get(counter).getHilodds().getLINELIST().size() >= 3) {
//log.info("Export Step 06");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(1).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(1).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(1).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getHilodds() != null && footballList.get(counter).getHilodds().getLINELIST() != null && footballList.get(counter).getHilodds().getLINELIST().size() >= 3) {
//log.info("Export Step 07");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(2).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(2).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHilodds().getLINELIST().get(2).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getFhlodds() != null && footballList.get(counter).getFhlodds().getLINELIST() != null && footballList.get(counter).getFhlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 08");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(0).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(0).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(0).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getFhlodds() != null && footballList.get(counter).getFhlodds().getLINELIST() != null && footballList.get(counter).getFhlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 09");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(1).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(1).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(1).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getFhlodds() != null && footballList.get(counter).getFhlodds().getLINELIST() != null && footballList.get(counter).getFhlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 10");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(2).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(2).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFhlodds().getLINELIST().get(2).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getChlodds() != null && footballList.get(counter).getChlodds().getLINELIST() != null && footballList.get(counter).getChlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 11");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(0).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(0).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(0).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getChlodds() != null && footballList.get(counter).getChlodds().getLINELIST() != null && footballList.get(counter).getChlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 12");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(1).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(1).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(1).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getChlodds() != null && footballList.get(counter).getChlodds().getLINELIST() != null && footballList.get(counter).getChlodds().getLINELIST().size() >= 3) {
//log.info("Export Step 13");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(2).getLINE().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(2).getL().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getChlodds().getLINELIST().get(2).getH().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getCrsodds() != null) {
//log.info("Export Step 14");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0100().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0200().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0201().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0300().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0301().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0302().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0400().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0401().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0402().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0500().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0501().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0502().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getSM1MH().replace("100@", ""));

                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0001().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0002().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0102().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0003().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0103().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0203().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0004().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0104().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0204().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0005().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0105().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0205().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getSM1MA().replace("100@", ""));

                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0000().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0101().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0202().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getS0303().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getCrsodds().getSM1MD().replace("100@", ""));
                    } else {
                        cellCounter += 31; 
                    }

                    if (footballList.get(counter).getFcsodds() != null) {
//log.info("Export Step 14");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0100().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0200().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0201().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0300().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0301().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0302().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0400().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0401().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0402().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0500().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0501().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0502().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getSM1MH().replace("100@", ""));

                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0001().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0002().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0102().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0003().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0103().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0203().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0004().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0104().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0204().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0005().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0105().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0205().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getSM1MA().replace("100@", ""));

                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0000().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0101().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0202().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getS0303().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFcsodds().getSM1MD().replace("100@", ""));
                    } else {
                        cellCounter += 31; 
                    }

                    if (footballList.get(counter).getFtsodds() != null) {
//log.info("Export Step 15");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFtsodds().getH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFtsodds().getA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getFtsodds().getN().replace("100@", ""));
                    } else {
                        cellCounter += 3; 
                    }

                    if (footballList.get(counter).getTtgodds() != null) {
//log.info("Export Step 16");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP1().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP2().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP3().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP4().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP5().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getP6().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getTtgodds().getM7().replace("100@", ""));
                    } else {
                        cellCounter += 7; 
                    }

                    if (footballList.get(counter).getOoeodds() != null) {
//log.info("Export Step 17");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getOoeodds().getO().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getOoeodds().getE().replace("100@", ""));
                    } else {
                        cellCounter += 2; 
                    }

                    if (footballList.get(counter).getHftodds() != null) {
//log.info("Export Step 18");
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getHH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getHA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getHD().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getAH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getAA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getAD().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getDH().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getDA().replace("100@", ""));
                        currentRow.createCell(cellCounter++).setCellValue(footballList.get(counter).getHftodds().getDD().replace("100@", ""));
                    } else {
                        cellCounter += 9; 
                    }
                    lastCellCounter = cellCounter -1; 
                }

                for(int counter = 0; counter < lastCellCounter; counter++) {
                    sheet.autoSizeColumn(counter);
                }
                
                try (FileOutputStream fileOut = new FileOutputStream("/opt/tomcat/latest/logs/result.xlsx", false)) {
                    workbook.write(fileOut);
                }
            }
log.info("Exported");             
            return true; 
        } catch (Exception e) {
            log.error("Error", e); 
            return false; 
        }
    }
    
    private void exportHeader(Row headerRow, Row headerRow2) {
        int cellCounter = 0; 
        int startCellCounter = 0; 
        headerRow2.createCell(cellCounter++).setCellValue("比賽編號");
        headerRow2.createCell(cellCounter++).setCellValue("比賽類別");
        headerRow2.createCell(cellCounter++).setCellValue("比賽時間");
        headerRow2.createCell(cellCounter++).setCellValue("主場");
        headerRow2.createCell(cellCounter++).setCellValue("客場");

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("賽果");
        headerRow2.createCell(cellCounter++).setCellValue("主客和");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("半場主客和");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("波膽");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("半場波膽");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("半全場");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("總入球");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("角球總數");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("入球單雙");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("主客和");
        headerRow2.createCell(cellCounter++).setCellValue("主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場主客和");
        headerRow2.createCell(cellCounter++).setCellValue("主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("讓球主客和");
        headerRow2.createCell(cellCounter++).setCellValue("主讓球數");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客讓球數");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("讓球");
        headerRow2.createCell(cellCounter++).setCellValue("主讓球數");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客讓球數");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("入球大細 (1)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("入球大細 (2)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("入球大細 (3)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場入球大細 (1)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場入球大細 (2)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場入球大細 (3)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("角球大細 (1)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("角球大細 (2)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));

        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("角球大細 (3)");
        headerRow2.createCell(cellCounter++).setCellValue("界線");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("細");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("大");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("波膽 (主場)");
        headerRow2.createCell(cellCounter++).setCellValue("01:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("波膽 (客場)");
        headerRow2.createCell(cellCounter++).setCellValue("00:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("波膽 (和)");
        headerRow2.createCell(cellCounter++).setCellValue("00:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場波膽 (主場)");
        headerRow2.createCell(cellCounter++).setCellValue("01:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("04:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("05:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場波膽 (客場)");
        headerRow2.createCell(cellCounter++).setCellValue("00:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:04");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("00:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:05");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半場波膽 (和)");
        headerRow2.createCell(cellCounter++).setCellValue("00:00");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("01:01");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("02:02");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("03:03");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和其他");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("首入球");
        headerRow2.createCell(cellCounter++).setCellValue("主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("無");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("總入球");
        headerRow2.createCell(cellCounter++).setCellValue("1");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("2");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("3");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("4");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("5");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("6");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("7");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("單雙");
        headerRow2.createCell(cellCounter++).setCellValue("單");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("雙");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
        startCellCounter = cellCounter; 
        headerRow.createCell(cellCounter).setCellValue("半全場");
        headerRow2.createCell(cellCounter++).setCellValue("主主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("主和");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("客和");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和主");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和客");
        headerRow.createCell(cellCounter).setCellValue("");
        headerRow2.createCell(cellCounter++).setCellValue("和和");
        headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), startCellCounter, cellCounter -1));
        
    }
    
}
