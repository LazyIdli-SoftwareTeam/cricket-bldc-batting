/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.serial.HandleSerial;
import javafx.application.Platform;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.MediaStageNew;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class PracticeTimer implements Runnable{
    public static PracticeTimer this_obj = null;
    public boolean active = false;
    public static void manageTimer(){
        if(this_obj==null){
            new PracticeTimer();
        }else if(!this_obj.active){
            new Thread(this_obj).start();
        }
        this_obj.current_timer=0;
        this_obj.final_timer=0;
    }
    public PracticeTimer(){
        this_obj=this;
        new Thread(this).start();
    }
    int final_timer = 0;
    int current_timer=0;
    int balls = 0;
    public static int memusage_per = 0;
    void checkMemory(){
        try {
            Runtime runtime = Runtime.getRuntime();
            long memoryMax = runtime.maxMemory();
            long memoryUsed = runtime.totalMemory()-runtime.freeMemory();
            //System.out.println(memoryMax+"," + runtime.totalMemory()+","+runtime.freeMemory());
            double memoryUsedPercent = (memoryUsed*100.0)/runtime.totalMemory();
            //System.out.println("Memory used " + memoryUsedPercent);
            memusage_per = (int)memoryUsedPercent;
            if(memoryUsedPercent>40){
                //runtime.gc();
                System.gc();
            }
            //System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void run(){
        active = true;
        while(active){
            if(HandleEvents.game_mode!=Variables.game_mode_practice){
                active=false;
                break;
            }
            try {
                Thread.sleep(1000);
                if(HandleEvents.game_sub_status==Variables.game_sub_status_scored){
                    if(final_timer==0){
                        balls = PracticeScreen.practiceBean.getCurrent_balls()+1;
                        checkMemory();
                        PracticeScreen.practiceBean.setCurrent_balls(balls);
                        if(balls>=(PracticeScreen.practiceBean.getOvers()*6)){
                            active=false;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    HandleEvents.game_status = Variables.game_status_init;
                                    //HandleEvents.handleEvent(Variables.button_type_bowler, HandleEvents.bowler_pos);
                                    MediaStageNew.this_obj.handlemode(HandleEvents.game_mode);
                                }
                            });
                            
                            break;
                        }
                        final_timer = PracticeScreen.practiceBean.getBall_dilay();
                        if(balls%6==0){
                            System.gc();
                            final_timer = PracticeScreen.practiceBean.getOver_delay();
                            if(PracticeScreen.practiceBean.isAutosellect()){
                                HandleEvents.bowler_pos++;
                                if(HandleEvents.bowler_pos>8)
                                    HandleEvents.bowler_pos=1;
                                HandleSerial.handleCom(0xF0+HandleEvents.bowler_pos);
                                HandleEvents.bowler_path=HandleEvents.generalSettings.getModeData().getBowler_path()[HandleEvents.bowler_pos-1];
                                HandleEvents.bowler_trigger=HandleEvents.generalSettings.getModeData().getTrigger_interval()[HandleEvents.bowler_pos-1];
                            }
                        }
                        current_timer=0;
                    }else if(current_timer<final_timer){
                        if(HandleEvents.game_status!=Variables.game_status_paused){
                            current_timer++;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if(MediaStageNew.reftesh_text!=null)
                                        MediaStageNew.reftesh_text.setValue("Next Ball in "+(final_timer-current_timer)+" Secs Remaining Balls "+ ((PracticeScreen.practiceBean.getOvers()*6)-PracticeScreen.practiceBean.getCurrent_balls()));
                                }
                            });
                        }
                    }else{
                        final_timer=0;
                        current_timer=0;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                HandleEvents.handleEvent(Variables.button_type_play, 0);
                            }
                        });
                        MediaStageNew.reftesh_text=null;
                    }                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
        //active = false;
    }
}
