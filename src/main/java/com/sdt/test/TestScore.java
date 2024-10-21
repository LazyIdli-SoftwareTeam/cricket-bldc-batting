/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.test;

import com.sdt.data.ScoreCombinationBean;
import com.sdt.data.ScoreSequenceBean;
import com.sdt.system.ErrorAlert;
import java.util.ArrayList;
import javafx.application.Platform;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author Srikanth
 */
public class TestScore {
    static ArrayList<ScoreSequenceBean> sequences = new ArrayList<>();
    static String datavals = "";
    public static void autoscore(){
        
        if(true){
            if(sequences.get(0).getBi_no()==12 && sequences.get(0).getBi_status()==1)
                sequences.get(0).setBi_status(0);
            for(int i=0;i<sequences.size();){
                if(sequences.get(i).getBi_no()==12){
                    sequences.get(i).setBi_no(4);
                }                
                if(sequences.get(i).getBi_status()==1){
                    sequences.remove(i);
                }else{
                    i++;
                }
            }
            boolean pole_1_crossed = false;
            boolean score_reagion_crossed=false;
            for(int i=1;i<sequences.size();i++){
                int prev_dir = sequences.get(i-1).getDirection();
                int prev_pole = getPole(sequences.get(i-1).getBi_no());
                int pole = getPole(sequences.get(i).getBi_no());
                if(pole_1_crossed){
                    if(score_reagion_crossed){
                        
                    }else{
                        if(pole>prev_pole){
                            sequences.get(i).setDirection(1);
                        }else if(prev_pole==1 && prev_dir==0){
                            sequences.get(i).setDirection(1);
                        }else if(prev_pole==2 ){
                            score_reagion_crossed=true;
                        }
                    }
                }else{
                    if(prev_pole==1 && prev_dir==0){
                        pole_1_crossed=true;
                        sequences.get(i).setDirection(1);
                    }else{                        
                        if(pole==1)
                            pole_1_crossed=true;
                    }  
                    
                    if(sequences.get(i).getInterval()-sequences.get(i-1).getInterval()>300){
                        pole_1_crossed=true;
                        sequences.get(i).setDirection(1);
                    }
                }               
            }
            int seq []= {-1,-1,-1,-1,-1};
            int seq_pos=0;
            for(int i=1;i<sequences.size();i++){
                if(sequences.get(i).getDirection()==1)
                    seq[seq_pos++]=sequences.get(i).getBi_no();
            }
            int score = 0;
            /*if(HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations()!=null && HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size()>0){
                for(int i=0;i<HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().size();i++){
                    ScoreCombinationBean scb = HandleEvents.generalSettings.getAutoScotringBean().getScorecombinations().get(i);
                    if(scb.getPos_1()==seq[0] && scb.getPos_2()==seq[1] && scb.getPos_3()==seq[2] && scb.getPos_4()==seq[3] && scb.getPos_5()==seq[4]){
                        score=scb.getResult();
                    }
                }
            }*/
            final String result = "("+seq[0]+","+seq[1]+","+seq[2]+","+seq[3]+","+seq[4]+")="+score;
            System.out.println(datavals +result );
        }
          
    }
    
    public static int getPole(int di){
        int pole =-1;
        switch(di){
            case 1:
                pole = 1;
                break;
            case 2:
                pole = 1;
                break;
            case 3:
                pole = 2;
                break;
            case 4:
                pole = 2;
                break;
            case 5:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi5_pole();
                break;
            case 6:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi6_pole();
                break;
            case 7:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi7_pole();
                break;
            case 8:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi8_pole();
                break;
            case 9:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi9_pole();
                break;
            case 10:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi10_pole();
                break;
        }
        
        return pole;
    }
    
    public static void main(String args[]){
        sequences.add(new ScoreSequenceBean(12,1,0));
        sequences.add(new ScoreSequenceBean(1,1,182));
        sequences.add(new ScoreSequenceBean(1,0,628));
        sequences.add(new ScoreSequenceBean(1,1,816));
        sequences.add(new ScoreSequenceBean(3,0,838));
        sequences.add(new ScoreSequenceBean(3,1,1026));
        autoscore();
        /*
        12,1,0
        1,1,185
        2,0,523
        4,0,692
        12,0,692
        2,1,711
        4,1,880
        12,1,880
        4,0,1203
        12,0,1203
        4,1,1395
        12,1,1395
        1,0,1607
        1,1,1806
        only 2
        */
    }
}
