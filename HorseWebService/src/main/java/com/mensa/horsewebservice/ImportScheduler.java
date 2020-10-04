/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mensa.horsewebservice;

import com.google.gson.Gson;
import com.mensa.horsewebservice.dao.CornerHighLowDetailHandler;
import com.mensa.horsewebservice.dao.CornerHighLowHandler;
import com.mensa.horsewebservice.dao.CorrectScoreHandler;
import com.mensa.horsewebservice.dao.CouponHandler;
import com.mensa.horsewebservice.dao.FirstCorrectScoreHandler;
import com.mensa.horsewebservice.dao.FirstGoalHighLowDetailHandler;
import com.mensa.horsewebservice.dao.FirstGoalHighLowHandler;
import com.mensa.horsewebservice.dao.FirstHomeAwayDrawHandler;
import com.mensa.horsewebservice.dao.FirstTeamToScoreHandler;
import com.mensa.horsewebservice.dao.FootballHandler;
import com.mensa.horsewebservice.dao.FootballResultHandler;
import com.mensa.horsewebservice.dao.GoalHighLowDetailHandler;
import com.mensa.horsewebservice.dao.GoalHighLowHandler;
import com.mensa.horsewebservice.dao.HalfFullHomeAwayDrawHandler;
import com.mensa.horsewebservice.dao.HandicapHandler;
import com.mensa.horsewebservice.dao.HandicapHomeAwayDrawHandler;
import com.mensa.horsewebservice.dao.HomeAwayDrawHandler;
import com.mensa.horsewebservice.dao.LeagueHandler;
import com.mensa.horsewebservice.dao.OddEvenHandler;
import com.mensa.horsewebservice.dao.RecordHandler;
import com.mensa.horsewebservice.dao.TeamHandler;
import com.mensa.horsewebservice.dao.TotalGoalHandler;
import com.mensa.horsewebservice.model.Football.CornerHighLow;
import com.mensa.horsewebservice.model.Football.CornerHighLowDetail;
import com.mensa.horsewebservice.model.Football.CorrectScore;
import com.mensa.horsewebservice.model.Football.Coupon;
import com.mensa.horsewebservice.model.Football.FirstCorrectScore;
import com.mensa.horsewebservice.model.Football.FirstGoalHighLow;
import com.mensa.horsewebservice.model.Football.FirstHomeAwayDraw;
import com.mensa.horsewebservice.model.Football.FirstTeamToScore;
import com.mensa.horsewebservice.model.Football.Football;
import com.mensa.horsewebservice.model.Football.FootballResult;
import com.mensa.horsewebservice.model.Football.FootballResultRecord;
import com.mensa.horsewebservice.model.Football.GoalHighLow;
import com.mensa.horsewebservice.model.Football.HalfFullHomeAwayDraw;
import com.mensa.horsewebservice.model.Football.Handicap;
import com.mensa.horsewebservice.model.Football.HandicapHomeAwayDraw;
import com.mensa.horsewebservice.model.Football.HomeAwayDraw;
import com.mensa.horsewebservice.model.Football.League;
import com.mensa.horsewebservice.model.Football.OddEven;
import com.mensa.horsewebservice.model.Football.Team;
import com.mensa.horsewebservice.model.Football.TotalGoal;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author matt_
 */
public class ImportScheduler implements ServletContextListener {
    private static Logger log = LogManager.getLogger(ImportScheduler.class);
    private ScheduledExecutorService scheduler;
    private int currentDay = -1; 
    private String UrlAll = "https://bet.hkjc.com/football/getJSON.aspx?jsontype=index.aspx"; 
    private String UrlOne = "https://bet.hkjc.com/football/getJSON.aspx?jsontype=odds_allodds.aspx&matchid=";
    private String UrlResult = "https://bet.hkjc.com/football/getJSON.aspx?jsontype=results.aspx";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Start contextInitialized");
        log.info("Start contextInitialized");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new ImportJob(), 0, 2, TimeUnit.HOURS);
        log.info("Finished contextInitialized");
        System.out.println("Finished contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Start contextDestroyed");
        log.info("Start contextDestroyed");
        scheduler.shutdownNow(); 
        try {
            scheduler.awaitTermination(7, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("Scheduler awaitTermination Error: ", ex);
        }
        log.info("Finished contextDestroyed"); 
        System.out.println("Finished contextDestroyed");
    }
    
    public class ImportJob implements Runnable {
        @Override
        public void run() {
            int day = LocalDateTime.now().getDayOfYear(); 
            int hour = LocalDateTime.now().getHour(); 
            //if (day == currentDay || hour < 8) return; 
            
            currentDay = day; 

            log.info("Started ImportJob");
            Downloader downloader = new Downloader(); 
            String contentJson = downloader.GetResponseFromUrl(UrlAll); 
            Gson gson = new Gson();
            Football[] records = null; 
            for (int checkError = 0; checkError < 10; checkError++) {
                try {
                    Thread.sleep(1000);
                    records = gson.fromJson(contentJson, Football[].class); 
                    log.info("" + records.length);
                    break; 
                } catch (Exception e) {
                    if ((contentJson != null) && (contentJson.indexOf("Cookies must be enabled in order to view this page.") < 0))
                    {
                        log.error(contentJson, e); 
                    }
                    contentJson = downloader.GetResponseFromUrl(UrlAll); 
                    if (checkError >= 9) currentDay = -1; 
                }
            }
            if (records == null) return; 
            FootballHandler footballHandler = new FootballHandler(); 
            for (int counter = 0; counter < records.length; counter++) {
                try {
                    Thread.sleep(1000);
//log.info("Step 01"); 
                    Football football = footballHandler.Get(records[counter]); 
//log.info("Step 02"); 
                    if (football == null) {
//log.info("Step 03"); 
//log.info(records[counter].getMatchID()); 
                        contentJson = downloader.GetResponseFromUrl(UrlOne + records[counter].getMatchID()); 
//log.info("Step 04"); 
//log.info(contentJson); 
                        Football[] actualRecords = null; 
                        for (int checkError = 0; checkError < 10; checkError++) {
                            try {
                                actualRecords = gson.fromJson(contentJson, Football[].class); 
                                break; 
                            } catch (Exception e) {
                                if ((contentJson != null) && (contentJson.indexOf("Cookies must be enabled in order to view this page.") < 0))
                                {
                                    log.info(contentJson); 
                                    log.error(records[counter].getMatchID(), e);
                                } else {
                                    log.error("Exchange to ActualRecords Error. Waiting 30 second."); 
                                }
                                Thread.sleep(30000);
                                contentJson = downloader.GetResponseFromUrl(UrlOne + records[counter].getMatchID()); 
                                if (checkError >= 9) currentDay = -1; 
                            }                                
                        }
                        if (actualRecords == null) continue; 
//log.info("Step 05"); 
                        Football actualFootball = null; 
//log.info("Step 06"); 
                        for (int counter2 = 0; counter2 < actualRecords.length; counter2++)
                        {
                            actualFootball = actualRecords[counter2];
                            if (records[counter].getMatchID().equals(actualFootball.getMatchID()))
                            {
                                break; 
                            }
                        }

//log.info("Step 07"); 
//log.info(actualFootball.getMatchID()); 
                        // <editor-fold desc="Coupon">
                        if (actualFootball.getCoupon() != null) {
                            CouponHandler couponHandler = new CouponHandler(); 
                            Coupon coupon = couponHandler.Get(actualFootball.getCoupon()); 
                            if (coupon == null) 
                            {
                                actualFootball.getCoupon().setCreated_at(LocalDateTime.now());
                                actualFootball.getCoupon().setUpdated_at(LocalDateTime.now());
                                coupon = couponHandler.CreateAndReturn(actualFootball.getCoupon()); 
                                if (coupon != null) actualFootball.setCoupon(coupon);
                            } else {
                                actualFootball.setCoupon(coupon);
                            }
                        }
                        // </editor-fold>
//log.info("Step 08"); 
                        // <editor-fold desc="League">
                        if (actualFootball.getLeague() != null) {
                            LeagueHandler leagueHandler = new LeagueHandler(); 
                            League league = leagueHandler.Get(actualFootball.getLeague()); 
                            if (league == null) {
                                actualFootball.getLeague().setCreated_at(LocalDateTime.now());
                                actualFootball.getLeague().setUpdated_at(LocalDateTime.now());
                                league = leagueHandler.CreateAndReturn(actualFootball.getLeague()); 
                                if (league != null) actualFootball.setLeague(league);
                            } else {
                                actualFootball.setLeague(league);
                            }
                        }
                        // </editor-fold>
//log.info("Step 09"); 
                        // <editor-fold desc="Home Team">
                        if (actualFootball.getHomeTeam() != null) {
                            TeamHandler teamHandler = new TeamHandler(); 
                            Team team = teamHandler.Get(actualFootball.getHomeTeam()); 
                            if (team == null) 
                            {
                                actualFootball.getHomeTeam().setCreated_at(LocalDateTime.now());
                                actualFootball.getHomeTeam().setUpdated_at(LocalDateTime.now());
                                team = teamHandler.CreateAndReturn(actualFootball.getHomeTeam()); 
                                if (team != null) actualFootball.setHomeTeam(team);
                            } else {
                                actualFootball.setHomeTeam(team);
                            }
                        }
                        // </editor-fold>
//log.info("Step 10"); 
                        // <editor-fold desc="Away Team">
                        if (actualFootball.getAwayTeam() != null) {
                            TeamHandler teamHandler = new TeamHandler(); 
                            Team team = teamHandler.Get(actualFootball.getAwayTeam()); 
                            if (team == null) 
                            {
                                actualFootball.getAwayTeam().setCreated_at(LocalDateTime.now());
                                actualFootball.getAwayTeam().setUpdated_at(LocalDateTime.now());
                                team = teamHandler.CreateAndReturn(actualFootball.getAwayTeam()); 
                                if (team != null) actualFootball.setAwayTeam(team);
                            } else {
                                actualFootball.setAwayTeam(team);
                            }
                        }
                        // </editor-fold>
//log.info("Step 11"); 
                        // <editor-fold desc="Corner High Low">
                        if (actualFootball.getChlodds() != null) {
                            CornerHighLowHandler cornerHighLowHandler = new CornerHighLowHandler(); 
                            CornerHighLowDetailHandler cornerHighLowDetailHandler = new CornerHighLowDetailHandler(); 
                            actualFootball.getChlodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getChlodds().setUpdated_at(LocalDateTime.now());
                            for (int counter3 = 0; counter3 < actualFootball.getChlodds().getLINELIST().size(); counter3++) {
                                actualFootball.getChlodds().getLINELIST().get(counter3).setMatchID(actualFootball.getMatchID());
                                actualFootball.getChlodds().getLINELIST().get(counter3).setCreated_at(LocalDateTime.now());
                                actualFootball.getChlodds().getLINELIST().get(counter3).setUpdated_at(LocalDateTime.now());
                                CornerHighLowDetail detail = cornerHighLowDetailHandler.CreateAndReturn(actualFootball.getChlodds().getLINELIST().get(counter3));
                                actualFootball.getChlodds().getLINELIST().set(counter3, detail); 
                            }
                            CornerHighLow cornerHighLow = cornerHighLowHandler.CreateAndReturn(actualFootball.getChlodds()); 
                            if (cornerHighLow != null) actualFootball.setChlodds(cornerHighLow);
                        }
                        // </editor-fold>
//log.info("Step 12"); 
                        // <editor-fold desc="Correct Score">
                        if (actualFootball.getCrsodds() != null) {
                            CorrectScoreHandler correctScoreHandler = new CorrectScoreHandler(); 
                            actualFootball.getCrsodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getCrsodds().setUpdated_at(LocalDateTime.now());
                            CorrectScore correctScore = correctScoreHandler.CreateAndReturn(actualFootball.getCrsodds()); 
                            if (correctScore != null) actualFootball.setCrsodds(correctScore);
                        }
                        // </editor-fold>
//log.info("Step 13"); 
                        // <editor-fold desc="First Correct Score">
                        if (actualFootball.getFcsodds() != null) {
                            FirstCorrectScoreHandler firstCorrectScoreHandler = new FirstCorrectScoreHandler(); 
                            actualFootball.getFcsodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getFcsodds().setUpdated_at(LocalDateTime.now());
                            FirstCorrectScore firstCorrectScore = firstCorrectScoreHandler.CreateAndReturn(actualFootball.getFcsodds()); 
                            if (firstCorrectScore != null) actualFootball.setFcsodds(firstCorrectScore);
                        }
                        // </editor-fold>
//log.info("Step 14"); 
                        // <editor-fold desc="First Goal High Low">
                        if (actualFootball.getFhlodds() != null) {
                            FirstGoalHighLowHandler firstGoalHighLowHandler = new FirstGoalHighLowHandler(); 
                            FirstGoalHighLowDetailHandler firstGoalHighLowDetailHandler = new FirstGoalHighLowDetailHandler(); 
                            actualFootball.getFhlodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getFhlodds().setUpdated_at(LocalDateTime.now());
                            for (int counter3 = 0; counter3 < actualFootball.getHilodds().getLINELIST().size(); counter3++) {
                                actualFootball.getFhlodds().getLINELIST().get(counter3).setMatchID(actualFootball.getMatchID());
                                actualFootball.getFhlodds().getLINELIST().get(counter3).setCreated_at(LocalDateTime.now());
                                actualFootball.getFhlodds().getLINELIST().get(counter3).setUpdated_at(LocalDateTime.now());
                                actualFootball.getFhlodds().getLINELIST().set(counter3, firstGoalHighLowDetailHandler.CreateAndReturn(actualFootball.getFhlodds().getLINELIST().get(counter3))); 
                            }
                            FirstGoalHighLow firstGoalHighLow = firstGoalHighLowHandler.CreateAndReturn(actualFootball.getFhlodds()); 
                            if (firstGoalHighLow != null) actualFootball.setFhlodds(firstGoalHighLow);
                        }
                        // </editor-fold>
//log.info("Step 15"); 
                        // <editor-fold desc="First Home Away Draw">
                        if (actualFootball.getFhaodds() != null) {
                            FirstHomeAwayDrawHandler firstHomeAwayDrawHandler = new FirstHomeAwayDrawHandler(); 
                            actualFootball.getFhaodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getFhaodds().setUpdated_at(LocalDateTime.now());
                            FirstHomeAwayDraw firstHomeAwayDraw = firstHomeAwayDrawHandler.CreateAndReturn(actualFootball.getFhaodds()); 
                            if (firstHomeAwayDraw != null) actualFootball.setFhaodds(firstHomeAwayDraw);
                        }
                        // </editor-fold>
//log.info("Step 16"); 
                        // <editor-fold desc="First Team To Score">
                        if (actualFootball.getFtsodds() != null) {
                            FirstTeamToScoreHandler firstTeamToScoreHandler = new FirstTeamToScoreHandler(); 
                            actualFootball.getFtsodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getFtsodds().setUpdated_at(LocalDateTime.now());
                            FirstTeamToScore firstTeamToScore = firstTeamToScoreHandler.CreateAndReturn(actualFootball.getFtsodds()); 
                            if (firstTeamToScore != null) actualFootball.setFtsodds(firstTeamToScore);
                        }
                        // </editor-fold>
//log.info("Step 17"); 
                        // <editor-fold desc="Goal High Low">
                        if (actualFootball.getHilodds() != null) {
                            GoalHighLowHandler goalHighLowHandler = new GoalHighLowHandler(); 
                            GoalHighLowDetailHandler goalHighLowDetailHandler = new GoalHighLowDetailHandler(); 
                            actualFootball.getHilodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getHilodds().setUpdated_at(LocalDateTime.now());
                            for (int counter3 = 0; counter3 < actualFootball.getHilodds().getLINELIST().size(); counter3++) {
                                actualFootball.getHilodds().getLINELIST().get(counter3).setMatchID(actualFootball.getMatchID());
                                actualFootball.getHilodds().getLINELIST().get(counter3).setCreated_at(LocalDateTime.now());
                                actualFootball.getHilodds().getLINELIST().get(counter3).setUpdated_at(LocalDateTime.now());
                                actualFootball.getHilodds().getLINELIST().set(counter3, goalHighLowDetailHandler.CreateAndReturn(actualFootball.getHilodds().getLINELIST().get(counter3))); 
                            }
                            GoalHighLow goalHighLow = goalHighLowHandler.CreateAndReturn(actualFootball.getHilodds()); 
                            if (goalHighLow != null) actualFootball.setHilodds(goalHighLow);
                        }
                        // </editor-fold>
//log.info("Step 18"); 
                        // <editor-fold desc="Half Full Home Away Draw">
                        if (actualFootball.getHftodds() != null) {
                            HalfFullHomeAwayDrawHandler halfFullHomeAwayDrawHandler = new HalfFullHomeAwayDrawHandler(); 
                            actualFootball.getHftodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getHftodds().setUpdated_at(LocalDateTime.now());
                            HalfFullHomeAwayDraw halfFullHomeAwayDraw = halfFullHomeAwayDrawHandler.CreateAndReturn(actualFootball.getHftodds()); 
                            if (halfFullHomeAwayDraw != null) actualFootball.setHftodds(halfFullHomeAwayDraw);
                        }
                        // </editor-fold>
//log.info("Step 19"); 
                        // <editor-fold desc="Handicap">
                        if (actualFootball.getHdcodds() != null) {
                            HandicapHandler handicapHandler = new HandicapHandler(); 
                            actualFootball.getHdcodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getHdcodds().setUpdated_at(LocalDateTime.now());
                            Handicap handicap = handicapHandler.CreateAndReturn(actualFootball.getHdcodds()); 
                            if (handicap != null) actualFootball.setHdcodds(handicap);
                        }
                        // </editor-fold>
//log.info("Step 20"); 
                        // <editor-fold desc="Handicap Home Away Draw">
                        if (actualFootball.getHhaodds() != null) {
                            HandicapHomeAwayDrawHandler handicapHomeAwayDrawHandler = new HandicapHomeAwayDrawHandler(); 
                            actualFootball.getHhaodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getHhaodds().setUpdated_at(LocalDateTime.now());
                            HandicapHomeAwayDraw handicapHomeAwayDraw = handicapHomeAwayDrawHandler.CreateAndReturn(actualFootball.getHhaodds()); 
                            if (handicapHomeAwayDraw != null) actualFootball.setHhaodds(handicapHomeAwayDraw);
                        }
                        // </editor-fold>
//log.info("Step 21"); 
                        // <editor-fold desc="Home Away Draw">
                        if (actualFootball.getHadodds() != null) {
                            HomeAwayDrawHandler homeAwayDrawHandler = new HomeAwayDrawHandler(); 
                            actualFootball.getHadodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getHadodds().setUpdated_at(LocalDateTime.now());
                            HomeAwayDraw homeAwayDraw = homeAwayDrawHandler.CreateAndReturn(actualFootball.getHadodds()); 
                            if (homeAwayDraw != null) actualFootball.setHadodds(homeAwayDraw);
                        }
                        // </editor-fold>
//log.info("Step 22"); 
                        // <editor-fold desc="Odd Even">
                        if (actualFootball.getOoeodds() != null) {
                            OddEvenHandler oddEvenHandler = new OddEvenHandler(); 
                            actualFootball.getOoeodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getOoeodds().setUpdated_at(LocalDateTime.now());
                            OddEven oddEven = oddEvenHandler.CreateAndReturn(actualFootball.getOoeodds()); 
                            if (oddEven != null) actualFootball.setOoeodds(oddEven);
                        }
                        // </editor-fold>
//log.info("Step 23"); 
                        // <editor-fold desc="Total Goal">
                        if (actualFootball.getTtgodds() != null) {
                            TotalGoalHandler totalGoalHandler = new TotalGoalHandler(); 
                            actualFootball.getTtgodds().setCreated_at(LocalDateTime.now());
                            actualFootball.getTtgodds().setUpdated_at(LocalDateTime.now());
                            TotalGoal totalGoal = totalGoalHandler.CreateAndReturn(actualFootball.getTtgodds()); 
                            if (totalGoal != null) actualFootball.setTtgodds(totalGoal);
                        }
                        // </editor-fold>

//log.info("Step 99"); 
                        actualFootball.setCreated_at(LocalDateTime.now());
                        actualFootball.setUpdated_at(LocalDateTime.now());
                        if (footballHandler.Create(actualFootball)) {
                            log.info("Created");
                        } else {
                            log.info("Fail Create"); 
                        }
                    }
                } catch (Exception e) {
                    log.error("Error: "+counter, e);
                    currentDay = -1; 
                }
            }
//log.info("Step 101");
            FootballResultRecord[] footballResultRecords = null; 
            contentJson = downloader.GetResponseFromUrl(UrlResult); 
            for (int checkError = 0; checkError < 10; checkError++) {
                try {
                    Thread.sleep(1000);
                    footballResultRecords = gson.fromJson(contentJson, FootballResultRecord[].class); 
                    break; 
                } catch (Exception e) {
                    log.error(contentJson, e); 
                    contentJson = downloader.GetResponseFromUrl(UrlAll); 
                    if (checkError >= 9) currentDay = -1; 
                }
            }
//log.info("Step 102");
            for (int counter = 0; counter < footballResultRecords.length; counter++) {
                if (footballResultRecords[counter] != null && "ActiveMatches".equals(footballResultRecords[counter].getName())) {
                    records = footballResultRecords[counter].getMatches(); 
                    for (int counter2 = 0; counter2 < records.length; counter2++) {
                        try {
                            if (records[counter2] == null || records[counter2].getResults() == null || records[counter2].getResults().getCRS() == null || records[counter2].getResults().getCRS().equals("-")) continue; 
                            Football football = footballHandler.Get(records[counter2]);
                            if (football != null) {
                                if (football.getResults() != null && football.getResults().getCRS() != null && !football.getResults().getCRS().equals("-")) continue; 
                                football.getResults().setCRS(records[counter2].getResults().getCRS());
                                football.getResults().setFCS(records[counter2].getResults().getFCS());
                                football.getResults().setFHA(records[counter2].getResults().getFHA());
                                football.getResults().setHAD(records[counter2].getResults().getHAD());
                                football.getResults().setHFT(records[counter2].getResults().getHFT());
                                football.getResults().setOOE(records[counter2].getResults().getOOE());
                                football.getResults().setTTG(records[counter2].getResults().getTTG());
                                football.getResults().setCreated_at(LocalDateTime.now());
                                football.getResults().setUpdated_at(LocalDateTime.now());
                                football.setUpdated_at(LocalDateTime.now()); 
                                football.setCornerresult(records[counter2].getCornerresult());
                                footballHandler.Update(football); 
                            }
                        } catch (Exception ex) {
                            log.error("Counter: " + counter2, ex);
                            currentDay = -1; 
                        }
                        
                    }
                }
            }
//log.info("Step 103");
            //Exporter exporter = new Exporter(); 
            //exporter.export(); 
            ImportSuggestBuyRecord importer = new ImportSuggestBuyRecord(); 
            importer.importSuggestBuyRecord();
log.info("Imported"); 
        }
    }
}
