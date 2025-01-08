/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.serial;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sdt.logging.WriteJsonFile;
import com.sdt.screens.AutoScoring;
import com.sdt.upload_data.GameDataHandler;
import javafx.application.Platform;
import org.json.simple.JSONObject;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.MediaStageNew;
import zapcricketsimulator.Variables;

/**
 *
 * @author Srikanth
 */
public class SerialReceive implements Runnable{
    public static boolean check_ball = false;
    public static boolean init_machine = false;
    public static boolean pridicting_score = false;
    public SerialReceive(){
        new DataReceiver();
        new Thread(this).start();        
    }
    int predicting_ctr = 0;
    boolean data_requested = false;
    int data [] = new int[1024];
    int ctr =0;
    int req_ctr=0;
    public void processData(){
        switch(data[2]){
            case 0xE0:
                if(data[3]==0x08){                    
                    //HandleEvents.machineDataBean.setSet_speed(data[4]);
                    HandleEvents.machineDataBean.getMotor_speed()[0]=data[5];
                    HandleEvents.machineDataBean.getMotor_speed()[1]=data[6];
                    HandleEvents.machineDataBean.getMotor_speed()[2]=data[7];
                    HandleEvents.machineDataBean.setPwm_start_stop(data[8]);
                    //HandleEvents.machineDataBean.setSwing_in_level(data[8]);
                    //HandleEvents.machineDataBean.setSwing_out_level(data[9]);
                    //HandleEvents.machineDataBean.setSpin_off(data[10]);
                    //HandleEvents.machineDataBean.setSpin_leg(data[11]);
                    //HandleEvents.machineDataBean.setSeem_in(data[12]);
                    //HandleEvents.machineDataBean.setSeem_out(data[13]);
                    //HandleEvents.machineDataBean.setBounce_low(data[14]);
                    //HandleEvents.machineDataBean.setBounce_high(data[15]);
                    //HandleEvents.machineDataBean.setOperating_mode(data[16]);
                    HandleEvents.machineDataBean.setBall_status(data[9]);
                    HandleEvents.machineDataBean.setLed_status(data[10]);
                    //HandleEvents.machineDataBean.setSkill_test(data[18]);
                    HandleEvents.machineDataBean.setRead_status(1);
                    System.out.println(HandleEvents.machineDataBean.getBall_status());
                }
                break;
            case 0x0F:
                if(HandleEvents.generalSettings.getAuto_scoring_enable()==1){
                    AutoScoring.process(data);
                }
                break;
            case 0x81:
                if (data[2 + 1] == 0x08) {
                    int pan = 0;
                    int tilt = 0;
                    int right_motor = 0;
                    int left_motor = 0;
                    pan = data[3 + 1];
                    pan = (pan << 8) | data[4 + 1];
                    tilt = data[5 + 1];
                    tilt = (tilt << 8) | data[6 + 1];
                    left_motor = data[7 + 1];
                    left_motor = (left_motor << 8) | data[8 + 1];
                    right_motor = data[9 + 1];
                    right_motor = (right_motor << 8) | data[10 + 1];
                    System.out.println("Pan " + pan);
                    System.out.println("Tilt " + tilt);
                    System.out.println("Left Motor " + left_motor);
                    System.out.println("Right Motor " + right_motor);
//                    ObjectNode o = new ObjectNode();
                    JSONObject o = new JSONObject();

                    o.put("pan", pan);

                    o.put("tilt", tilt);
                    o.put("leftMotor", left_motor);
                    o.put("rightMotor", pan);
                    WriteJsonFile.writeFile(o);
                }
                break;
        }
    }
    boolean first =true;
    public void run(){
        while(USB_Com.status){
            /*int temp = -1;
            temp = USB_Com.readByte(1000);
            //System.out.println(temp+","+ctr);
            if(temp!=-1){
                data[ctr++]=temp;
            }else{
                if(ctr>0){
                    processData();
                }                
                ctr=0;
            }*/
            if(init_machine){
                //System.out.println(HandleEvents.machineDataBean.getRead_status());
                if(first){
                    first=false;
                    if(MediaStageNew.error_status==0)
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                HandleEvents.handleEvent(Variables.button_type_ball_init, 0);//0xE2 error reset
                            }
                        });                                 
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }   
                    HandleEvents.machineDataBean.setRead_status(0);
                }
                if(HandleEvents.machineDataBean.getRead_status()==1){
                    //check if machine is on
                    //if not switch it on and wait for 5 sec before release ball
                    if(HandleEvents.machineDataBean.getPwm_start_stop()==1){
                          
                        if(HandleEvents.machineDataBean.getBall_status()==2){
                            if(MediaStageNew.error_status==0)
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        HandleEvents.handleEvent(Variables.button_type_ball_init, 0);//0xE2 error reset
                                    }
                                });                                 
                                try {
                                    Thread.sleep(1000);
                                } catch (Exception e) {
                                }   
                            //HandleEvents.machineDataBean.setRead_status(0);
                        }else if(HandleEvents.machineDataBean.getBall_status()==3){
                            if(MediaStageNew.error_status==0){
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        HandleEvents.handleEvent(Variables.button_type_ball_error, 0);
                                    }
                                });                                 
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                            }   
                            HandleEvents.machineDataBean.setRead_status(0);
                        }else if(HandleEvents.machineDataBean.getBall_status()==1){
                            init_machine = false;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if(check_ball){
                                        check_ball=false;
                                         HandleEvents.handleEvent(Variables.button_type_play, 0);
                                    }else{
                                        HandleEvents.handleEvent(Variables.mode_sellection, HandleEvents.game_mode);
                                    }
                                }
                            }); 
                        }else if(HandleEvents.machineDataBean.getBall_status()==0){
                            try {
                                Thread.sleep(3000);
                            } catch (Exception e) {
                            }   
                            HandleEvents.machineDataBean.setRead_status(0);
                        }

                }else{
                        HandleSerial.handleCom(HandleSerial.power_on);                                                
                        HandleEvents.machineDataBean.setRead_status(0);
                       /* try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                        }    */                          
                    }                                          
                }else if(HandleEvents.machineDataBean.getRead_status()==0){
                    HandleEvents.machineDataBean.setRead_status(2);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    } 
                    HandleSerial.handleCom(HandleSerial.read_data);
                }else if(HandleEvents.machineDataBean.getRead_status()==2){                       
                    req_ctr++;
                    if(req_ctr>20){
                        req_ctr=0;
                        HandleEvents.machineDataBean.setRead_status(0);
                    }
                }
            }else if(check_ball){
                //if ball found HandleEvents.handleEvent(Variables.button_type_play, 0);
                if(HandleEvents.machineDataBean.getRead_status()==1){                    
                    if(HandleEvents.machineDataBean.getBall_status()==1){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                HandleEvents.handleEvent(Variables.button_type_play, 0);
                            }
                        });                        
                        check_ball=false;                                
                    }else if(HandleEvents.machineDataBean.getBall_status()==2){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                HandleEvents.handleEvent(Variables.button_type_ball_init, 0);
                            }
                        });
                        
                    }else if(HandleEvents.machineDataBean.getBall_status()==3){
                        if(MediaStageNew.error_status==0){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    HandleEvents.handleEvent(Variables.button_type_ball_error, 0);
                                }
                            });                            
                        }
                    }else if(HandleEvents.machineDataBean.getBall_status()==0){
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                        }   
                    }
                    HandleEvents.machineDataBean.setRead_status(0);                     
                }else if(HandleEvents.machineDataBean.getRead_status()==0){                                     
                    HandleEvents.machineDataBean.setRead_status(2);
                    HandleSerial.handleCom(HandleSerial.read_data);
                }else{
                    
                }                
            }else{
                
                
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            if(pridicting_score ){
                predicting_ctr++;
                if(predicting_ctr>6){
                    pridicting_score=false;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            HandleEvents.handleEvent(Variables.button_type_result_dedball, 0);
                        }
                    });
                }else{
                    
                }
            }else{
                predicting_ctr=0;
            }
        }
    }
    class DataReceiver implements Runnable{
        public DataReceiver(){
            new Thread(this).start();
        }
        public void run(){
            while(USB_Com.status){
                int temp = -1;
                temp = USB_Com.readByte(1000);
                //System.out.println(temp+","+ctr);
                if(temp!=-1){
                    data[ctr++]=temp;
                }else{
                    if(ctr>0){
                        processData();
                    }                
                    ctr=0;
                }
                
            }
        }
    }
}
