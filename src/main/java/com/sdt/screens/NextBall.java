/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.*;
import com.sdt.displaycomponents.SpeedButton1;
import com.sdt.serial.HandleSerial;
import com.sdt.serial.USB_Com;
import com.sdt.xml.ScriptFiles;
import java.util.ArrayList;
import java.util.Arrays;

import zapcricketsimulator.ActTime;
import zapcricketsimulator.HandleEvents;

/**
 *
 * @author Srikanth
 */

public class NextBall {
    public static BallBean ballBean = new BallBean();
    public static int randon_speed = 0;
    public static int temp_mode = 0;
    public static boolean manual = false;
    public static boolean selectionManual = false;
    public static int temp_val = 0;
    public static int type = 0;
    public static int current_over = 0;
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



        ModeDatBean m  = HandleEvents.generalSettings.getModeData();
//        if ()
//        ballBean.bow
        int uPos = type;
        if (HandleEvents.gameBean.getBowler_selection() == 1) {
            int overs =
                    playerGameBean.getBall_count() / 6;
            if (overs != current_over) {
                current_over = overs;
                int prv_config = m.getBowling_type()[type];
                int current_config = m.getBowling_type()[type + 1];
                System.out.println("prev = " + prv_config + " conv = " + current_config);
                ActTime.previousConfig(prv_config, current_config);
            }
            type = overs % m.getBowler_path().length;
            uPos = type;
        }
        ballBean.setBall_release(m.getTrigger_interval()[uPos]);
        ballBean.setBowler_path(m.getBowler_path()[uPos]);
//        int sp = m.getBowling_speed()[uPos]
        int sp = (m.getBowling_speed()[uPos][skill - 1]);
        if (manual) {
            sp = HandleEvents.machineDataBean.getSet_speed();
        }
        HandleEvents.machineDataBean.setSet_speed(sp);
        SpeedButton1.updateSpeed(sp);
//        if (HandleEvents.machineDataBean)
        ballBean.setBall_speed_1(sp * 4);
        ballBean.setBall_speed_2(sp * 4);
        ballBean.setBall_speed_3(sp * 4);
        ballBean.setBall_speed_4(sp * 4);

//        System.out.println("written data cmdd");
        System.out.println("--------------------------------------------");
        System.out.println("release " + ballBean.getBall_release());
        System.out.println("path " + ballBean.getBowler_path());
        System.out.println("speed" + ballBean.getBall_speed_1());
        System.out.println("speed script" + ballBean.getBall_speed_4());
        System.out.println("type " +    m.getBowling_type()[uPos]);
        System.out.println( "bowling type " + HandleEvents.gameBean.getBowler_selection());
        System.out.println("balls "+  playerGameBean.getBall_count());
        System.out.println("type " + uPos);
        System.out.println("--------------------------------------------");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
//        ArrayList<KeyValueBean> Bowling_Types = new ArrayList(Arrays.asList(new KeyValueBean(3, "Speed"),new KeyValueBean(6, "Off_Spin"),new KeyValueBean(7, "Leg_Spin"),new KeyValueBean(8, "Seem_In"),new KeyValueBean(9, "Seem_Out"),new KeyValueBean(12, "Seem_Magic"),new KeyValueBean(13, "Spin_Magic"),new KeyValueBean(14, "Bowler_Magic")));
        //seamin /offspin left wheel up right wheel full down
        //leg spin /seam out left wheel down right wheel up
        int tp = m.getBowling_type()[uPos];
        if (tp == 6 || tp == 8) {
            ActTime.seamIn();
        } else if (tp == 9 || tp == 7) {
            ActTime.seamOut();
        }
        byte byteval[]=new byte[7];
        byteval[0]=(byte)ballBean.getBall_speed_1();
        int val = byteval[0];
        if(val<0)
            val=val+256;
//        HandleEvents.machineDataBean.setSet_speed(val);
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
        System.out.println("left speed done");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
//        byteval=new byte[9];
//        byteval[0]=15;
//        val = ballBean.getPan();
//        byteval[1]=(byte)(val>>8);
//        byteval[2]=(byte)(val&0xFF);
//        val = ballBean.getTilt();
//        byteval[3]=(byte)(val>>8);
//        byteval[4]=(byte)(val&0xFF);
//        val = ballBean.getLeft();
//        byteval[5]=(byte)(val>>8);
//        byteval[6]=(byte)(val&0xFF);
//        val = ballBean.getRight();
//        byteval[7]=(byte)(val>>8);
//        byteval[8]=(byte)(val&0xFF);
//        USB_Com.WriteData(getCmd1((byte)0x82,byteval));
//        System.out.println("right speed done");

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