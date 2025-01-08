/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.BallBean;
import com.sdt.data.PlayerGameBean;
import com.sdt.serial.HandleSerial;
import com.sdt.serial.USB_Com;
import com.sdt.xml.ScriptFiles;
import java.util.ArrayList;
import zapcricketsimulator.HandleEvents;

/**
 *
 * @author Srikanth
 */

public class NextBall {
    public static BallBean ballBean = new BallBean();
    public static int randon_speed = 0;
    public static int temp_mode = 0;
    public static int temp_val = 0;
    public static void planNextBall(){
        // skill test 
        int pos=HandleEvents.gameBean.getSeq_pos();            
        PlayerGameBean playerGameBean = HandleEvents.gameBean.getPlayer_data().get(pos);
        int bowler = HandleEvents.gameBean.getBowler_selection();
        int balls = playerGameBean.getBall_count();
        int skill = playerGameBean.getSkill_level();
        int hand_usage = playerGameBean.getHand_usage();
        String selectHand = "";
        switch (hand_usage){
            case 1:
                selectHand = "R";
                break;
            case 2:
                selectHand = "L";
                break;
            default :
                selectHand = "R";
                break;
        }
        ArrayList<BallBean> ball_list = ScriptFiles.script_map.get("Default "+selectHand+skill);
        if(ball_list!=null){
            ballBean = ball_list.get(balls%ball_list.size());
        }
        byte [] cmd1 = {35,(byte)0x12,7,13,(byte)250,0,100,0,100,0,100,0,0,0,0,0,0,0x40,33};//test do value
        cmd1[17]=USB_Com.getCRC(cmd1, 17);
        USB_Com.WriteData(cmd1);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        } 
         byte byteval[]=new byte[7];
        byteval[0]=(byte)ballBean.getBall_speed_1();
        int val = byteval[0];
        if(val<0)
            val=val+256;
        HandleEvents.machineDataBean.setSet_speed(val);
        val = ballBean.getBall_speed_2();
        byteval[1]=(byte)(val>>8);
        byteval[2]=(byte)(val&0xFF);
        val = ballBean.getBall_speed_3();
        byteval[3]=(byte)(val>>8);
        byteval[4]=(byte)(val&0xFF);
        val = ballBean.getBall_speed_4();
        byteval[5]=(byte)(val>>8);
        byteval[6]=(byte)(val&0xFF);        
        USB_Com.WriteData(getCmd1((byte)0xDD,byteval));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }        
        byteval=new byte[9];
        byteval[0]=15;
        val = ballBean.getPan();
        byteval[1]=(byte)(val>>8);
        byteval[2]=(byte)(val&0xFF);
        val = ballBean.getTilt();
        byteval[3]=(byte)(val>>8);
        byteval[4]=(byte)(val&0xFF);
        val = ballBean.getLeft();
        byteval[5]=(byte)(val>>8);
        byteval[6]=(byte)(val&0xFF);
        val = ballBean.getRight();
        byteval[7]=(byte)(val>>8);
        byteval[8]=(byte)(val&0xFF);
        USB_Com.WriteData(getCmd1((byte)0x82,byteval));

    }
    public static byte [] getCmd1(byte cmd,byte [] cmddata){
        byte data[] = new byte[6+cmddata.length];
        data[0]='#';
        data[1]=0x01;
        data[2]=cmd;
        data[3]=(byte)cmddata.length;
        for(int i=0;i<cmddata.length;i++)
           data[i+4] = cmddata[i];
        data[4+cmddata.length]=USB_Com.getCRC(data, 4+cmddata.length);
        data[5+cmddata.length]='!';
        return data;
    }
    public static byte [] getCmd1(byte cmd){
        byte data[] = new byte[6];
        data[0]='#';
        data[1]=0x01;
        data[2]=cmd;
        data[3]=0x00;
        data[4]=USB_Com.getCRC(data, 4);
        data[5]='!';
        return data;
    }
}
