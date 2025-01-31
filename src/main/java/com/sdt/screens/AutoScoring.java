/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.AutoScoringBean;
import com.sdt.data.ScoreCombinationBean;
import com.sdt.data.ScoreSequenceBean;
import com.sdt.logging.LogManager;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import javafx.application.Platform;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;
//L1 backyard
//local
//first class
//international left
/**
 *
 * @author Srikanth
 */
public class AutoScoring implements Runnable{
    public static AutoScoring this_obj = null;
    public static boolean active_thread =true;
    public static String display_string = "";
    public AutoScoring(){
    }
    static ArrayList<ScoreSequenceBean> sequences = new ArrayList<>();
    static String datavals = "";
    public void run(){
        try {
            DatagramSocket ds = new DatagramSocket(/*HandleEvents.generalSettings.getAutoScotringBean().getPort_no()*/4211);
            byte receive [] = new byte[65535];
            DatagramPacket dpReceive = null;
            while(active_thread){
                dpReceive = new DatagramPacket(receive, receive.length);
                ds.receive(dpReceive);
                String packet_data = data(receive).toString();
                //System.out.println(packet_data);
                if(packet_data.indexOf(/*HandleEvents.generalSettings.getAutoScotringBean().getSerial_no()*/"DISTS")>=0 && HandleSerial.ball_released){
                    HandleSerial.ball_released=false;
                    int prefix_len = 5;//HandleEvents.generalSettings.getAutoScotringBean().getSerial_no().length();
                    sequences.clear();                    
                    //System.out.println();
                    datavals = "";
                    score_data_val = "";
                    for(int i=0;i<20;i++){
                        int start = (i*4) + prefix_len;
                        if(receive[start]==0x55)
                            break;
                        ScoreSequenceBean ssb = new ScoreSequenceBean();
                        ssb.setBi_no(receive[start]);
                        ssb.setBi_status(receive[start+1]);
                        int byte1=receive[start+2];
                        if(byte1<0)
                            byte1 = byte1+256;
                        int byte2=receive[start+3];
                         if(byte2<0)
                            byte2 = byte2+256;
                         byte2 = (byte2<<8);
                         //System.out.println((byte2)+","+byte1);
                        int val = ((byte2)+(byte1));
                        ssb.setInterval(val);
                        sequences.add(ssb);
                        datavals = datavals + "("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")";
                        //System.out.print("("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")");
                        if(ssb.getBi_status()==0&&ssb.getBi_no()!=HandleEvents.generalSettings.getAutoScotringBean().getSolenoid_sensor() ){
                            score_data_val = score_data_val + "("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")";
                        }
                    }                                      
                    autoscore();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delay(int ms){
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
    public static void process(int[] data){
        int command = data[2];
        int length = data[3];
        if(command==15 && length == 0xC8){
            sequences.clear();
            datavals = "";
            score_data_val = "";
            for(int i=4;i<0xC8;i=i+4){
                int bi_no = data[i];
                if(bi_no==0||bi_no==0x55)
                    break;
                int bi_status = data[i+1];
                int byte1=data[i+2];
                if(byte1<0)
                    byte1 = byte1+256;
                int byte2=data[i+3];
                if(byte2<0)
                   byte2 = byte2+256;
                byte2 = (byte2<<8);
                int bi_time = ((byte2)+(byte1));
                ScoreSequenceBean ssb = new ScoreSequenceBean();
                ssb.setBi_no(bi_no);
                ssb.setBi_status(bi_status);
                ssb.setInterval(bi_time);
                sequences.add(ssb);
                datavals = datavals + "("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")";
                //System.out.print("("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")");
                if(ssb.getBi_status()==0&&ssb.getBi_no()!=HandleEvents.generalSettings.getAutoScotringBean().getSolenoid_sensor() ){
                    score_data_val = score_data_val + "("+ssb.getBi_no()+","+ssb.getBi_status()+","+ssb.getInterval()+")";
                }
                System.out.println(bi_no+","+bi_status+","+bi_time);         
            }
            autoscore();
        }        
    }
    static int score =0;
    static int random = 0;
    static int status = -1;
    static int actual_passing = 1000;
    public static int score_time = 2000;
    public static String score_data = "";
    public static String score_data_val = "";
    public static String display_result = "";
    public static void autoscore(){
        int min_interval = 1000;
        int max_pole = 2;
        status = -1;
        display_string ="";
        score_data = "";
        AutoScoringBean autoScoringBean = HandleEvents.generalSettings.getAutoScotringBean();
        if(HandleEvents.game_mode==Variables.game_mode_sp || HandleEvents.game_mode==Variables.game_mode_mp){
            
            for(int i=0;i<sequences.size();){                
                
                if(sequences.get(i).getBi_no()==autoScoringBean.getBall_sensor_1()|| sequences.get(i).getBi_no()==autoScoringBean.getBall_sensor_2()){
                    if(sequences.get(i).getInterval()<actual_passing||actual_passing==0)
                        actual_passing=sequences.get(i).getInterval();
                    if(sequences.get(i).getInterval()<min_interval){
                        min_interval = sequences.get(i).getInterval();
                    }
                }                
                int wickettime = 1000;
                switch(HandleEvents.game_skill_level){
                    case 1:
                        wickettime = autoScoringBean.getSkill_1_wicket_time();
                        break;
                    case 2:
                        wickettime = autoScoringBean.getSkill_2_wicket_time();
                        break;
                    case 3:
                        wickettime = autoScoringBean.getSkill_3_wicket_time();
                        break;
                    case 4:
                        wickettime = autoScoringBean.getSkill_4_wicket_time();
                        break;
                }
                if(sequences.get(i).getBi_no()==autoScoringBean.getWicket_sensor() && sequences.get(i).getInterval()<(min_interval+wickettime) && autoScoringBean.isWicket_input()){
                    display_string = "passing="+actual_passing + ", Reselt time="+(sequences.get(i).getInterval()-actual_passing) + ", Result=out";
                    score_time = sequences.get(i).getInterval()-actual_passing;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //delay(1000);                            
                            HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                        }
                    });
                    return;
                }   
                if(sequences.get(i).getBi_status()==1 || sequences.get(i).getBi_no()==autoScoringBean.getSolenoid_sensor()){
                    sequences.remove(i);
                }else{
                    int temppole=getPole(sequences.get(i).getBi_no());
                    if(temppole<10 && max_pole<temppole)
                        max_pole=temppole;
                    i++;
                }
                
            }
            
            int temp_time=5000,temp_status=-1;
            for(int i=0;i<sequences.size();i++){
               temp_status=getPole(sequences.get(i).getBi_no());
                if(temp_status>=10){
                    if(sequences.get(i).getInterval()>min_interval && sequences.get(i).getInterval()<temp_time){
                        display_string = "passing="+actual_passing + ", Reselt time="+(sequences.get(i).getInterval()-actual_passing) + ", Result="+(temp_status-10);
                        score_time = sequences.get(i).getInterval()-actual_passing;
                        status = temp_status-10;
                        temp_time=sequences.get(i).getInterval();
                    }
                } 
            }
            if(status!=-1){
                //ErrorAlert.info(datavals+status);
                score_data=score_data_val+"="+status;
                LogManager.logInfo("(direct)"+datavals+"="+status);
                if(HandleEvents.game_mode==Variables.game_mode_sixer){
                   if(status==6){
                       Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //delay(1000);
                                HandleEvents.handleEvent(Variables.button_type_six, 1);
                            }
                        });                       
                   }else{
                       Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //delay(1000);
                                HandleEvents.handleEvent(Variables.button_type_six, 0);
                            }
                        });                       
                   }
               }else{
                   if(status>0){
                       Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //delay(1000);
                                HandleEvents.handleEvent(Variables.button_type_result_runs_straight+random, status);
                                random++;
                                if(random>2)
                                    random = 0;
                            }
                        });
                       
                   }else if(status==0){
                       Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //delay(1000);
                                HandleEvents.handleEvent(Variables.button_type_result_norun, status);
                            }
                        });                       
                   }else if(status==9){
                       Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //delay(1000);
                                HandleEvents.handleEvent(Variables.button_type_result_bowled, status);
                            }
                        });                       
                   }
               }
                return;
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
                        if(pole==prev_pole && sequences.get(i-1).getDirection()==1 && sequences.get(i).getBi_no()!=sequences.get(i-1).getBi_no()){
                            sequences.get(i).setDirection(0);
                        }else if(pole>prev_pole){
                            sequences.get(i).setDirection(1);
                        }else if(prev_pole==1 && prev_dir==0){
                            sequences.get(i).setDirection(1);
                        }else if(prev_pole==max_pole ){
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
                    
                    if(sequences.get(i).getInterval()-sequences.get(i-1).getInterval()>(autoScoringBean.getMax_prev_pole_time_ref()-(autoScoringBean.getMax_prev_pole_time_factor()*HandleEvents.machineDataBean.getSet_speed()))){
                        pole_1_crossed=true;
                        sequences.get(i).setDirection(1);
                    }
                }               
            }
            int seq []= {0,0,0,0,0};
            int seq_pos=0;
            for(int i=0;i<sequences.size();i++){
                if(sequences.get(i).getDirection()==1){
                    display_string = "passing="+actual_passing + ", Reselt time="+(sequences.get(i).getInterval()-actual_passing) ;
                    score_time = sequences.get(i).getInterval()-actual_passing;
                    boolean seq_repeat = false;
                    for(int j=0;j<seq_pos;j++){
                        if(sequences.get(i).getBi_no()==seq[j])
                            seq_repeat = true;
                    }
                    if(!seq_repeat)
                        seq[seq_pos++]=sequences.get(i).getBi_no();
                }
            }
            score = 0;
            if(autoScoringBean.getScorecombinations()!=null && autoScoringBean.getScorecombinations().size()>0){
                for(int i=0;i<autoScoringBean.getScorecombinations().size();i++){
                    ScoreCombinationBean scb = autoScoringBean.getScorecombinations().get(i);
                    if(scb.getPos_1()==seq[0] && scb.getPos_2()==seq[1] && scb.getPos_3()==seq[2] && scb.getPos_4()==seq[3] && scb.getPos_5()==seq[4]){
                        score=scb.getResult();
                    }
                }
            }
            final String result = "("+seq[0]+","+seq[1]+","+seq[2]+","+seq[3]+","+seq[4]+")="+score;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //ErrorAlert.info(datavals+result);
                    score_data=score_data_val+"="+result;
                    display_result = datavals+result;
                    LogManager.logInfo("(combination)"+datavals+result);
                    if(HandleEvents.game_mode==Variables.game_mode_sixer){
                        if(score==6){
                            //delay(1000);
                            HandleEvents.handleEvent(Variables.button_type_six, 1);
                        }else{
                            //delay(1000);
                            HandleEvents.handleEvent(Variables.button_type_six, 0);
                        }
                    }else{
                        if(score>0){
                            //delay(1000);
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight+random, score);
                            random++;
                            if(random>2)
                                random = 0;
                        }else if(score==0){
                            //delay(1000);
                            HandleEvents.handleEvent(Variables.button_type_result_norun, score);
                        }else if(score==-1){
                            //delay(1000);
                            HandleEvents.handleEvent(Variables.button_type_result_bowled, score);
                        }
                    }
                    
                    
                }
            });
        }
          
    }
    
    public static  int getPole(int di){
        int pole =-1;
        switch(di){
            case 1:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi1_pole();
                break;
            case 2:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi2_pole();
                break;
            case 3:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi3_pole();
                break;
            case 4:
                pole = HandleEvents.generalSettings.getAutoScotringBean().getDi4_pole();
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
        //if(pole>9)
        //    pole=-1;
        return pole;
    }
    
    public static StringBuilder data(byte[] a){
        if(a==null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i=0;
        while(a[i]!=0){
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
    
    public static void main(String args[]){
       // new AutoScoring();
    }
}
