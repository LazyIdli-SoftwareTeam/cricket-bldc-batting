/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.serial.HandleSerial;
import java.util.Calendar;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.MediaStageNew;

/**
 *
 * @author possi
 */
public class BowlingThread implements Runnable{
    public BowlingThread(){
        Thread th = new Thread(this);
        th.start();
        //th.setPriority(10);
        
    }
    int ctr = 0;
    //double pre_time = 0;
    public void run(){
        while(true){
            try {
                Thread.sleep(50);
                if(MediaStageNew.enable_bowl){
                    //ctr++;
                    //if(ctr*10>=HandleEvents.bowler_trigger){
                    //long time_diff = Calendar.getInstance().getTimeInMillis()-MediaStageNew.ms1;
                    double time_diff = MediaStageNew.mp.getCurrentTime().toMillis();
                    /*if(time_diff>100){
                        if(time_diff-pre_time<49 || time_diff-pre_time>61)
                            System.out.println(time_diff-pre_time);
                    }
                    pre_time = time_diff;*/
                    //if(MediaStageNew.mp.getRate()!=1)
                    //    System.out.println(MediaStageNew.mp.rateProperty().getValue());
                    //if(time_diff>100 && MediaStageNew.mp.getBufferProgressTime().toMillis()!=6208)
                    //    System.out.println(MediaStageNew.mp.getBufferProgressTime().toMillis());                    
                    if(time_diff+50>HandleEvents.bowler_trigger){
                        //ctr=0;
                        
                        //time_diff = MediaStageNew.mp.getCurrentTime().toMillis();
                        if(HandleEvents.bowler_trigger-time_diff>0)
                            Thread.sleep((long)(HandleEvents.bowler_trigger-time_diff));
                        MediaStageNew.enable_bowl=false;
                        //MediaStageNew.ms3 =  Calendar.getInstance().getTimeInMillis();
                        //time_diff = MediaStageNew.mp.getCurrentTime().toMillis();
                        HandleSerial.handleCom(HandleSerial.ball_release);   
                        //System.out.println("Ball Released");
                    }
                    if(time_diff+500>HandleEvents.bowler_trigger){
                        MediaStageNew.this_obj.doreplay(time_diff);
                    }
                }else{
                    //ctr=0;                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
