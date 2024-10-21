/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zapcricketsimulator;

import com.sdt.data.BallInfo;
import com.sdt.data.GameBean;
import com.sdt.data.GeneralSettingsBean;
import com.sdt.data.MachineDataBean;
import com.sdt.data.MatchBean;
import com.sdt.data.MatchDataBean;
import com.sdt.data.MatchResultBean;
import com.sdt.data.ModeDatBean;
import com.sdt.data.OverDataBean;
import com.sdt.data.PlayerGameBean;
import com.sdt.data.SixerChallengeBean;
import com.sdt.screens.HomeScreen1;
import com.sdt.screens.MatchScreen;
import com.sdt.screens.PracticeScreen;
import com.sdt.screens.PracticeTimer;
import com.sdt.screens.SixerChallengeScreen;
import com.sdt.screens.TargetScreen;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import com.sdt.xml.HandleFile;
import com.sdt.logging.LogManager;
import com.sdt.screens.AutoScoring;
import com.sdt.screens.BowlingThread;
import com.sdt.screens.MultiPlayerScreen;
import com.sdt.screens.NextBall;
import com.sdt.screens.SinglePlayerScreen;
import com.sdt.screens.TabletCom;
import com.sdt.serial.SerialReceive;
import com.sdt.serial.USB_Com;
import com.sdt.serial.USB_ComExt;
import com.sdt.upload_data.GameDataHandler;
import com.sdt.xml.ScriptFiles;
import javafx.application.Platform;

/**
 *
 * @author possi
 */
public class HandleEvents {
    
    
    public static GeneralSettingsBean generalSettings = new GeneralSettingsBean();
    //public static ModeDatBean modeData[]=new ModeDatBean[4];
    public static MachineDataBean machineDataBean = new MachineDataBean();
    
    public static int game_mode=0;
    public static int prev_game_status=Variables.game_status_none;
    public static int game_status=Variables.game_status_none;
    public static int game_sub_status=0;
    public static int game_skill_level = 2;
    public static String workingDir =null;
    public static String bowler_path = "";
    public static int bowler_pos=1;
    public static int backup_bowler_pos=1;
    public static int bowler_trigger=1;
    public static int magic_mode=0;
    public static boolean wideNoBallScore = false;
    //public static int current_speed=60;
    public static GameBean gameBean= null;
    
    public static void initData(){
        LogManager.initLogger();
        HandleFile.readData();
        ScriptFiles.loadScripts();
        bowler_pos = generalSettings.getDefault_bowler();
        workingDir = System.getProperty("user.dir");
        System.out.println("working dir with the path" +  workingDir);
        LogManager.logInfo("working dir with the path" +  workingDir);

        if(generalSettings.getModeData()!=null && !generalSettings.getModeData().getBowler_path()[bowler_pos-1].equals("")){
            bowler_path = generalSettings.getModeData().getBowler_path()[bowler_pos-1];
            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1];
        }else{
            //ErrorAlert.info("Default Bowler Loaded");
            bowler_path = workingDir + "/Media/bowler/fast";
        }

        if(generalSettings.isTablet_mode_enable()){
            new TabletCom();
        }
        /*if(generalSettings.getAuto_scoring_enable()==1){
            new AutoScoring();
        }*/
        //new BowlingThread();
        System.out.println("got here");
        System.out.println(generalSettings.getCom_port());

        HandleSerial.initSerial(generalSettings.getCom_port());

        //USB_ComExt.Connect();
        //HandleSerial.handleCom(HandleSerial.skill_test);
        SerialReceive.init_machine=true;
        
        //HandleSerial.handleCom(HandleSerial.update_speed);
    }
    public static void handleEvent(int type ,int subtype){
        switch(type){
            case Variables.mode_sellection:
                //if(SerialReceive.init_machine)
                //    return;
                game_mode = subtype;
                game_status = Variables.game_status_init;
                MediaStageNew.this_obj.handlemode(game_mode);                
                break;
            case Variables.button_type_bowler:
                if(game_status == Variables.game_status_init||game_status == Variables.game_status_paused||game_sub_status == Variables.game_sub_status_idle){
                    bowler_pos=subtype;
                    backup_bowler_pos = subtype;
                    bowler_path=generalSettings.getModeData().getBowler_path()[bowler_pos-1];
                    bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1];
                    MediaStageNew.this_obj.handleBowling(Variables.bowler_intro);
                    if(game_status == Variables.game_status_paused){
                        game_status = prev_game_status;
                    }
                    HandleSerial.handleCom(0xF0+subtype);
                }               
                break;
            case Variables.button_type_ball_init:
            case Variables.button_type_ball_error:
                MediaStageNew.this_obj.handleErrorScreen(type);
                break;
            case Variables.button_type_play:                
                //if(game_sub_status==Variables.game_sub_status_bowled){
                //    System.out.println(MediaStageNew.mp.getStatus());
                //}
                //System.out.println("ball status "+machineDataBean.getBall_status());
                //System.out.println(MediaStageNew.player_change+","+game_status+","+game_sub_status);
                    
                if(MediaStageNew.player_change){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            NextBall.planNextBall();
                        }
                     });
                    //HandleSerial.handleCom(0xF0+bowler_pos);
                    MediaStageNew.player_change=false;
                    game_status = Variables.game_status_init;
                    MediaStageNew.this_obj.initPlayerSettings();                   
                    return;
                }else{
                    if(machineDataBean.getBall_status()!=1){
                    
                        HandleSerial.handleCom(HandleSerial.ball_init);
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                        MediaStageNew.error_status=0;
                        SerialReceive.check_ball=true;
                        HandleEvents.machineDataBean.setRead_status(0);
                        break;
                    }           
//                    MediaStageNew.error_status=0;
                    if(game_status == Variables.game_status_paused){
                        game_status = prev_game_status;
                        MediaStageNew.this_obj.handlePlay();
                    }else if(game_status == Variables.game_status_started && game_sub_status!=Variables.game_sub_status_bowled){
                        switch(game_mode){
                            case Variables.game_mode_sp:
                                if((gameBean.getNo_of_overs_each()*6)>gameBean.getPlayer_data().get(0).getBall_count()){
                                    MediaStageNew.this_obj.handleBowling(Variables.bowler_bowling);
                                    game_sub_status=Variables.game_sub_status_bowled;
                                }
                                break;
                            case Variables.game_mode_mp:
                                if(gameBean.getSeq_pos()<gameBean.getNo_of_players()){
                                    MediaStageNew.this_obj.handleBowling(Variables.bowler_bowling);
                                    game_sub_status=Variables.game_sub_status_bowled; 
                                }
                                break;                       
                        }
                    }    
                }
                            
                break;
            case Variables.button_type_pause:
                prev_game_status = game_status;
                game_status = Variables.game_status_paused;
                MediaStageNew.this_obj.handlePause();
                break;
            case Variables.button_type_start:                
                game_ended = false;
                game_status =Variables.game_status_started;
                game_sub_status=Variables.game_sub_status_idle;
                handle_start_button();      
                HandleSerial.handleCom(0xF0+bowler_pos);
                MediaStageNew.player_change=true;
                if(HandleEvents.generalSettings.isCloud_reporting())
                    GameDataHandler.ballbyballUpdate();
                break;
            case Variables.button_type_emg:
                //send signel through USART
                game_mode=0;
                game_status =0;
                HomeScreen1.this_obj.displayOptions();
                MediaStageNew.this_obj.doinitMedia();
                MediaStageNew.this_obj.doMediaLoop();
                break;
            case Variables.button_type_result_runs_straight:
            case Variables.button_type_result_runs_off:
            case Variables.button_type_result_runs_leg:
            case Variables.button_type_result_norun:
            case Variables.button_type_result_wide:
            case Variables.button_type_result_noball:
            case Variables.button_type_result_freehit:
            case Variables.button_type_result_dedball:
            case Variables.button_type_result_bowled:
            case Variables.button_type_result_catch:
            case Variables.button_type_result_lbw:
            case Variables.button_type_result_stumped:
                if(game_sub_status==Variables.game_sub_status_bowled){  
                    handle_score_button(type,subtype);
                }
                break;
            
        }
    }
    //public static MatchResultBean matchresult = new MatchResultBean();
    //static MatchBean matchBean = null;
    static int score =0;
    static int balls =0;
    static int pos=0;
    public static void storeBallDetails(PlayerGameBean playerGameBean,int result){
        OverDataBean overDataBean = null;
        //System.out.println(balls);
        int overs = playerGameBean.getBall_count()/6;
        if(playerGameBean.getBall_count()%6==0 && overs<gameBean.getNo_of_overs_each() && !HandleEvents.wideNoBallScore){            
            if(gameBean.getBowler_selection()==1){
                bowler_pos++;
                if(bowler_pos>8)
                    bowler_pos=1;
                bowler_path=generalSettings.getModeData().getBowler_path()[bowler_pos-1];
                bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1];
                HandleSerial.handleCom(0xF0+bowler_pos);
            }else if(gameBean.getBowler_selection()==2 && overs!=0 && generalSettings.getBowler_sequence1().size()>=overs){
                bowler_pos = generalSettings.getBowler_sequence1().get(overs);
                bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence1().get(overs)-1];
                bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence1().get(overs)-1];
                HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence1().get(overs));
            }else if(gameBean.getBowler_selection()==3&& overs!=0&& generalSettings.getBowler_sequence2().size()>=overs){
                bowler_pos = generalSettings.getBowler_sequence2().get(overs);
                bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence2().get(overs)-1];
                bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence2().get(overs)-1];
                HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence2().get(overs));
            }
        }
        HandleEvents.wideNoBallScore = false;
        if(balls%6==0){            
            overDataBean = new OverDataBean();
            playerGameBean.getOvers().add(overDataBean);
        }else{
            overDataBean = playerGameBean.getOvers().get(balls/6);
        }  
        overDataBean.getBallsInfo().add(new BallInfo(result, machineDataBean.getOperating_mode(), machineDataBean.getSet_speed(), playerGameBean.getSkill_level(), generalSettings.isSkill_test()));
    }
    public static void handle_score_button(int type,int subtype){
        
        pos=gameBean.getSeq_pos();            
        PlayerGameBean playerGameBean = gameBean.getPlayer_data().get(pos);
        score = playerGameBean.getTotal_score();
        balls = playerGameBean.getBall_count();
                
        switch(type){
            case Variables.button_type_result_runs_straight:                
            case Variables.button_type_result_runs_off:                
            case Variables.button_type_result_runs_leg:
                if(subtype==9){
                    type=Variables.button_type_result_catch;
                    playerGameBean.setBall_count(balls+1);
                    playerGameBean .setTotal_score(score-generalSettings.getMatch_negative_scoring());
                    storeBallDetails(playerGameBean,subtype);
                    game_sub_status=Variables.game_sub_status_scored;
                }else{
                    if(subtype==6 && generalSettings.getAuto_scoring_enable()==1){
                        if(HandleEvents.game_skill_level==1){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_2()){
                                subtype=4;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_3()){
                                subtype=3;
                            }else{
                                subtype=0;
                            }
                        }else if(HandleEvents.game_skill_level==2){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_2()){
                                subtype=4;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_3()){
                                subtype=3;
                            }else{
                                subtype=0;
                            }
                        }else if(HandleEvents.game_skill_level==3){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_2()){
                                subtype=4;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_3()){
                                subtype=3;
                            }else{
                                subtype=0;
                            }
                        }else{
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_2()){
                                subtype=4;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_3()){
                                subtype=3;
                            }else{
                                subtype=0;
                            }
                        }
                    }else if(subtype==4 && generalSettings.getAuto_scoring_enable()==1){
                        if(HandleEvents.game_skill_level==1){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_2()){
                                subtype=2;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_1_score_time_3()){
                                subtype=1;
                            }else{
                                subtype=0;
                            }
                        }else if(HandleEvents.game_skill_level==2){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_2()){
                                subtype=2;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_2_score_time_3()){
                                subtype=1;
                            }else{
                                subtype=0;
                            }
                        }else if(HandleEvents.game_skill_level==3){
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_2()){
                                subtype=2;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_3_score_time_3()){
                                subtype=1;
                            }else{
                                subtype=0;
                            }
                        }else{
                            if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_1()){

                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_2()){
                                subtype=2;
                            }else if(AutoScoring.score_time<generalSettings.getAutoScotringBean().getSkill_4_score_time_3()){
                                subtype=1;
                            }else{
                                subtype=0;
                            }
                        }
                    }
                    if(USB_Com.status){
                        //byte cmd[]={0x23,0x12,0x07,3,0,0,0,0,0x21};
                        byte [] cmd = {35,(byte)0x12,0x11,12,0,0,0,0,0,0,0,0,0,0,0,0,0x40,33};
                        if(subtype==2){
                            cmd[5]=(byte)1;
                            cmd[16]=USB_Com.getCRC(cmd, 16);
                            USB_Com.WriteData(cmd);
                        }else if(subtype==4){
                            cmd[7]=(byte)1;
                            cmd[16]=USB_Com.getCRC(cmd, 16);
                            USB_Com.WriteData(cmd);
                        }else if(subtype==6){
                            cmd[9]=(byte)1;
                            cmd[16]=USB_Com.getCRC(cmd, 16);
                            USB_Com.WriteData(cmd);
                        }
                    }                    
                    playerGameBean .setTotal_score(score+subtype);    
                    playerGameBean.setBall_count(balls+1);
                    storeBallDetails(playerGameBean,subtype);
                    game_sub_status=Variables.game_sub_status_scored; 
                }                      
                break;
            case Variables.button_type_result_norun:
                // playerGameBean .setTotal_score(score+subtype);
                playerGameBean.setBall_count(balls+1);
                storeBallDetails(playerGameBean,0);
                game_sub_status=Variables.game_sub_status_scored;       
                break;
            case Variables.button_type_result_wide:
                playerGameBean .setTotal_score(score+1);
                wideNoBallScore = true;
                //playerGameBean.setBall_count(balls+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_wide);
                game_sub_status=Variables.game_sub_status_scored;        
                break;
            case Variables.button_type_result_noball:
                playerGameBean .setTotal_score(score+1);
                //playerGameBean.setBall_count(balls+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_noball);
                game_sub_status=Variables.game_sub_status_scored;   
                break;
            case Variables.button_type_result_freehit:
                wideNoBallScore = true;
                //playerGameBean .setTotal_score(score+1);
                //playerGameBean.setBall_count(balls+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_freehit);
                game_sub_status=Variables.game_sub_status_scored;   
                break;
            case Variables.button_type_result_dedball:
                //playerGameBean .setTotal_score(score+1);
                //playerGameBean.setBall_count(balls+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_dedball);
                game_sub_status=Variables.game_sub_status_scored;   
                break;
            case Variables.button_type_result_bowled:
                //playerGameBean .setTotal_score(score+1);
                playerGameBean.setBall_count(balls+1);
                playerGameBean.setWickets(playerGameBean.getWickets()+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_bowled);
                game_sub_status=Variables.game_sub_status_scored;
                break;
            case Variables.button_type_result_catch:
                 //playerGameBean .setTotal_score(score+1);
                playerGameBean.setBall_count(balls+1);
                playerGameBean.setWickets(playerGameBean.getWickets()+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_bowled);
                game_sub_status=Variables.game_sub_status_scored;
                break;
            case Variables.button_type_result_lbw:
                 //playerGameBean .setTotal_score(score+1);
                playerGameBean.setBall_count(balls+1);
                playerGameBean.setWickets(playerGameBean.getWickets()+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_bowled);
                game_sub_status=Variables.game_sub_status_scored;
                break;
            case Variables.button_type_result_stumped:
                 //playerGameBean .setTotal_score(score+1);
                playerGameBean.setBall_count(balls+1);
                playerGameBean.setWickets(playerGameBean.getWickets()+1);
                storeBallDetails(playerGameBean,Variables.button_type_result_bowled);
                game_sub_status=Variables.game_sub_status_scored;  
                break;
        }
        if(game_sub_status==Variables.game_sub_status_scored){                    
            //TargetScreen.bscoredisplay.setText(matchresult.getCurrent_runs()+" of "+matchresult.getCurrent_balls()+" balls");            
            MediaStageNew.this_obj.handleScore(type, subtype,generalSettings.isReplay_enable());
            game_sub_status=Variables.game_sub_status_idle;
            if(game_mode==Variables.game_mode_mp){
                if(playerGameBean.getBall_count()>=(gameBean.getNo_of_overs_each()*6)){
                    gameBean.setSeq_pos(gameBean.getSeq_pos()+1);
                    MediaStageNew.player_change = true;
                    if(gameBean.getSeq_pos()<gameBean.getNo_of_players()){
                        game_skill_level = playerGameBean.getSkill_level();
                        HandleEvents.machineDataBean.setSet_speed(generalSettings.getDefault_speed());
                        HandleSerial.handleCom(HandleSerial.update_speed);
                        if(gameBean.getBowler_selection()==2 &&  generalSettings.getBowler_sequence1().size()>0){
                            bowler_pos = generalSettings.getBowler_sequence1().get(0);
                            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence1().get(0)-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence1().get(0)-1];
                            HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence1().get(0)-1);
                        }else if(gameBean.getBowler_selection()==3& generalSettings.getBowler_sequence2().size()>0){
                            bowler_pos = generalSettings.getBowler_sequence2().get(0);
                            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence2().get(0)-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence2().get(0)-1];
                            HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence2().get(0));
                        }else{
                            System.out.println("batsman changed");
                            bowler_pos=backup_bowler_pos;
                            bowler_path=generalSettings.getModeData().getBowler_path()[bowler_pos-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1]; 
                            HandleSerial.handleCom(0xF0+bowler_pos);
                        }
                    }else{
                        HandleEvents.machineDataBean.setSet_speed(generalSettings.getDefault_speed());
                        HandleSerial.handleCom(HandleSerial.update_speed);
                        game_ended = true;
                    }
                }
            }else{
                if(playerGameBean.getBall_count()>=(gameBean.getNo_of_overs_each()*6)){
                    HandleEvents.machineDataBean.setSet_speed(generalSettings.getDefault_speed());
                    HandleSerial.handleCom(HandleSerial.update_speed);
                }
            }
        }
        
    }
    public static boolean game_ended = false;
    public static void handle_start_button(){
        switch(game_mode){
            case Variables.game_mode_sp:
                gameBean.setNo_of_players(1);
                gameBean.setType(Variables.game_mode_sp);
                gameBean.setSeq_pos(0);
                gameBean.setBowler_selection(SinglePlayerScreen.bboler_selection.getValue().getKey());
                gameBean.setNo_of_overs_each(SinglePlayerScreen.no_of_overs.getValue());
                if(gameBean.getPlayer_data().size()>0 ){
                    gameBean.getPlayer_data().get(0).setPlayer_id(SinglePlayerScreen.bplayer_id.getText());
                    gameBean.getPlayer_data().get(0).setPlayer_name(SinglePlayerScreen.bplayer_name.getText());
                    gameBean.getPlayer_data().get(0).setSkill_level(SinglePlayerScreen.bplayer_skill.getValue().getKey());
                    gameBean.getPlayer_data().get(0).setHand_usage(SinglePlayerScreen.bplayer_skill.getValue().getType());
                    game_skill_level = gameBean.getPlayer_data().get(0).getSkill_level();
                    if(gameBean.getBowler_selection()==2 &&  generalSettings.getBowler_sequence1().size()>0){
                        bowler_pos = generalSettings.getBowler_sequence1().get(0);
                        bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence1().get(0)-1];
                        bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence1().get(0)-1];
                        //HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence1().get(0)-1);
                    }else if(gameBean.getBowler_selection()==3& generalSettings.getBowler_sequence2().size()>0){
                        bowler_pos = generalSettings.getBowler_sequence2().get(0);
                        bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence2().get(0)-1];
                        bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence2().get(0)-1];
                        //HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence2().get(0));
                    }else{
                        //System.out.println("batsman changed");
                        bowler_pos=backup_bowler_pos;
                        bowler_path=generalSettings.getModeData().getBowler_path()[bowler_pos-1];
                        bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1]; 
                        //HandleSerial.handleCom(0xF0+bowler_pos);
                    }
                    gameBean.getPlayer_data().get(0).setBall_count(0);
                    gameBean.getPlayer_data().get(0).setTotal_score(0);
                    gameBean.getPlayer_data().get(0).setWickets(0);
                    gameBean.getPlayer_data().get(0).getOvers().clear();
                }
                MediaStageNew.this_obj.handlewelcome(TargetScreen.targetBean.getName());
                game_sub_status=Variables.game_sub_status_idle;
                break;
            case Variables.game_mode_mp:
                gameBean.setType(Variables.game_mode_mp);
                gameBean.setSeq_pos(0);
                gameBean.setBowler_selection(MultiPlayerScreen.bboler_selection.getValue().getKey());
                gameBean.setNo_of_overs_each(MultiPlayerScreen.no_of_overs.getValue());
                int no_of_players = gameBean.getNo_of_players();
                for(int i=0;i<no_of_players;i++){
                    gameBean.getPlayer_data().get(i).setPlayer_id(MultiPlayerScreen.bplayer_ids.get(i).getText());
                    gameBean.getPlayer_data().get(i).setPlayer_name(MultiPlayerScreen.bplayer_names.get(i).getText());
                    gameBean.getPlayer_data().get(i).setSkill_level(MultiPlayerScreen.bplayer_skills.get(i).getValue().getKey());
                    gameBean.getPlayer_data().get(i).setHand_usage(MultiPlayerScreen.bplayer_skills.get(i).getValue().getType());
                    if(i==0){
                        game_skill_level = gameBean.getPlayer_data().get(0).getSkill_level();
                        if(gameBean.getBowler_selection()==2 &&  generalSettings.getBowler_sequence1().size()>0){
                            bowler_pos = generalSettings.getBowler_sequence1().get(0);
                            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence1().get(0)-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence1().get(0)-1];
                            //HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence1().get(0)-1);
                        }else if(gameBean.getBowler_selection()==3& generalSettings.getBowler_sequence2().size()>0){
                            bowler_pos = generalSettings.getBowler_sequence2().get(0);
                            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence2().get(0)-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence2().get(0)-1];
                            //HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence2().get(0));
                        }else{
                            //System.out.println("batsman changed");
                            bowler_pos=backup_bowler_pos;
                            bowler_path=generalSettings.getModeData().getBowler_path()[bowler_pos-1];
                            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[bowler_pos-1]; 
                            //HandleSerial.handleCom(0xF0+bowler_pos);
                        }
                    }
                    gameBean.getPlayer_data().get(i).setBall_count(0);
                    gameBean.getPlayer_data().get(i).setTotal_score(0);
                    gameBean.getPlayer_data().get(i).setWickets(0);
                    gameBean.getPlayer_data().get(i).getOvers().clear();
                }
                MediaStageNew.this_obj.handlewelcome(TargetScreen.targetBean.getName());
                game_sub_status=Variables.game_sub_status_idle;
                break;
           
        }
        if(gameBean.getBowler_selection()==2 &&  generalSettings.getBowler_sequence1().size()>0){
            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence1().get(0)-1];
            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence1().get(0)-1];
            HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence1().get(0)-1);
        }else if(gameBean.getBowler_selection()==3& generalSettings.getBowler_sequence2().size()>0){
            bowler_path=generalSettings.getModeData().getBowler_path()[generalSettings.getBowler_sequence2().get(0)-1];
            bowler_trigger = generalSettings.getModeData().getTrigger_interval()[generalSettings.getBowler_sequence2().get(0)-1];
            HandleSerial.handleCom(0xF0+generalSettings.getBowler_sequence2().get(0)-1);
        }
    }
    
}
