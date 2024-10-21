/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.upload_data;

import com.sdt.data.BallInfo;
import com.sdt.data.GameBean;
import com.sdt.data.OverDataBean;
import com.sdt.data.PlayerGameBean;
import com.sdt.screens.MultiPlayerScreen;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author Srikanth
 */
public class GameDataHandler {
    public static boolean status = false;
    static String BASE_URL = "http://localhost:9090";
    static OkHttpClient client =null;
    public static void init(){
        try {
            client = new OkHttpClient();
        } catch (Exception e) {
        }
        
    }
    
    public static void check_status(){
        if(client==null )
            init();
        while(data.size()>0){
            String json = data.get(0);
            try {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                Request request = new Request.Builder().url(BASE_URL + "/sd/local/v1/save").post(body).build();
                Call call = client.newCall(request);
                Response response = call.execute();
                data.remove(0);
                //System.out.println("API Response = "+response.isSuccessful());
            } catch (Exception e) {
                //e.printStackTrace();
                break;
            }
        }
    }
    static ArrayList<String> data = new ArrayList<>();
    public static void pushData(GameBean gameBean){
        //check_status();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        for(int i=0;i<gameBean.getNo_of_players();i++){
            PlayerGameBean playergamebean = gameBean.getPlayer_data().get(i);
            String json = "{\n" +
                "  \"customerId\": \""+playergamebean.getPlayer_id()+"\",\n" +
                "  \"machineId\": \""+HandleEvents.generalSettings.getAutoScotringBean().getSerial_no()+"\",\n" +
                "  \"nickName\": \""+playergamebean.getPlayer_name()+"\",\n" +
                "  \"email\": \"test@straightdrive.tech\",\n" +
                "  \"phone\": \"+91000000000\",\n" ;
                if(HandleEvents.game_mode==Variables.game_mode_sp)
                    json =json+"  \"gameType\": \"single\",\n";
                else
                    json =json+"  \"gameType\": \"multi\",\n";
                json =json+"  \"gameLevel\": \""+MultiPlayerScreen.skill_levels.get((playergamebean.getSkill_level()-1)*2).getValue()+"\",\n" ;
                json =json+"  \"totalOvers\": "+gameBean.getNo_of_overs_each()+",\n" +
                "  \"totalRun\": "+playergamebean.getTotal_score()+",\n" +
                "  \"playedOn\": \""+sdf.format(cal.getTime())+"\",\n" +
                "  \"scoresByOver\": [\n" ;
                //"    {\n" ;
                for(int j=0;j<playergamebean.getOvers().size();j++){
                    OverDataBean overDataBean = playergamebean.getOvers().get(j);
                    if(j!=0)
                        json =json+",";
                    json =json+" {\n     \"overNo\": "+(j+1)+",\n" +
                        "      \"overData\": [\n" ;
                    for(int k=0;k<overDataBean.getBallsInfo().size();k++){
                        BallInfo ballinfo = overDataBean.getBallsInfo().get(k);
                        if(k!=0)
                            json =json+",";
                        json =json+"        {\n" +
                            "          \"ballNo\": "+(k+1)+",\n" +
                            "          \"ballSpeed\": "+ballinfo.getSpeed()+",\n" +
                            "          \"bowlingStyle\": \""+ballinfo.getType()+"\",\n" +
                            "          \"run\": \""+ballinfo.getResult()+"\"\n" +
                            "        }\n" ;
                    }                       
                    json =json+"      ]\n" +
                        "    }\n" ;
                }                 
                json =json+"  ]\n" +
                "}";
                //System.out.println("json log "+json);
                data.add(json);
                check_status();
        }
        
        
    }
    
    public static void startUpdate(GameBean gameBean){
        
    }
    public static void ballbyballUpdate(){
            String json = "{\n" +
                "  \"command\": \"score_update\",\n" + 
                "  \"machineId\": \""+HandleEvents.generalSettings.getAutoScotringBean().getSerial_no()+"\",\n" ;
                //    "  \"machineId\": \"machine_1\",\n" ;
            if(HandleEvents.game_mode==Variables.game_mode_sp){
                json =json+"  \"type\": \"single\",\n" ; 
            }else{
                json =json+"  \"type\": \"multi\",\n" ; 
            }
            json =json+"  \"totalOvers\": "+HandleEvents.gameBean.getNo_of_overs_each()+",\n" ; 
            json = json + "  \"data\": [\n" ;
            //
            for(int i=0;i<HandleEvents.gameBean.getNo_of_players();i++){
                PlayerGameBean playergamebean = HandleEvents.gameBean.getPlayer_data().get(i);
                json = json + "    {\n" +
                "      \"customerId\": \""+playergamebean.getPlayer_id()+"\",\n" +
                "      \"nickName\": \""+playergamebean.getPlayer_name()+"\",\n" +
                "      \"gameLevel\": \""+MultiPlayerScreen.skill_levels.get((playergamebean.getSkill_level()-1)*2).getValue()+"\",\n" +
                "      \"totalBalls\": "+playergamebean.getBall_count()+",\n" +
                "      \"runs\": "+playergamebean.getTotal_score()+",\n" ;
                if(i == HandleEvents.gameBean.getSeq_pos()){
                    json = json +"	  \"status\":\"Playing\",\n" ;
                    json = json +"      \"runByBalls\": [\n" ;                
                    if(playergamebean.getOvers().size()>0){
                        OverDataBean overdats = playergamebean.getOvers().get(playergamebean.getOvers().size()-1);
                        int ball = 0;
                        for(int j=0;j<overdats.getBallsInfo().size();j++){
                            if(j>0)
                                json = json+",";
                            String result = "";
                            if(overdats.getBallsInfo().get(j).getResult()<=6){
                                result = ""+overdats.getBallsInfo().get(j).getResult();
                                ball++;
                            }else if(overdats.getBallsInfo().get(j).getResult()==9){
                                result = "W";
                                ball++;
                            }else if(overdats.getBallsInfo().get(j).getResult()==Variables.button_type_result_freehit){
                                result = "Db";
                            }else if(overdats.getBallsInfo().get(j).getResult()==Variables.button_type_result_dedball){
                                result = "Db";
                            }else if(overdats.getBallsInfo().get(j).getResult()==Variables.button_type_result_bowled){
                                result = "W";
                                ball++;
                            }
                            json = json+"        {\n" +
                            "          \"ballNo\": "+ball+",\n" +
                            "          \"run\": \""+result+"\"\n" +
                            "           }\n" ;
                        }
                    }
                    json = json +"      ]\n" ;
                }else if(i < HandleEvents.gameBean.getSeq_pos()){
                    json = json +"	  \"status\":\"Played\",\n" ;
                    json = json +"      \"runByBalls\": [\n" ; 
                    json = json +"      ]\n" ;
                }else{
                    json = json +"	  \"status\":\" Yet to Play\",\n" ;
                    json = json +"      \"runByBalls\": [\n" ; 
                    json = json +"      ]\n" ;
                }
                json = json +"    },\n" ;
            }             
            json = json + "  ]\n" +
                "}";
            if(client==null ){
                check_status();                
            }
            try {
                JSONParser parser = new JSONParser();
                JSONObject json_obj = (JSONObject)parser.parse(json);
                //System.out.println(json_obj.toJSONString());
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json_obj.toJSONString());
                Request request = new Request.Builder().url(BASE_URL + "/sd/local/v1/currentgamedata/save").post(body).build();
                //System.out.println(BASE_URL + "/sd/local/v1/currentgamedata/save");
                Call call = client.newCall(request);
                Response response = call.execute();
                System.out.println("API Response = "+response.isSuccessful());
                System.out.println("API Response = "+response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
