/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.ScoreSequenceBean;
import java.util.ArrayList;

/**
 *
 * @author Srikanth
 */
public class TestAutoScoring {
    static ArrayList<ScoreSequenceBean> sequences = new ArrayList<>();
    static String datavals = "";
    public static void main(String args[]){
        sequences.add(new ScoreSequenceBean(12, 1, 0));
        sequences.add(new ScoreSequenceBean(4, 0, 546));
        sequences.add(new ScoreSequenceBean(12, 0, 700));
        sequences.add(new ScoreSequenceBean(1, 0, 746));
        sequences.add(new ScoreSequenceBean(1,1, 846));
        sequences.add(new ScoreSequenceBean(1, 0, 1382));
        sequences.add(new ScoreSequenceBean(1, 1, 1481));
        //sequences.add(new ScoreSequenceBean(3, 0, 850));
        //sequences.add(new ScoreSequenceBean(3, 1, 1853));
        //sequences.add(new ScoreSequenceBean(4, 1, 1340));
       // sequences.add(new ScoreSequenceBean(12, 1, 1340)); 
            //if(sequences.get(0).getBi_no()==12 && sequences.get(0).getBi_status()==1)
            //    sequences.get(0).setBi_status(0);
            int triger_time = 0;
            if(sequences.size()>1){
                triger_time=sequences.get(1).getInterval();
            }
            for(int i=0;i<sequences.size();){
                /*if(sequences.get(i).getBi_no()==12){
                    sequences.get(i).setBi_no(4);
                }*/
                //sequences.get(i).setInterval(sequences.get(i).getInterval()-triger_time);
                if(sequences.get(i).getBi_no()==11 /*&& HandleEvents.generalSettings.getAutoScotringBean().isWicket_input()*/){
                    /*Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                        }
                    });
                    return;*/
                }                
                if(sequences.get(i).getBi_status()==1 || sequences.get(i).getBi_no()==12){
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
                        if(pole==prev_pole && sequences.get(i).getBi_no()!=sequences.get(i-1).getBi_no()){//suggested by sanket
                            sequences.get(i).setDirection(0);
                        }else if(pole>prev_pole){
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
                if(i==1){
                    System.out.println("("+sequences.get(i-1).getBi_no()+","+sequences.get(i-1).getDirection()+")");
                }
                System.out.println("("+sequences.get(i).getBi_no()+","+sequences.get(i).getDirection()+")");
            }
            //System.out.println(sequences.size());
            int seq []= {0,0,0,0,0};
            int seq_pos=0;
            for(int i=0;i<sequences.size();i++){
                if(sequences.get(i).getDirection()==1){
                    seq[seq_pos++]=sequences.get(i).getBi_no();
                    System.out.println(sequences.get(i).getBi_no());
                }
            }
    }
    
     public static int getPole(int di){
        int pole =-1;
        switch(di){
            case 1:
                pole=1;
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi1_pole();
                break;
            case 2:
                pole=1;
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi2_pole();
                break;
            case 3:
                pole=2;
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi3_pole();
                break;
            case 4:
                pole=2;
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi4_pole();
                break;
            case 5:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi5_pole();
                break;
            case 6:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi6_pole();
                break;
            case 7:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi7_pole();
                break;
            case 8:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi8_pole();
                break;
            case 9:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi9_pole();
                break;
            case 10:
                //pole = HandleEvents.generalSettings.getAutoScotringBean().getDi10_pole();
                break;
        }
        
        return pole;
    }
}
