/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.serial;

import com.sdt.displaycomponents.SpeedButton1;
import com.sdt.screens.NextBall;
import com.sdt.screens.TabletCom;
import javafx.application.Platform;
import jssc.SerialPort;
import org.json.simple.JSONObject;
import zapcricketsimulator.HandleEvents;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author possi
 */
public class HandleSerial {
    public static final int ball_release = 0xBB;
    public static final int update_speed = 0xDD;
    public static final int update_speed_skilltest = 0xDE;
    public static final int tilt_up = 0xCF;
    public static final int tilt_down = 0xCC;
    public static final int pan_left = 0xCD;
    public static final int pan_right = 0xCE;
    public static final int read_data = 0xE0;
    public static final int update_mode = 0xE1;
    public static final int ball_init = 0xE2;
    public static final int power_on = 0xE3;
    public static final int skill_test = 0xEB;
    public static final int bowler1_update = 0xF1;
    public static final int bowler2_update = 0xF2;
    public static final int bowler3_update = 0xF3;
    public static final int bowler4_update = 0xF4;
    public static final int bowler5_update = 0xF5;
    public static final int bowler6_update = 0xF6;
    public static final int bowler7_update = 0xF7;
    public static final int bowler8_update = 0xF8;

    public HandleSerial() throws IOException {
    }

    public static byte[] getCmd(int cmd , byte [] data,int data_len){
        byte [] cmd_data = new byte[5+data_len];    
        cmd_data[0] = '#';
        cmd_data[1] = (byte)cmd;
        cmd_data[2] = (byte)data_len;
        if(data_len>0){
            for(int i=0;i<data_len;i++){
                cmd_data[3+i] = data[i];
            }
        }
        cmd_data[3+data_len] = USB_Com.getCRC(cmd_data, 3+data_len);
        cmd_data[4+data_len] ='!';
        return cmd_data;
    }
    
         public static byte[] getCmd1(int cmd , byte [] data,int data_len){
            byte [] cmd_data = new byte[6+data_len];
            cmd_data[0] = '#';
            cmd_data[1] = 0x01;
            cmd_data[2] = (byte)cmd;
            cmd_data[3] = (byte)data_len;
            if(data_len>0){
                for(int i=0;i<data_len;i++){
                    cmd_data[4+i] = data[i];
                }
            }
            cmd_data[4+data_len] = USB_Com.getCRC(cmd_data, 4+data_len);
            cmd_data[5+data_len] ='!';
            return cmd_data;
        }
     public static byte[] getCmd12(int cmd , byte [] data,int data_len){
        byte [] cmd_data = new byte[6+data_len];    
        cmd_data[0] = '#';
        cmd_data[1] = 0x12;
        cmd_data[2] = (byte)cmd;
        cmd_data[3] = (byte)data_len;
        if(data_len>0){
            for(int i=0;i<data_len;i++){
                cmd_data[4+i] = data[i];
            }
        }
        cmd_data[4+data_len] = USB_Com.getCRC(cmd_data, 4+data_len);
        cmd_data[5+data_len] ='!';
        return cmd_data;
    }
    
    static String port_name="COM3";
    public static void printPorts(){
        String portlist []= USB_Com.getPortList();
        for(int i=0;i<portlist.length;i++){
            System.out.println(portlist[i]);
        }
    }
    public static void initSerial(){
        USB_Com.Connect(port_name, HandleEvents.generalSettings.getBaudrate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        new SerialReceive();
        if(USB_Com.status){
             byte [] cmd1 = {35,(byte)0x12,9,1,1,0x40,33};//stop setting Request
             USB_Com.WriteData(cmd1);
        }
    }

    public static void initSerial(String port){

        USB_Com.Connect(port, HandleEvents.generalSettings.getBaudrate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);        
        new SerialReceive();
        if(USB_Com.status){
             byte [] cmd1 = {35,(byte)0x12,9,1,1,0x40,33};//stop setting Request
            System.out.print("init serial done " + cmd1);
             USB_Com.WriteData(cmd1);
        }
    }
    public static boolean ball_released = false;
    public static boolean closing = false;

    public static void handleCom(int type){
        if(!USB_Com.status)
            return;
        byte data [] = new byte[1];
        byte data1 [] = new byte[4];
        switch(type){
            case ball_release:                
                //data[0]=(byte)ball_release;
                //Calendar cal = Calendar.getInstance();
                //USB_Com.WriteData(data);
                data1=getCmd1(ball_release, null, 0);
                System.out.println("ball realesed");
                try {
                    TabletCom.outToClient.writeByte(64);
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                USB_Com.WriteData(data1);
                if(HandleEvents.generalSettings.getAuto_scoring_enable()==1)
                    ball_released=true;               
                break;
            case read_data:
                data1=getCmd1(read_data, null, 0);
                USB_Com.WriteData(data1);
                break;
            case ball_init:
                data1=getCmd1(ball_init, null, 0);
                System.out.println("ball init");
                USB_Com.WriteData(data1);
                break;
            case power_on:
                //System.out.println(HandleEvents.machineDataBean.getSet_speed());
                if(closing){
                    byte [] cmd_data1 = {0,0};
                    data1=getCmd1(power_on, cmd_data1, 2);
                    System.out.println("power on cmd 1");
                }else{
                    byte [] cmd_data1 = {1,(byte)25};
                    data1=getCmd1(power_on, cmd_data1, 2);
                    System.out.println("power on cmd 2");
                }
                USB_Com.WriteData(data1);
                break;
            case skill_test://handles by software
               /* byte [] cmd_data2 = {(byte)HandleEvents.generalSettings.getSkill_test()};
                data1=getCmd(skill_test, cmd_data2,1);
                USB_Com.WriteData(data1);*/
                break;
            case update_mode:{
                byte [] cmd_data3 = {(byte)NextBall.temp_mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                USB_Com.WriteData(data1);
            }break;
            case update_speed:{
                //0xdd,speed,speed,0xdf
                int speed = HandleEvents.machineDataBean.getSet_speed();
                int speed_value = (int)(HandleEvents.machineDataBean.getSet_speed()*4);
                byte speed_byte1 = (byte)(speed_value>>8);
                byte speed_byte2 = (byte)(speed_value);
                byte [] cmd_data = {(byte)speed,speed_byte1,speed_byte2,speed_byte1,speed_byte2,0,0};
                data1=getCmd1(update_speed, cmd_data, 7);
                /*data1[1]=(byte)HandleEvents.current_speed;
                data1[2]=(byte)HandleEvents.current_speed;
                data1[3]=(byte)0xDF;*/
                USB_Com.WriteData(data1);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SpeedButton1.updateSpeed(HandleEvents.machineDataBean.getSet_speed());
                    }
                });
            }break;
            case update_speed_skilltest:{
                 byte [] cmd_data = {(byte)NextBall.randon_speed};
                data1=getCmd(update_speed, cmd_data, 1);
                USB_Com.WriteData(data1);
            }break;
            /*case tilt_up:
                data1=getCmd(tilt_up, null, 0);
                USB_Com.WriteData(data1);
                break;
            case tilt_down:
                data1=getCmd(tilt_down, null, 0);
                USB_Com.WriteData(data1);
                break;
            case pan_left:
                data1=getCmd(pan_left, null, 0);
                USB_Com.WriteData(data1);
                break;
            case pan_right:
                data1=getCmd(pan_right, null, 0);
                USB_Com.WriteData(data1);
                break;*/
            case bowler1_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[0][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[0][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[0][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[0][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
            }break;
            case bowler2_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[1][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[1][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[1][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[1][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[1];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler3_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[2][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[2][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[2][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[2][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[2];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler4_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[3][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[3][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[3][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[3][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[3];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler5_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[4][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[4][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[4][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[4][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[4];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler6_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[5][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[5][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[5][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[5][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[5];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler7_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[6][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[6][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[6][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[6][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[6];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                //System.out.println(speed+","+mode);
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
            case bowler8_update:{
                int speed = HandleEvents.generalSettings.getDefault_speed();
                switch(HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getSkill_level()){
                    case 1:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[7][0];
                        break;
                    case 2:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[7][1];
                        break;
                    case 3:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[7][2];
                        break;
                    case 4:
                        speed = HandleEvents.generalSettings.getModeData().getBowling_speed()[7][3];
                        break;
                }
                int mode = HandleEvents.generalSettings.getModeData().getBowling_type()[7];
                if(mode==12){
                    mode = 8;
                }else if(mode ==13){
                    mode = 6;
                }else if(mode ==14){
                    mode = HandleEvents.generalSettings.getModeData().getBowling_type()[0];
                }
                HandleEvents.machineDataBean.setSet_speed(speed);
                handleCom(update_speed);
                try {
                    Thread.sleep(800);
                } catch (Exception e) {
                }
                byte [] cmd_data3 = {(byte)mode};
                data1=getCmd(update_mode, cmd_data3, 1);
                 USB_Com.WriteData(data1);
            }break;
                
        }
    }
    
}
