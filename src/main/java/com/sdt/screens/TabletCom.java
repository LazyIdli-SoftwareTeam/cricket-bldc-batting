/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;


import com.sdt.data.PlayerGameBean;
import static com.sdt.displaycomponents.SpeedButton1.updateSpeed;
import com.sdt.logging.LogManager;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import java.io.BufferedReader;
import com.sdt.xml.HandleFile;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.sdt.xml.ScriptFiles;
import javafx.application.Platform;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import zapcricketsimulator.HandleEvents;
import static zapcricketsimulator.HandleEvents.gameBean;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class TabletCom implements Runnable{
    public static TabletCom this_obj = null;
    public static boolean active = false;
    public static void closeCom(){
        active = false;
        try {
            if(connectionSocket!=null){
                connectionSocket.close();
            }
            if(tcp_socket!=null){
                tcp_socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    public TabletCom(){
        if(!active){
            this_obj = this;
            active = true;
            try {
                new Thread(this).start();
            } catch (Exception e) {
                e.printStackTrace();
                active=false;
            }
        }
    }   
    boolean connected = false;
    public static ServerSocket tcp_socket = null;
    public static Socket connectionSocket = null;
    BufferedReader inFromClient = null;
    public static DataOutputStream outToClient =null;
    public void run(){
        System.out.println("TCP Listnetr started");
        while (active) {            
            try {
                if(!connected){
                    if(tcp_socket==null || !tcp_socket.isBound()) {
                        tcp_socket = new ServerSocket(HandleEvents.generalSettings.getTcp_port());
                        System.out.println("port "  +  HandleEvents.generalSettings.getTcp_port());
                    }
                    connectionSocket = tcp_socket.accept();
                    System.out.println("Client connected "+connectionSocket.getInetAddress());
                    inFromClient =  new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    connected = true;
                }else{
                    String readval = inFromClient.readLine();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //process_cmd(readval);
                            process_json_cmd(readval);
                        }
                    });                    
                    //System.out.println("Received: " + readval);
                    //System.out.println("Data Length: " + readval.length());
                    //outToClient.writeBytes("{\"command\":\"init\",\"machineId\":\"machine_1\",\"type\":\"home\"}\r");
                    if(readval==null)
                        connected = false;
                }
                Thread.sleep(100);
            } catch (Exception e) { 
                e.printStackTrace();
                connected = false;
            }            
        }
        
    }
    public void process_json_cmd(String readval){
        try {
            System.out.println("readval" + readval);
            JSONParser parser = new JSONParser();
            //System.out.println(readval);
            if(readval==null){
                LogManager.logError("Null Data Request Received with Connection");
                return;
            }
            JSONObject request = (JSONObject)parser.parse(readval);
            String command = request.get("command").toString();
            String type = request.get("type").toString();
            JSONObject response = new JSONObject();
            response.put("command", command);
            response.put("machine_id", HandleEvents.generalSettings.getAutoScotringBean().getSerial_no());
            response.put("speed", HandleEvents.machineDataBean.getSet_speed());
            switch(command){
                case "init":
                    System.out.println("init recived");
                    if(HandleEvents.game_mode==Variables.game_mode_sp){
                        //{"command":"init","machineId":"machine_1","type":"single"
                        //,"data":[{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                        response.put("type", "single");
                        JSONArray array = new JSONArray();
                        JSONObject player = new JSONObject();
                        array.add(player);  
                        response.put("data", array);
                        if(HandleEvents.game_status == Variables.game_status_started){
                            PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(0);
                            player.put("customerId",playerbean.getPlayer_id());
                            player.put("nickName",playerbean.getPlayer_name());
                            player.put("gameLevel",SinglePlayerScreen.bplayer_skill.getValue().toString());
//                            player.put("gameLevel",MultiPlayerScreen.skill_levels.get(playerbean.getSkill_level()-1).getValue());
                            player.put("totalOvers",HandleEvents.gameBean.getNo_of_overs_each());
                            player.put("bowlSelect",SinglePlayerScreen.boler_select.get(HandleEvents.gameBean.getBowler_selection()).getValue());
                        }else{
                            player.put("customerId","");
                            player.put("nickName","");
                            player.put("gameLevel",SinglePlayerScreen.bplayer_skill.getValue().toString());
//                            player.put("gameLevel",MultiPlayerScreen.skill_levels.get(1).getValue());
                            player.put("totalOvers",2);
                            player.put("bowlSelect",SinglePlayerScreen.boler_select.get(0).getValue());
                        }
                    }else if(HandleEvents.game_mode==Variables.game_mode_mp){
                        //{"command":"init","machineId":"machine_1","type":"multi","players":"2","data":[
			//{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"},
			//{"customerId":"customer2","nickName":"nickname2","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                        response.put("type", "multi");
                        response.put("players", HandleEvents.gameBean.getNo_of_players());
                        JSONArray array = new JSONArray();                          
                        response.put("data", array);
                        if(HandleEvents.game_status == Variables.game_status_started){
                            for(int i = 0 ; i <HandleEvents.gameBean.getNo_of_players();i++ ){
                                PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(i);
                                JSONObject player = new JSONObject();
                                array.add(player);
                                player.put("customerId",playerbean.getPlayer_id());
                                player.put("nickName",playerbean.getPlayer_name());
                                player.put("gameLevel",MultiPlayerScreen.bplayer_skills.get(i).getValue().toString());
//                                player.put("gameLevel",MultiPlayerScreen.skill_levels.get(playerbean.getSkill_level()-1).getValue());
                                player.put("totalOvers",HandleEvents.gameBean.getNo_of_overs_each());
                                player.put("bowlSelect",SinglePlayerScreen.boler_select.get(HandleEvents.gameBean.getBowler_selection()).getValue());
                            }
                        }else{
                            for(int i = 0 ; i <HandleEvents.gameBean.getNo_of_players();i++ ){
                                PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(i);
                                JSONObject player = new JSONObject();
                                array.add(player);
                                player.put("customerId","");
                                player.put("nickName","");
//                                player.put("gameLevel",MultiPlayerScreen.skill_levels.get(1).getValue());
                                player.put("gameLevel",MultiPlayerScreen.bplayer_skills.get(i).getValue().toString());
                                player.put("totalOvers",2);
                                player.put("bowlSelect",SinglePlayerScreen.boler_select.get(0).getValue());
                            }
                        }
                    }else{
                        response.put("type", "home");
                    }                    
                    break;
                case "action":
                    switch(type){
                        case "single":
                            System.out.println("commamnd");
                            if(request.get("data")==null){
                                //HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_sp);
                                HomeScreen1.this_obj.changeScreen(Variables.game_mode_sp);
                                response.put("type", "success");
                            }else{
                                //{"command":"action","type":"single","data":[
                                    //{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                                try {
                                    JSONArray array = (JSONArray)request.get("data");
                                    JSONObject player = (JSONObject)array.get(0);
                                    SinglePlayerScreen.bplayer_id.setText(player.get("customerId").toString());
                                    SinglePlayerScreen.bplayer_name.setText(player.get("nickName").toString());
//                                    System.out.println(player.get("skill").toString());
//                                    System
//                                    SinglePlayerScreen.bplayer_skill.selectValue(player.get("gameLevel").toString());
                                    SinglePlayerScreen.bplayer_skill.selectValue(Integer.parseInt(player.get("hand").toString()),Integer.parseInt(player.get("skill").toString()));
                                    SinglePlayerScreen.no_of_overs.getValueFactory().setValue(Integer.parseInt(player.get("totalOvers").toString()));
                                    SinglePlayerScreen.bboler_selection.selectValue(player.get("bowlSelect").toString());
                                    response.put("type", "started");
                                    HandleEvents.handleEvent(Variables.button_type_start, 0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println(e);
                                    response.put("type", "error");
                                }
                                
                            }
                            break;
                        case "multi":
                            if(request.get("data")==null){
                                //HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_mp);
                                HomeScreen1.this_obj.changeScreen(Variables.game_mode_mp);
                                response.put("type", "success");
                            }else{
                                //{"command":"action","type":"multi","players":"2","data":[
                                    //{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"},
                                    //{"customerId":"customer2","nickName":"nickname2","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                                try {
                                    JSONArray array = (JSONArray)request.get("data");
                                    int players = Integer.parseInt(request.get("players").toString());
                                    MultiPlayerScreen.bno_of_players.selectValue(players);                                    
                                    for(int i=0;i<players;i++){
                                        JSONObject player = (JSONObject)array.get(i);
                                        MultiPlayerScreen.no_of_overs.getValueFactory().setValue(Integer.parseInt(player.get("totalOvers").toString()));
                                        MultiPlayerScreen.bboler_selection.selectValue(player.get("bowlSelect").toString());
                                        MultiPlayerScreen.bplayer_ids.get(i).setText(player.get("customerId").toString());
                                        MultiPlayerScreen.bplayer_names.get(i).setText(player.get("nickName").toString());
                                        MultiPlayerScreen.bplayer_skills.get(i).selectValue(Integer.parseInt(player.get("hand").toString()),Integer.parseInt(player.get("skill").toString()));
//                                        MultiPlayerScreen.bplayer_skills.get(i).selectValue(player.get("gameLevel").toString());
                                    }
                                    response.put("type", "started");
                                    HandleEvents.handleEvent(Variables.button_type_start, 0);
                                } catch (Exception e) {
                                    response.put("type", "error");
                                }
                                
                            }
                            break;
                        case "pause":
                            HandleEvents.handleEvent(Variables.button_type_pause, 0);
                            response.put("type", "paused");
                            break;
                        case "play":
//                            HandleEvents.initData();
//
//                            HandleFile.readData();

                            ScriptFiles.loadScripts();
//                            bowler_pos = generalSettings.getDefault_bowler();
//                            workingDir = System.getProperty("user.dir");
                            HandleEvents.handleEvent(Variables.button_type_play, 0);
                            response.put("type", "playing");
                            break;
                        case "emg_exit":
                            HandleEvents.handleEvent(Variables.button_type_emg, 0);
                            response.put("type", "emg_exited");
                            break;
                        case "refresh":
                            //response.put("type", "refresh");
                            if(HandleEvents.game_mode==Variables.game_mode_sp){
                                //{"command":"init","machineId":"machine_1","type":"single"
                                //,"data":[{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                                response.put("type", "single");
                                JSONArray array = new JSONArray();
                                JSONObject player = new JSONObject();
                                array.add(player);  
                                response.put("data", array);
                                if(HandleEvents.game_status == Variables.game_status_started){
                                    PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(0);
                                    player.put("customerId",playerbean.getPlayer_id());
                                    player.put("nickName",playerbean.getPlayer_name());
                                    player.put("gameLevel",SinglePlayerScreen.bplayer_skill.getValue().toString());
//                                    player.put("gameLevel",MultiPlayerScreen.skill_levels.get(playerbean.getSkill_level()-1).getValue());
                                    player.put("totalOvers",HandleEvents.gameBean.getNo_of_overs_each());
                                    player.put("bowlSelect",SinglePlayerScreen.boler_select.get(HandleEvents.gameBean.getBowler_selection()).getValue());
                                }else{
                                    player.put("customerId","");
                                    player.put("nickName","");
                                    player.put("gameLevel",SinglePlayerScreen.bplayer_skill.getValue().toString());
//                                    player.put("gameLevel",MultiPlayerScreen.skill_levels.get(1).getValue());
                                    player.put("totalOvers",2);
                                    player.put("bowlSelect",SinglePlayerScreen.boler_select.get(0).getValue());
                                }
                            }else if(HandleEvents.game_mode==Variables.game_mode_mp){
                                //{"command":"init","machineId":"machine_1","type":"multi","players":"2","data":[
                                //{"customerId":"customer1","nickName":"nickname1","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"},
                                //{"customerId":"customer2","nickName":"nickname2","gameLevel":"Beginner/Intermediate/Club Player/Professional","totalOvers":2,"bowlSelect":"Auto/Manual"}]}
                                response.put("type", "multi");
                                response.put("players", HandleEvents.gameBean.getNo_of_players());
                                JSONArray array = new JSONArray();                          
                                response.put("data", array);
                                if(HandleEvents.game_status == Variables.game_status_started){
                                    for(int i = 0 ; i <HandleEvents.gameBean.getNo_of_players();i++ ){
                                        PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(i);
                                        JSONObject player = new JSONObject();
                                        array.add(player);
                                        player.put("customerId",playerbean.getPlayer_id());
                                        player.put("nickName",playerbean.getPlayer_name());
                                        player.put("gameLevel",MultiPlayerScreen.bplayer_skills.get(i).getValue().toString());
//                                      player.put("gameLevel",MultiPlayerScreen.skill_levels.get(playerbean.getSkill_level()-1).getValue());
                                        player.put("totalOvers",HandleEvents.gameBean.getNo_of_overs_each());
                                        player.put("bowlSelect",SinglePlayerScreen.boler_select.get(HandleEvents.gameBean.getBowler_selection()).getValue());
                                    }
                                }else{
                                    for(int i = 0 ; i <HandleEvents.gameBean.getNo_of_players();i++ ){
                                        PlayerGameBean playerbean = HandleEvents.gameBean.getPlayer_data().get(i);
                                        JSONObject player = new JSONObject();
                                        array.add(player);
                                        player.put("customerId","");
                                        player.put("nickName","");
                                        player.put("gameLevel",MultiPlayerScreen.bplayer_skills.get(i).getValue().toString());
//                                        player.put("gameLevel",MultiPlayerScreen.skill_levels.get(1).getValue());
                                        player.put("totalOvers",2);
                                        player.put("bowlSelect",SinglePlayerScreen.boler_select.get(0).getValue());
                                    }
                                }
                            }else{
                                response.put("type", "home");
                            }
                            break;
                        default:
                            response.put("type", "error");
                            break;
                    }
                    break;
                case "bowler":
                    int pos = Integer.parseInt(request.get("type").toString());
                     HandleEvents.handleEvent(Variables.button_type_bowler, pos);
                    response.put("type", "1");
                    break;
                case "function":
                    switch(type){
                        case "speed_up":
                            if(HandleEvents.game_status == Variables.game_status_none ||  HandleEvents.game_status == Variables.game_status_init){
                                if(HandleEvents.machineDataBean.getSet_speed()<150 && HandleEvents.machineDataBean.getSet_speed()>=40){
                                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()+1);
                                    updateSpeed(HandleEvents.machineDataBean.getSet_speed());
                                    HandleSerial.handleCom(HandleSerial.update_speed);                        
                                }
                            }
                            if(HandleEvents.game_mode==Variables.game_mode_sp){
                                if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()>=1 && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()<4){
                                    HandleEvents.gameBean.getPlayer_data().get(0).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()+1);
//                                    System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                    System.out.println(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                    SinglePlayerScreen.bplayer_skill.selectValue(HandleEvents.gameBean.getPlayer_data().get(0).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                }
                            }
                            if(HandleEvents.game_mode==Variables.game_mode_mp){
                                if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()>=1 && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()<4){
                                    HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()+1);
//                                    System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                    System.out.println(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                    MultiPlayerScreen.bplayer_skills.get(gameBean.getSeq_pos()).selectValue(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                    
                                }
                            }
                            response.put("type", "speed_up");
                            response.put("speed", HandleEvents.machineDataBean.getSet_speed());
//                            response.put("skillspeed",SinglePlayerScreen.bplayer_skill.getValue().toString());
                            break;
                        case "speed_down":
                            if(HandleEvents.game_status == Variables.game_status_none ||  HandleEvents.game_status == Variables.game_status_init){
                                if(HandleEvents.machineDataBean.getSet_speed()<=150 && HandleEvents.machineDataBean.getSet_speed()>40){
                                    HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
                                    updateSpeed(HandleEvents.machineDataBean.getSet_speed());
                                    HandleSerial.handleCom(HandleSerial.update_speed);                        
                                }
                            }
                            if(HandleEvents.game_mode==Variables.game_mode_sp){
                                if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()<=4 && HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()>1){
                                    HandleEvents.gameBean.getPlayer_data().get(0).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()-1);
//                                    System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                    System.out.println(HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                    SinglePlayerScreen.bplayer_skill.selectValue(HandleEvents.gameBean.getPlayer_data().get(0).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level());
                                }
                            }
                            if(HandleEvents.game_mode==Variables.game_mode_mp){
                                if(HandleEvents.game_status == Variables.game_status_started && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()<=4 && HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()>1){
                                    HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).setSkill_level(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level()-1);
//                                    System.out.println("VALUE UPDATED"+gameBean.getSeq_pos());
//                                    System.out.println(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                    MultiPlayerScreen.bplayer_skills.get(gameBean.getSeq_pos()).selectValue(HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getHand_usage(),HandleEvents.gameBean.getPlayer_data().get(gameBean.getSeq_pos()).getSkill_level());
                                    
                                }
                            }
//                            HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
//                            HandleSerial.handleCom(HandleSerial.update_speed);
                            response.put("type", "speed_down");
                            response.put("speed", HandleEvents.machineDataBean.getSet_speed());
                            break;
                        case "pan_left":
                            HandleSerial.handleCom(HandleSerial.pan_left);
                            response.put("type", "pan_left");
                            break;
                        case "pan_right":
                            HandleSerial.handleCom(HandleSerial.pan_right);
                            response.put("type", "pan_right");
                            break;
                        case "tilt_up":
                            HandleSerial.handleCom(HandleSerial.tilt_up);
                            response.put("type", "tilt_up");
                            break;
                        case "tilt_down":
                            HandleSerial.handleCom(HandleSerial.tilt_down);
                            response.put("type", "tilt_down");
                            break;
                        default:
                            response.put("type", "error");
                            break;
                    }
                    break;
                case "result":
                    response.put("type", type);
                    System.out.println(type);
                    switch(type){
                        case "1LEG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 1);
                            break;
                        case "1OFF":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_off, 1);
                            break;
                        case "1STG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 1);
                            break;
                        case "2LEG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 2);
                            break;
                        case "2OFF":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_off, 2);
                            break;
                        case "2STG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 2);
                            break;
                        case "3LEG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 3);
                            break;
                        case "3OFF":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_off, 3);
                            break;
                        case "3STG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 3);
                            break;
                        case "4LEG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 4);
                            break;
                        case "4OFF":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_off, 4);
                            break;
                        case "4STG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 4);
                            break;
                        case "6LEG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_leg,6);
                            break;
                        case "6OFF":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_off, 6);
                            break;
                        case "6STG":
                            HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 6);
                            break;
                        case "NoRun":
                            HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                            break;
                        case "NoBall":
                            HandleEvents.handleEvent(Variables.button_type_result_freehit, 0);
                            break;
                        case "Out":
                            HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                            break;
                        case "Wide":
                            HandleEvents.handleEvent(Variables.button_type_result_wide, 0);
                            break;    
                        default:
                            HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                    }
                    break;
            }
            System.out.println("Response: " +response.toJSONString());
            outToClient.writeBytes(response.toJSONString()+"\r");
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    String [] game_mode = {"","One Over Each","Two Over Each","Normal Game"};
    String [] team_batting_first = {"","Team A","Team B"};
    public void process_cmd(String readval){
        try {
            switch(readval){
                case "ZAPCSYNC":
                    if(HandleEvents.game_mode==Variables.mode_sellection){
                        outToClient.writeBytes("MAINPAGE\r");
                    }else if(HandleEvents.game_mode==Variables.game_mode_target){
                        if(HandleEvents.game_status==Variables.game_status_init){
                            outToClient.writeBytes("TGMODE,IDLE\r");
                        }else{
                            //TGMODE,STARTED,(PLAYERNAME),(PLAYERMOB) ,(TARGETSCORE),(TARGETBALLS),(CURRENTSCORE),(CURRENTBALLS)    
                            outToClient.writeBytes("TGMODE,STARTED,"+TargetScreen.targetBean.getName()+","+TargetScreen.targetBean.getMob()
                                    +","+TargetScreen.targetBean.getTarget_score()+","+TargetScreen.targetBean.getTarget_bals()+","
                                    +TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+"\r");
                        }
                    }else if(HandleEvents.game_mode==Variables.game_mode_practice){
                        if(HandleEvents.game_status==Variables.game_status_init){
                            outToClient.writeBytes("PCMODE,IDLE\r");
                        }else{
                            //PCMODE,STARTED ,(PLAYERNAME),(PLAYERMOB), (TOTALOVERS),(DELAY_BTW_OVERS),(DELAY_BTW_BALLS),(CURRENT_BALLS)
                             outToClient.writeBytes("PCMODE,STARTED,"+PracticeScreen.practiceBean.getName()+","+PracticeScreen.practiceBean.getMob()
                                     +","+PracticeScreen.practiceBean.getOvers()+","+PracticeScreen.practiceBean.getOver_delay()
                                     +","+PracticeScreen.practiceBean.getBall_dilay()+","+PracticeScreen.practiceBean.getCurrent_balls()+"\r");
                        }
                    }else if(HandleEvents.game_mode==Variables.game_mode_match){
                        if(HandleEvents.game_status==Variables.game_status_init){
                            outToClient.writeBytes("MAMODE,IDLE\r");
                        }else{
                            //MAMODE,STARTED , TEAM Playing First, Game Mode,(NO OF Players), (TEAM A NAME) (TEAM A MOB),(TEAM A PLAYER1,TEAM A PLAYER2…),
                            //(TEAM A SCORE) ,(TEAM A OVERS),(TEAM B NAME), (TEAM B MOB) ),(TEAM B PLAYER1,TEAM B PLAYER2…), (TEAM B SCORE), (TEAM B OVERS), 
                            //(CURRENT TEAM A PLAYER),(CURRENT TEAM B PLAYER),MAEND
                            
                            String players_a = "";
                            String players_b = "";
                            for(int i=0;i<8;i++){
                                if(i<MatchScreen.matchBean.getNo_of_players()){
                                    if(MatchScreen.matchBean.getTeamA_player_names()==null ||MatchScreen.matchBean.getTeamA_player_names().size()<=i ){
                                        players_a = players_a+"Player A"+(i+1)+",";
                                    }else{
                                         players_a = players_a+MatchScreen.matchBean.getTeamA_player_names().get(i)+",";
                                    }
                                    if(MatchScreen.matchBean.getTeamB_player_names()==null ||MatchScreen.matchBean.getTeamB_player_names().size()<=i ){
                                        players_b = players_b+"Player B"+(i+1)+",";
                                    }else{
                                        players_b = players_b+MatchScreen.matchBean.getTeamB_player_names().get(i)+",";
                                    }
                                }else{
                                    players_a = players_a+",";
                                    players_b = players_b+",";
                                }
                            }
                            String responce = "MAMODE,STARTED,"+team_batting_first[MatchScreen.matchBean.getPlaying_team()]+","+game_mode[MatchScreen.matchBean.getGame_type()]+","
                                    +MatchScreen.matchBean.getNo_of_players()+ ","+MatchScreen.matchBean.getTeama_name()+","+MatchScreen.matchBean.getTeama_mob()+","+players_a
                                    +MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","
                                    +MatchScreen.matchBean.getTeamb_name()+","+MatchScreen.matchBean.getTeamb_mob()+","+players_b
                                    +MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+","
                                    +MatchScreen.playera_name.getText()+","+MatchScreen.playerb_name.getText()+",MAEND";
                            outToClient.writeBytes(responce+"\r");
                        }
                    }else if(HandleEvents.game_mode==Variables.game_mode_sixer){
                        if(HandleEvents.game_status==Variables.game_status_init){
                            outToClient.writeBytes("SCMODE,IDLE\r");
                        }else{
                            //SCMODE, STARTED, (PLAYERNAME),(PLAYERMOB), (TARGETBALLS),(TARGETSIXES),(CURRENTBALLS),(ACHIEVED_SIXES)
                            outToClient.writeBytes("SCMODE,STARTED,"+SixerChallengeScreen.sixerchallengeBean.getName()+","+SixerChallengeScreen.sixerchallengeBean.getMob()
                                    +","+SixerChallengeScreen.sixerchallengeBean.getBalls()+","+SixerChallengeScreen.sixerchallengeBean.getSixers()+","
                                    +SixerChallengeScreen.sixerchallengeBean.getCurrent_balls()+","+SixerChallengeScreen.sixerchallengeBean.getCurrent_sixers()+"\r");
                        }
                    }
                    break;
                case "TGMODE":
                    if(HandleEvents.game_mode==Variables.mode_sellection){
                        HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_target);
                        new TargetScreen(HomeScreen.this_obj.pane,HomeScreen.this_obj.width,HomeScreen.this_obj.height);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGPAUS":
                case "PCPAUS":
                case "MAPAUS":
                case "SCPAUS":
                    if(HandleEvents.game_status==Variables.game_status_started){
                        HandleEvents.handleEvent(Variables.button_type_pause, 0);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "PCPLAY":
                case "TGPLAY":
                case "MAPLAY":
                case "SCPLAY":
                    if(HandleEvents.game_status!=Variables.game_status_init){
                        HandleEvents.handleEvent(Variables.button_type_play, 0);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "PCBWL1F":
                case "TGBWL1F":
                case "MABWL1F":
                case "SCBWL1F":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 1);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL2M":
                case "PCBWL2M":
                case "MABWL2M":
                case "SCBWL2M":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 2);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL3O":
                case "PCBWL3O":
                case "MABWL3O":
                case "SCBWL3O":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 3);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL4L":
                case "PCBWL4L":
                case "MABWL4L":
                case "SCBWL4L":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 4);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL5S":
                case "PCBWL5S":
                case "MABWL5S":
                case "SCBWL5S":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 5);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL6B":
                case "PCBWL6B":
                case "MABWL6B":
                case "SCBWL6B":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 6);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL7C":
                case "PCBWL7C":
                case "MABWL7C":
                case "SCBWL7C":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 7);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGBWL8D":
                case "PCBWL8D":
                case "MABWL8D":
                case "SCBWL8D":
                    if(HandleEvents.game_status == Variables.game_status_init||HandleEvents.game_status == Variables.game_status_paused||HandleEvents.game_sub_status == Variables.game_sub_status_idle){
                        HandleEvents.handleEvent(Variables.button_type_bowler, 8);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR1OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 1);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR1OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 1);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR1LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 1);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR1LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 1);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR1STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 1);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR1STG":
                     if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 1);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR2OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 2);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR2OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 2);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR2LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 2);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR2LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 2);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR2STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 2);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR2STG":
                     if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 2);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR3OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 3);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR3OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 3);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR3LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 3);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR3LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 3);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR3STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 3);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR3STG":
                     if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 3);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR4OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 4);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR4OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 4);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR4LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 4);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR4LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 4);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR4STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 4);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR4STG":
                     if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 4);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR6OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 6);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR6OFF":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_off, 6);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR6LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 6);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR6LEG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_leg, 6);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR6STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 6);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR6STG":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_runs_straight, 6);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCR00":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCR00":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRWIDE":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_wide, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRWIDE":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_wide, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRNOBAL":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_noball, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRNOBAL":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_noball, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRFRHIT":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_freehit, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRFRHIT":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_freehit, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRBWLD":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRBWLD":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_bowled, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRCATCH":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_catch, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRCATCH":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_catch, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRLBW":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_lbw, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRLBW":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_lbw, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MASCRSTMPD":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_stumped, 0);
                        //MASCR, Tram A Player*, current_score Team A,current_balls Team A, Tram B Player*,current_score Team B,current_balls Team B, MAEND
                        if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                        else
                            outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "TGSCRSTMPD":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_result_stumped, 0);
                        //TGSCR,current_score,current_balls,TGEND
                        outToClient.writeBytes("TGSCR,"+TargetScreen.targetBean.getCurrent_score()+","+TargetScreen.targetBean.getCurrent_balls()+",TGEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "PCMODE":
                    if(HandleEvents.game_mode==Variables.mode_sellection){
                        HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_practice);
                        new PracticeScreen(HomeScreen.this_obj.pane,HomeScreen.this_obj.width,HomeScreen.this_obj.height);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "PCAUTOBWL":
                    if(HandleEvents.game_mode==Variables.game_mode_practice){
                        HandleEvents.handleEvent(Variables.button_type_auto_bowler_sel, 0);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "PCMANUBWL":
                    if(HandleEvents.game_mode==Variables.game_mode_practice){
                        HandleEvents.handleEvent(Variables.button_type_man_bowler_sel, 0);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MAMODE":
                    if(HandleEvents.game_mode==Variables.mode_sellection){
                        HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_match);
                        new MatchScreen(HomeScreen.this_obj.pane,HomeScreen.this_obj.width,HomeScreen.this_obj.height);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "SCMODE":
                    if(HandleEvents.game_mode==Variables.mode_sellection){
                        HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_sixer);
                        new SixerChallengeScreen(HomeScreen.this_obj.pane,HomeScreen.this_obj.width,HomeScreen.this_obj.height);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "SCSIXNOO":                    
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_six, 0);                        
                        outToClient.writeBytes("SCSCR,"+SixerChallengeScreen.sixerchallengeBean.getCurrent_sixers()+","+SixerChallengeScreen.sixerchallengeBean.getCurrent_balls()+",SCEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "SCSIXYES":
                    if(HandleEvents.game_sub_status==Variables.game_sub_status_bowled){  
                        HandleEvents.handleEvent(Variables.button_type_six, 1);                        
                        outToClient.writeBytes("SCSCR,"+SixerChallengeScreen.sixerchallengeBean.getCurrent_sixers()+","+SixerChallengeScreen.sixerchallengeBean.getCurrent_balls()+",SCEND\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN SPEED INC":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        if(HandleEvents.machineDataBean.getSet_speed()<150){
                            HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()+1);
                            HandleSerial.handleCom(HandleSerial.update_speed);
                            outToClient.writeBytes("MCHN_SPEED "+HandleEvents.machineDataBean.getSet_speed()+"\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN SPEED DEC":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        if(HandleEvents.machineDataBean.getSet_speed()>50){
                            HandleEvents.machineDataBean.setSet_speed(HandleEvents.machineDataBean.getSet_speed()-1);
                            HandleSerial.handleCom(HandleSerial.update_speed);
                            outToClient.writeBytes("MCHN_SPEED "+HandleEvents.machineDataBean.getSet_speed()+"\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN TLTUP":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        HandleSerial.handleCom(HandleSerial.tilt_up);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN TLTDN":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        HandleSerial.handleCom(HandleSerial.tilt_down);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN PANRT":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        HandleSerial.handleCom(HandleSerial.pan_right);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN PANLT":
                    if(HandleEvents.game_mode!=Variables.mode_sellection){
                        HandleSerial.handleCom(HandleSerial.pan_left);
                        outToClient.writeBytes("ACKOK\r");
                    }else{
                        outToClient.writeBytes("ACKERROR\r");
                    }
                    break;
                case "MCHN EMG":
                    HandleEvents.handleEvent(Variables.button_type_emg, 0);
                    outToClient.writeBytes("MAINPAGE\r");
                    break;
                default:
                    if(readval.contains("TGSTR")){
                        StringTokenizer st1 =new StringTokenizer(readval, ",");
                        if(st1.countTokens()==6){
                            st1.nextToken();
                            TargetScreen.bname.setText(st1.nextToken());
                            TargetScreen.bmob.setText(st1.nextToken());
                            try {
                                TargetScreen.runs.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                                TargetScreen.balls.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }                            
                            HandleEvents.handleEvent(Variables.button_type_start, 0);
                            outToClient.writeBytes("ACKOK\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }                        
                    }else if(readval.contains("PCSTR")){
                        //PCSTR, PlayerName, PlayerMobno, Noofovers, delbwballs, delaybwnovers ,PCSTEND
                        StringTokenizer st1 =new StringTokenizer(readval, ",");
                        if(st1.countTokens()==7){
                            st1.nextToken();
                            PracticeScreen.bname.setText(st1.nextToken());
                            PracticeScreen.bmob.setText(st1.nextToken());
                            try {
                                PracticeScreen.overs.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                                PracticeScreen.ball_delay.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                                PracticeScreen.over_delay.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            HandleEvents.handleEvent(Variables.button_type_start, 0);
                            outToClient.writeBytes("ACKOK\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }   
                    }else if(readval.contains("SCSTR")){
                        //SCSTR, PlayerName ,PlayerMobno ,Noofballs , noofsixes SCSTEND
                        StringTokenizer st1 =new StringTokenizer(readval, ",");
                        if(st1.countTokens()==6){
                            st1.nextToken();
                            SixerChallengeScreen.bname.setText(st1.nextToken());
                            SixerChallengeScreen.bmob.setText(st1.nextToken());
                            try {
                                SixerChallengeScreen.balls.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                                SixerChallengeScreen.sixer_to_hit.getValueFactory().setValue(Integer.parseInt(st1.nextToken()));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }       
                            HandleEvents.handleEvent(Variables.button_type_start, 0);
                            outToClient.writeBytes("ACKOK\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }   
                    }else if(readval.contains("MASTR")){                        
                        //MASTR ,MODE( 1)one over each 2) two over each 3) normal game )
                        //,TEAM Playing First ( 1) team A 2) team B), No of Players ,Team A name, Team A mob, (Team A Player 1, Team A Player2…) ,Team B name, Team A mob, (Team B Player 1, Team B Player2…) ,MASTEND
                        StringTokenizer st1 =new StringTokenizer(readval, ",");
                        //System.out.println(st1.countTokens());
                        if(st1.countTokens()==25){
                            st1.nextToken();
                            String game_mode= st1.nextToken();
                            if(game_mode.equals("One Over Each"))
                                MatchScreen.matchBean.setGame_type(1);
                            else if(game_mode.equals("Two Over Each"))
                                MatchScreen.matchBean.setGame_type(2);
                            else
                                MatchScreen.matchBean.setGame_type(1);                            
                            
                            try {
                                MatchScreen.matchBean.setNo_of_players(Integer.parseInt(st1.nextToken()));
                                String first_team= st1.nextToken();
                                if(first_team.equals("Team A"))
                                    MatchScreen.matchBean.setPlaying_team(1);
                                else if(first_team.equals("Team B"))
                                    MatchScreen.matchBean.setPlaying_team(2);
                                else
                                    MatchScreen.matchBean.setPlaying_team(1);
                                MatchScreen.matchBean.setTeama_name(st1.nextToken());
                                MatchScreen.matchBean.setTeama_mob(st1.nextToken());
                                MatchScreen.bteamaname.setText(MatchScreen.matchBean.getTeama_name());
                                MatchScreen.bteamamob.setText(MatchScreen.matchBean.getTeama_mob());
                                ArrayList<String> teamA_player_names= new ArrayList<>();
                                MatchScreen.matchBean.setTeamA_player_names(teamA_player_names);
                                for(int i=0;i<8;i++){
                                    if(i<MatchScreen.matchBean.getNo_of_players())
                                        teamA_player_names.add(st1.nextToken());
                                    else
                                        st1.nextToken();
                                }
                                MatchScreen.matchBean.setTeamb_name(st1.nextToken());
                                MatchScreen.matchBean.setTeamb_mob(st1.nextToken());
                                MatchScreen.bteambname.setText(MatchScreen.matchBean.getTeamb_name());
                                MatchScreen.bteambmob.setText(MatchScreen.matchBean.getTeamb_mob());
                                ArrayList<String> teamB_player_names= new ArrayList<>();
                                MatchScreen.matchBean.setTeamB_player_names(teamB_player_names);
                                for(int i=0;i<8;i++){
                                    if(i<MatchScreen.matchBean.getNo_of_players())
                                        teamB_player_names.add(st1.nextToken());
                                    else
                                        st1.nextToken();
                                }
                                st1.nextToken();
                                MatchScreen.playera_name.setText("");
                                MatchScreen.playerb_name.setText("");
                                MatchScreen.teama_score.setText("");
                                MatchScreen.teamb_score.setText("");
                                MatchScreen.teama_overs.setText("");
                                MatchScreen.teamb_overs.setText("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            HandleEvents.handleEvent(Variables.button_type_start, 0);
                            if(MatchScreen.matchBean.getCurrent_playing_team()==Variables.match_playing_team_A)
                                outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+"*,"+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+","+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");
                            else
                                outToClient.writeBytes("MASCR,"+MatchScreen.playera_name.getText()+","+MatchScreen.teama_score.getText()+","+MatchScreen.teama_overs.getText()+","+MatchScreen.playerb_name.getText()+"*,"+MatchScreen.teamb_score.getText()+","+MatchScreen.teamb_overs.getText()+",MAEND\r");                        
                            //outToClient.writeBytes("ACKOK\r");
                        }else{
                            outToClient.writeBytes("ACKERROR\r");
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
