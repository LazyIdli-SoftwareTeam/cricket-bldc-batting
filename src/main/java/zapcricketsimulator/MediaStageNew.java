/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zapcricketsimulator;

//import com.sdt.data.MatchDataBean;
import com.sdt.data.GameBean;
import com.sdt.data.MatchResultBean;
import com.sdt.data.PlayerGameBean;
//import com.sdt.displaycomponents.RectButton;
import com.sdt.displaycomponents.TextType1;
import com.sdt.displaycomponents.TextType3_0;
import com.sdt.displaycomponents.TextType5;
import com.sdt.screens.MatchScreen;
import com.sdt.screens.PracticeScreen;
import com.sdt.screens.SixerChallengeScreen;
//import com.sdt.screens.TargetScreen;
import com.sdt.serial.HandleSerial;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableMap;
//import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
//import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
//import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
//import javafx.util.Pair;
import com.sdt.logging.LogManager;
import com.sdt.screens.AutoScoring;
import static com.sdt.screens.AutoScoring.display_result;
import com.sdt.screens.MultiPlayerScreen;
import com.sdt.screens.NextBall;
import com.sdt.screens.PracticeTimer;
import com.sdt.serial.SerialReceive;
import com.sdt.serial.USB_Com;
import com.sdt.serial.USB_ComExt;
import com.sdt.upload_data.GameDataHandler;
import java.util.List;
import static zapcricketsimulator.HandleEvents.game_ended;
import static zapcricketsimulator.HandleEvents.workingDir;

/**
 *
 * @author possi
 */
public class MediaStageNew extends Stage {
    final int device_init=0;
    final int media_init=1;
    final int media_loop=2;
    public static final int welcome_screen = 3;
    public static final int target_screen = 4;
    public static final int reset_screen =5;
    public static final int run_game =6;
    public static final int score_player =7;
    public static final int video_frustration =8;
    public static final int video_celebration =9;
    public static final int bowler_selected =10;
    public static final int match_result =11;
    public static MediaStageNew this_obj=null;
    static MediaView mv = null;
    
    Pane root =null;
    public static boolean projector_status = false;
    public static boolean media_status = false;
    //public static MatchDataBean matchdataBean = new MatchDataBean();
    public MediaStageNew(boolean reinit){
        this.this_obj = this;         
        root = new Pane(); 
        root.setStyle("-fx-background-color: black;");
        //projector_status = triggerEvent(device_init);
        projector_status = doDeviceInit();
        if(projector_status && !reinit){
           media_status=doinitMedia(); 
           if(media_status){
               doMediaLoop();
               screen_status=1;
           }
        }else if(projector_status && reinit){
            media_status=doDeviceReInit(); 
           if(media_status){
               screen_status=1;
           }
        }
        
        
    }
    
    public void triggerEvent(int screen_no){
        
    }
    public static MediaPlayer mp = null;
    static Media m =null;
    public static int screen_status = -1;
   
    public void clearScreen(){
        try {
            /*int index=0;
            int i=0;
            //root.setStyle("-fx-background-color: black;");
            while(i<root.getChildren().size()){
                if(!root.getChildren().get(index).equals(mv)){
                    root.getChildren().remove(index);
                }else{
                    i=1;
                    index++;
                }
            }*/
            root.getChildren().clear();
            root.getChildren().add(mv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static ArrayList<String> video_seq = new ArrayList<String>();
    static File video = null;
    public void handleScore(int type , int sub_type,boolean replay){
        LogManager logger = new LogManager();
        try {            
            clearScreen();

            switch(type){
                case Variables.button_type_result_runs_straight:                    
                    if(sub_type==0) {
                        video_seq.add(HandleEvents.workingDir + "/Media/score/0.mp4");
                        System.out.println("working dir somethis is there" + workingDir);
                        LogManager.logInfo("working dir" + workingDir);
                    } else
                        video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6 && HandleEvents.generalSettings.isPlay_sixer_video())
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_runs_off:
                    if(sub_type==0)
                        video_seq.add(HandleEvents.workingDir+"/Media/score/0.mp4");
                    else
                        video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/2.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6 && HandleEvents.generalSettings.isPlay_sixer_video())
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_runs_leg:
                    if(sub_type==0)
                        video_seq.add(HandleEvents.workingDir+"/Media/score/0.mp4");
                    else
                        video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/3.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6 && HandleEvents.generalSettings.isPlay_sixer_video())
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_norun:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/0.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_wide:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/wide.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_noball:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/noball.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_freehit:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/FreeHit.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_dedball:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/FreeHit.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_bowled:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/bowled/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if( HandleEvents.generalSettings.isPlay_wicket_video())
                        video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_catch:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/catch/high/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if( HandleEvents.generalSettings.isPlay_wicket_video())
                        video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_lbw:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/lbw/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if( HandleEvents.generalSettings.isPlay_wicket_video())
                        video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_stumped:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/stumped/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if( HandleEvents.generalSettings.isPlay_wicket_video())
                        video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
            }            
            video_seq.add(HandleEvents.workingDir+ "/Media/match/TargetScreen.mp4");
            if(video_seq.size()>0){
                video = new File(video_seq.remove(0));
                loadVideo(video);
                doMediaSeq();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }
    
    public void handleScore_match(int type , int sub_type,boolean replay){ 
        try {
            clearScreen();
            switch(type){
                case Variables.button_type_result_runs_straight:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6)
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_runs_off:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/2.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6)
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_runs_leg:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/"+sub_type+"/3.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    if(sub_type==6)
                        video_seq.add(HandleEvents.bowler_path+ "/frustrate/1.mp4");
                    break;
                case Variables.button_type_result_norun:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/0.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_wide:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/wide.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_noball:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/noball.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_freehit:
                    video_seq.add(HandleEvents.workingDir+"/Media/score/noballheight.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    break;
                case Variables.button_type_result_bowled:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/bowled/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_catch:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/catch/high/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_lbw:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/lbw/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
                case Variables.button_type_result_stumped:
                    video_seq.add(HandleEvents.workingDir+"/Media/dismissal/stumped/1.mp4");
                    if(replay){
                        video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
                    }
                    video_seq.add(HandleEvents.bowler_path+ "/celebrate/1.mp4");
                    break;
            }
            if(MatchScreen.matchBean.isGame_over()){ 
                if(MatchScreen.matchBean.getTeama_score()==MatchScreen.matchBean.getTeamb_score())
                    video_seq.add(HandleEvents.workingDir+ "/Media/result/wellplayed.mp4");
                else
                    video_seq.add(HandleEvents.workingDir+ "/Media/result/won.mp4");   
                switch(HandleEvents.game_mode){
                    case Variables.game_mode_target:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/TargetMode.mp4");               
                    }break;
                    case Variables.game_mode_practice:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/PracticeMode.mp4"); 
                    }break;
                    case Variables.game_mode_match:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/MatchMode.mp4");                
                    }break;
                    case Variables.game_mode_sixer:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/SixerChallenge.mp4");               
                    }break;
                }    
            }else{
                video_seq.add(HandleEvents.workingDir+ "/Media/match/TargetScreen.mp4");
            }
            if(video_seq.size()>0){
                video = new File(video_seq.remove(0));
                loadVideo(video);
                doMediaSeq();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            
    }
    static int random_six = 0;
    public void handleSixerScore(int type){
        try {
            clearScreen();
            video_seq.clear();            
            if(type==0){
                video_seq.add(HandleEvents.workingDir+"/Media/score/0.mp4");
            }else{
                if(random_six==0 ){
                    video_seq.add(HandleEvents.workingDir+"/Media/score/6/1.mp4");
                    random_six=1;
                }else if(random_six==1){
                    video_seq.add(HandleEvents.workingDir+"/Media/score/6/2.mp4");
                    random_six=2;
                }else if(random_six==2){
                    video_seq.add(HandleEvents.workingDir+"/Media/score/6/3.mp4");
                    random_six=0;
                }else{
                    video_seq.add(HandleEvents.workingDir+"/Media/score/6/1.mp4");
                    random_six=0;
                }
                
            }
            if(HandleEvents.generalSettings.isReplay_enable()){
                video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
            }
            if(SixerChallengeScreen.sixerchallengeBean.getCurrent_balls()>=SixerChallengeScreen.sixerchallengeBean.getBalls()){
                if(SixerChallengeScreen.sixerchallengeBean.getCurrent_sixers()>=SixerChallengeScreen.sixerchallengeBean.getSixers()){
                    video_seq.add(HandleEvents.workingDir+ "/Media/result/won.mp4");
                }else{
                    video_seq.add(HandleEvents.workingDir+ "/Media/result/wellplayed.mp4");
                }
                switch(HandleEvents.game_mode){
                    case Variables.game_mode_target:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/TargetMode.mp4");               
                    }break;
                    case Variables.game_mode_practice:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/PracticeMode.mp4"); 
                    }break;
                    case Variables.game_mode_match:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/MatchMode.mp4");                
                    }break;
                    case Variables.game_mode_sixer:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/SixerChallenge.mp4");               
                    }break;
                }    
                HandleEvents.game_status=Variables.game_status_finalized;
            }else if(SixerChallengeScreen.sixerchallengeBean.getCurrent_sixers()>=SixerChallengeScreen.sixerchallengeBean.getSixers()){
                video_seq.add(HandleEvents.workingDir+ "/Media/result/won.mp4");
                switch(HandleEvents.game_mode){
                    case Variables.game_mode_target:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/TargetMode.mp4");               
                    }break;
                    case Variables.game_mode_practice:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/PracticeMode.mp4"); 
                    }break;
                    case Variables.game_mode_match:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/MatchMode.mp4");                
                    }break;
                    case Variables.game_mode_sixer:{
                        video_seq.add(HandleEvents.workingDir+ "/Media/source/SixerChallenge.mp4");               
                    }break;
                }    
                HandleEvents.game_status=Variables.game_status_finalized;
            }else{
                video_seq.add(HandleEvents.workingDir+ "/Media/match/TargetScreen.mp4");
            }
            video = new File(video_seq.remove(0));
            loadVideo(video);
            doMediaSeq();
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    public static long ms1=0,ms2=0,ms3=0;
    public static boolean enable_bowl = false;
    static String videoMRL ="";
    static int recordTime = 0;
    static String video_format = "";
    static String videoPath ="";
    static String command ="";
    static Process p =null;
    public void doreplay(double time ){
         try {
            //System.out.println("inside event");
            if(HandleEvents.generalSettings.isReplay_enable()){
                //System.out.println("start recording");
                String videoMRL = "rtsp://"+HandleEvents.generalSettings.getCamera_user_id()+":"+HandleEvents.generalSettings.getCamera_password()+"@"+HandleEvents.generalSettings.getCamera_ip()+":"+HandleEvents.generalSettings.getCamera_port()+"//Streaming/Channels/1";
                int recordTime = HandleEvents.generalSettings.getReplayduration_ms()/1000;
                String video_format = HandleEvents.generalSettings.getFormat();
                if(video_format==null || video_format.equals(""))
                    video_format = "copy";
                String videoPath = HandleEvents.workingDir + "/replay.mp4";
                String command = "C:/ffmpeg/bin/ffmpeg -y -i " + "\"" + videoMRL + "\"" +" -t " + recordTime + " -s hd480 -acodec copy -vcodec "+video_format+ " \"" + videoPath + "\"";
                Process p = Runtime.getRuntime().exec(command);                    
            }
            //double time = mp.getCurrentTime().toMillis();
            /*Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Rectangle rect = new Rectangle(screenwidth*0.35, screenheight*0.8, screenwidth*0.30, screenheight*0.1);
                    rect.setFill(Color.BLACK);
                    root.getChildren().add(rect);
                    Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
                    String string1 = "Ball Release "+time;
                    TextType3_0 text1 = new TextType3_0(screenwidth/2, screenheight*0.85, string1, f_type1, root,Color.WHITE);
                }
            });*/
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    int prev_game_mode=0,prev_game_status=0;
    public static int error_status=0;
    public void handleErrorScreen(int type){
        //System.out.println("handling error screen");
        error_status = type;
        prev_game_mode = HandleEvents.game_mode;
        prev_game_status = HandleEvents.game_status;
        video = new File(HandleEvents.workingDir, "/Media/match/ErrorScreen.mp4");
        clearScreen();
        loadVideo(video);
        video_seq.clear();   
        doMediaSeq();
        try {           
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
            if(type==Variables.button_type_ball_init){
                string1 = "System Initialization Ball Release Test Stand Aside";
                text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
            }else if(type==Variables.button_type_ball_error){
                string1 = "Please Check If Ball Loaded";
                text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
            }      
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    static String path = "";
    static String text_message = "";
    static ObservableMap<String, Duration> markers =null;
    public static boolean player_change = false;
    public static boolean init_player = false;
    public boolean doMediaSeq(){
        try {
            ms1 = Calendar.getInstance().getTimeInMillis();
            mp.play();
            mp.setOnError(new Runnable() {
                @Override
                public void run() {                    
                    try {
                        //System.out.println(Calendar.getInstance().getTime());
                        System.out.println("error playing "+mp.getError().getMessage());
                        LogManager.logError("error playing "+mp.getError().getMessage());
                        if(current_video!=null){                            
                            loadVideo(current_video);                            
                            doMediaSeq();
                            if(current_video.getName().contains("TargetScreen.mp4")){
                                loadTargetText();
                            } 
                            if(enable_bowl){
                                enable_bowl=false;
                            }
                        }                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                    
                }                    
            });
            if(current_video.getPath().contains("bowling")){                
                markers = m.getMarkers();
                markers.put("INTERVAL", Duration.millis(NextBall.ballBean.getBall_release()));
                mp.setOnMarker(new EventHandler<MediaMarkerEvent>() 
                {
                    public void handle(MediaMarkerEvent event) 
                    {
                        ms2 = Calendar.getInstance().getTimeInMillis();
                        try {
                            if(HandleEvents.generalSettings.isReplay_enable()){
                                String videoMRL = "rtsp://"+HandleEvents.generalSettings.getCamera_user_id()+":"+HandleEvents.generalSettings.getCamera_password()+"@"+HandleEvents.generalSettings.getCamera_ip()+":"+HandleEvents.generalSettings.getCamera_port()+"//Streaming/Channels/1";
                                int recordTime = HandleEvents.generalSettings.getReplayduration_ms()/1000;
                                String video_format = HandleEvents.generalSettings.getFormat();
                                if(video_format==null || video_format.equals(""))
                                    video_format = "copy";
                                String videoPath = HandleEvents.workingDir + "/replay.mp4";
                                String command = "C:/ffmpeg/bin/ffmpeg -y -i " + "\"" + videoMRL + "\"" +" -t " + recordTime + " -s hd480 -acodec copy -vcodec "+video_format+ " \"" + videoPath + "\"";
                                Process p = Runtime.getRuntime().exec(command);                    
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        HandleSerial.handleCom(HandleSerial.ball_release);  
                    }
                });
            }
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    try {
                        /*if(current_video.getPath().contains("bowling")){
                             Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    NextBall.planNextBall();
                                }
                             });
                        }*/
                        if(video_seq.size()!=0){ 
                            path = video_seq.remove(0);
                            video = new File(path);
                            if(video.getName().contains("replay.mp4")){
                                boolean ready = false;
                                if(video.exists()&&video.isFile()){
                                    if(video.length()>HandleEvents.generalSettings.getMin_file_size()){
                                        ready=true;
                                    }
                                }
                                if(!ready){
                                    Thread.sleep(HandleEvents.generalSettings.getMax_replay_delay());
                                    if(video.exists()&&video.isFile()){
                                        if(video.length()>HandleEvents.generalSettings.getMin_file_size()){
                                            ready=true;
                                        }else{
                                            LogManager.logError("Replay Video File Size "+video.length());
                                        }
                                    }
                                    if(!ready){
                                        if(video_seq.size()!=0){
                                            path = video_seq.remove(0);
                                            video = new File(path);
                                        }else{                                            
                                            return;
                                        }
                                    }
                                    
                                }
                                    
                            }
                            if(path.contains("TargetMode.mp4")||path.contains("PracticeMode.mp4")||path.contains("MatchMode.mp4")||path.contains("SixerChallenge.mp4")){
                                clearScreen();
                            }
                            loadVideo(video);
                            doMediaSeq();                            
                            if(path.contains("TargetScreen.mp4")){
                                //NextBall.planNextBall();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
//                                        if(game_ended)
//                                            HandleEvents.gameBean.setSeq_pos(0);
                                        NextBall.planNextBall();
                                    }
                                 });
                                loadTargetText();
//                                if(game_ended)

                                if(USB_Com.status){
                                    byte [] cmd = {35,(byte)0x12,0x11,12,0,0,0,0,0,0,0,0,0,0,0,0,0x40,33};
                                    cmd[16]=USB_Com.getCRC(cmd, 16);
                                    USB_Com.WriteData(cmd);
                                    //byte cmd[] = {0x23,0x12,0x07,0x03,0,0,0,0,0x21};
                                    //cmd[7]=USB_Com.getCRC(cmd, 7);
                                    //USB_Com.WriteData(cmd);
                                }
                            }                            
                        }else{
                           if(current_video.getPath().contains("bowling") && HandleEvents.game_sub_status==Variables.game_sub_status_bowled){                                
                                f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
                                if( HandleEvents.generalSettings.getAuto_scoring_enable()==1){
                                    rect = new Rectangle(screenwidth*0.35, screenheight*0.8, screenwidth*0.30, screenheight*0.1);
                                    rect.setFill(Color.BLACK);
                                    root.getChildren().add(rect);
                                    string1 = "Predicting Score";
                                    SerialReceive.pridicting_score=true;
                                    text1 = new TextType3_0(screenwidth/2, screenheight*0.85, string1, f_type1, root,Color.WHITE);
                                }else if( HandleEvents.generalSettings.getAuto_scoring_enable()==0){
                                    string1 = ""; //"Waiting For Score";
                                    HandleEvents.handleEvent(Variables.button_type_result_norun, 0);
                                }else{
                                    string1 = "";
                                }                                
                           }else if(current_video.getPath().contains("TargetScreen") ){
                               if((HandleEvents.game_mode ==Variables.game_mode_mp&&HandleEvents.gameBean.getSeq_pos()<HandleEvents.gameBean.getNo_of_players()) || (HandleEvents.game_mode ==Variables.game_mode_sp && HandleEvents.gameBean.getPlayer_data().get(0).getBall_count()<(HandleEvents.gameBean.getNo_of_overs_each()*6))){
                                   if(!player_change){ 
                                        //
                                        SerialReceive.check_ball=true;
                                        HandleEvents.machineDataBean.setRead_status(0);
                                        //HandleEvents.handleEvent(Variables.button_type_play, 0);
                                                                               
                                   }else{
                                       //player_change = false;
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
                                        HandleEvents.machineDataBean.setSet_speed(speed);
                                        HandleSerial.handleCom(HandleSerial.update_speed);
                                        HandleEvents.handleEvent(Variables.button_type_play, 0);
                                   }
                               }else{
                                   //report data
                                   //System.out.println("Game Completed");
                                   if(HandleEvents.generalSettings.isCloud_reporting())
                                        GameDataHandler.pushData(HandleEvents.gameBean);
                                   Thread.sleep(15000);
                                   handlemode(HandleEvents.game_mode);
                               }
                           }else if(current_video.getPath().contains("ErrorScreen")){                               
                               if(error_status==Variables.button_type_ball_init){
                                   clearScreen();
                                   if(prev_game_mode==0){
                                       try {
                                           Thread.sleep(5000);
                                       } catch (Exception e) {
                                       }
                                       error_status=0;
                                       HandleSerial.handleCom(HandleSerial.ball_release);
                                       HandleEvents.machineDataBean.setRead_status(0);
                                       doinitMedia();
                                       doMediaLoop();
                                   }
                               }else if(error_status==Variables.button_type_ball_error){
                                
                               }
                           }else if(current_video.getPath().contains("WelcomePlayer")&&init_player){
                               init_player=false;
                               /*try {
                                   Thread.sleep(5000);
                               } catch (Exception e) {
                               }
                               HandleEvents.handleEvent(Variables.button_type_play, 0);*/
                               
                           }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                   
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.logError("Sequencer error "+e.getMessage());
            return false;            
        }
        return true;
    }
    
    public void initPlayerSettings(){
        video = new File(HandleEvents.workingDir, "/Media/match/WelcomePlayer.mp4");
        clearScreen();
        init_player=true;
        loadVideo(video);
        video_seq.clear();   
        doMediaSeq();
        try {       
            displayTextloop();
            /*f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
            string1 = "Initializing New Player Settings";
            text1 = new TextType3_0(screenwidth*0.5, screenheight*0.5, string1, f_type1, root,Color.WHITE); */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    int display_count = HandleEvents.generalSettings.getPlayer_init_time();
    public void displayTextloop(){
        if(root.getChildren().contains(text_5)){
            root.getChildren().remove(text_5);
            root.getChildren().remove(text1);
            root.getChildren().remove(text2);
        } 
         f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
        if(HandleEvents.game_mode==Variables.game_mode_sp){
            string1 = "Welcome "+HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getPlayer_name();
            text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
        }else if(HandleEvents.game_mode==Variables.game_mode_mp){
            string1 = "Welcome "+HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getPlayer_name();;
            text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
        } 
        text2 = new TextType3_0(screenwidth/2, screenheight*0.55, "Experience SD Cricket Simulator", f_type1, root,Color.WHITE);
        text_5 = new TextType5(screenwidth*0.04, screenheight*0.07,/* "Your Game Starts in "+*/display_count-- +""/*+" , "+ HandleEvents.machineDataBean.getBall_status()*/);
        text_5.setLayoutX(screenwidth*0.5);
        text_5.setLayoutY(screenheight*0.37);
        root.getChildren().add(text_5);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if(display_count>0){
                        displayTextloop();
                    }else{
                        display_count=HandleEvents.generalSettings.getPlayer_init_time();
                        //MediaStageNew.player_change=false;
                        HandleEvents.game_status = Variables.game_status_started;
                        HandleEvents.handleEvent(Variables.button_type_play, 0);
                    }
                } catch (Exception e) {
                }
            }
        });  
    }
    
    
    static String string1 ="";
    static Rectangle rect = null;
    static Font f_type1 = null;
    static TextType3_0 text1 =null;
    static TextType3_0 text2 =null;
    
    
    
    public void handlewelcome(String Name){
        try {
            clearScreen();
            video = new File(HandleEvents.workingDir, "/Media/match/WelcomePlayer.mp4");
            loadVideo(video);
            video_seq.clear();   
            doMediaSeq();
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
            if(HandleEvents.game_mode==Variables.game_mode_sp){
                string1 = "Welcome "+HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getPlayer_name();
                text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
            }else if(HandleEvents.game_mode==Variables.game_mode_mp){
                string1 = "Welcome "+HandleEvents.gameBean.getPlayer_data().get(HandleEvents.gameBean.getSeq_pos()).getPlayer_name();;
                text1 = new TextType3_0(screenwidth/2, screenheight*0.47, string1, f_type1, root,Color.WHITE);
            }             
            text2 = new TextType3_0(screenwidth/2, screenheight*0.55, "Experience SD Cricket Simulator", f_type1, root,Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    static String match = "";
    static String game = "";
    static String no_of_players ="";
    static Rectangle teama_head =null;
    static TextType1 teama_name = null;
    static Rectangle playera_rect =null;
    static TextType1 playera_name = null;
    static Rectangle teama_tail = null ;
    static TextType1 teama_overs =null;
    public void handlewelcome_match(){
        clearScreen();
        try {
            video = new File(HandleEvents.workingDir, "/Media/match/WelcomePlayer.mp4");
            loadVideo(video);
            video_seq.clear();   
            doMediaSeq();

            rect = new Rectangle(screenwidth*0.1, screenheight*0.195, screenwidth*0.8, screenheight*0.05);
            rect.setFill(Color.BLACK);
            root.getChildren().add(rect);
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
            match = MatchScreen.matchBean.getTeama_name()+" vs "+MatchScreen.matchBean.getTeamb_name();
            text1 = new TextType3_0(screenwidth/2, screenheight*0.22, match, f_type1, root,Color.WHITE);
            
            if(MatchScreen.matchBean.getGame_type()==Variables.match_type_one_over_each){
                game="Game Type : One Over Each";
            }else if(MatchScreen.matchBean.getGame_type()==Variables.match_type_two_over_each){
                game="Game Type : Two Over Each";
            }else{
                game="Game Type : Default";
            }
            //TextType3_0 text2 = new TextType3_0(screenwidth*0.2, screenheight*0.22, game, f_type1, root,Color.WHITE);
            no_of_players = "Players : "+MatchScreen.matchBean.getNo_of_players();
            //TextType3_0 text3 = new TextType3_0(screenwidth*0.8, screenheight*0.22, no_of_players, f_type1, root,Color.WHITE);

            teama_head = new Rectangle(screenwidth*0.1, screenheight*0.251, screenwidth*0.39, screenheight*0.08);
            teama_head.setFill(Color.rgb(253, 2, 61));
            root.getChildren().add(teama_head);

            teama_name = new TextType1(screenwidth*0.12, screenheight*0.31, MatchScreen.matchBean.getTeama_name(), f_type1, root, Color.BLACK);       

            /*teamb_head = new Rectangle(screenwidth*0.51, screenheight*0.251, screenwidth*0.39, screenheight*0.08);
            teamb_head.setFill(Color.rgb(253, 2, 61));
            root.getChildren().add(teamb_head);

            teamb_name = new TextType1(screenwidth*0.53, screenheight*0.31, MatchScreen.matchBean.getTeamb_name(), f_type1, root, Color.BLACK);*/

            for(int i=0;i<MatchScreen.matchBean.getNo_of_players();i++){
                playera_rect = new Rectangle(screenwidth*0.1, screenheight*(0.332+(i*0.081)), screenwidth*0.39, screenheight*0.08);
                if(i%2==0)
                    playera_rect.setFill(Color.WHITE);
                else
                    playera_rect.setFill(Color.rgb(255, 215, 227));
                root.getChildren().add(playera_rect);
                String namea = "";
                if(MatchScreen.matchBean.getTeamA_player_names()!=null && MatchScreen.matchBean.getTeamA_player_names().size()>i)
                    namea=MatchScreen.matchBean.getTeamA_player_names().get(i);
                else
                    namea="Player "+(i+1);
                playera_name = new TextType1(screenwidth*0.12, screenheight*(0.332+(i*0.081)+0.06), namea, f_type1, root, Color.BLACK);

                
            }
            int players = MatchScreen.matchBean.getNo_of_players();
            teama_tail = new Rectangle(screenwidth*0.1, screenheight*(0.332+(players*0.081)), screenwidth*0.39, screenheight*0.08);
            teama_tail.setFill(Color.rgb(253, 2, 61));
            root.getChildren().add(teama_tail);

            teama_overs = new TextType1(screenwidth*0.12, screenheight*(0.332+(players*0.081)+0.06), "Remaining Overs : "+MatchScreen.matchBean.getNo_of_overs(), f_type1, root, Color.BLACK);       

            /*teamb_tail = new Rectangle(screenwidth*0.51, screenheight*(0.332+(players*0.081)), screenwidth*0.39, screenheight*0.08);
            teamb_tail.setFill(Color.rgb(253, 2, 61));
            root.getChildren().add(teamb_tail);

            teamb_overs = new TextType1(screenwidth*0.53, screenheight*(0.332+(players*0.081)+0.06), "Remaining Overs : "+MatchScreen.matchBean.getNo_of_overs(), f_type1, root, Color.BLACK);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void handlemode(int mode){
        clearScreen();
        try {
            switch(mode){
                case Variables.game_mode_sp:{
                    video = new File(HandleEvents.workingDir, "/Media/source/TargetMode.mp4");
                    loadVideo(video);
                    video_seq.clear(); 
                    doMediaSeq();
                }
                break;
                case Variables.game_mode_mp:{
                    video = new File(HandleEvents.workingDir, "/Media/source/MatchMode.mp4");
                    loadVideo(video);
                    video_seq.clear(); 
                    doMediaSeq();
                }
                break;     
                default://"/Media/match/home/1.mp4"
                    video = new File(HandleEvents.workingDir, "/Media/match/home/1.mp4");
                    loadVideo(video);
                    video_seq.clear(); 
                    doMediaSeq();
                    break;
            }      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handlePlay(){
        mp.play();
    }
    public void handlePause(){
        mp.pause();
    }
    public void handleBowling(int type){
        clearScreen();
        try {
            switch(type){
                case Variables.bowler_intro:{
                    video = new File(NextBall.ballBean.getBowler_path(), "/intro/1.mp4");
                    loadVideo(video);
                    doMediaLoop();                
                }break;
                case Variables.bowler_bowling:{
                    video = new File(NextBall.ballBean.getBowler_path(), "/bowling/1.mp4");
                    loadVideo(video);
                    video_seq.clear(); 
                    doMediaSeq();
                    //sendBallTrigger(HandleEvents.bowler_trigger);
                }break;
                case Variables.bowler_frustrate:{
                    video = new File(NextBall.ballBean.getBowler_path(), "/frustrate/1.mp4");
                    loadVideo(video);                
                }break;
                case Variables.bowler_celebrate:{
                    video = new File(NextBall.ballBean.getBowler_path(), "/celebrate/1.mp4");
                    loadVideo(video);                
                }break;
            }      
        } catch (Exception e) {
            e.printStackTrace();
        }          
    }
    public static TextType3_0 reftesh_text = null;
    public static Rectangle refresh_rect =null;
    public static TextType3_0 refresh_name=null;
    public void handlePracticeBowling(){
        try {
            clearScreen();            
            video = new File(HandleEvents.bowler_path, "/bowling/1.mp4");
            loadVideo(video);           
            video_seq.clear();
            if(HandleEvents.generalSettings.isReplay_enable()){
                video_seq.add(HandleEvents.workingDir+ "/replay.mp4");
            }
            video_seq.add(HandleEvents.workingDir+ "/Media/match/TargetScreen.mp4");            
            doMediaSeq();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
 
    public void loadWiningText(String Text){
        try {
            rect = new Rectangle(0, screenheight*0.8, screenwidth, screenheight*0.1);
            rect.setFill(Color.RED);
            //root.getChildren().add(rect);
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
            text1 = new TextType3_0(screenwidth/2, screenheight*0.85, Text, f_type1, root,Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static String string2="";
    static TextType1 playera_score =null;
    static String namea = "";
    TextType5 text_5 = null;
    int change_time = 5;
    public void loadTargetText2(){
        if(HandleEvents.generalSettings.isCloud_reporting())
                GameDataHandler.ballbyballUpdate();
        try {
            if(!player_change ){
                text_5 = new TextType5(screenwidth*0.235, screenheight*0.05, "Get Ready For The Next Ball");
                text_5.setLayoutX(screenwidth*0.375);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);

                /*text_5 = new TextType5(screenwidth*0.4, screenheight*0.05, AutoScoring.display_string);
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.5);
                root.getChildren().add(text_5);*/
            }else if(!game_ended){
                if(root.getChildren().contains(text_5)){
                    root.getChildren().remove(text_5);
                }                
                text_5 = new TextType5(screenwidth*0.4, screenheight*0.05, "Next Player Get Ready Game is about to Starts");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            HandleEvents.handleEvent(Variables.button_type_play, 0);
                            /*if(change_time>0){
                                change_time--;
                                loadTargetText();
                            }else{
                                change_time=5;
                                HandleEvents.handleEvent(Variables.button_type_play, 0);
                            }*/
                        } catch (Exception e) {
                        }
                    }
                });  
            }
            
                       
        } catch (Exception e) {
        }
    }
    public boolean loadTargetText(){
         if(HandleEvents.generalSettings.isCloud_reporting())
                GameDataHandler.ballbyballUpdate();
         
        try {            
            if(HandleEvents.game_mode==Variables.game_mode_sp){                
                text_5 = new TextType5(screenwidth*0.15, screenheight*0.05, "Player");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.15, screenheight*0.05, "Level");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.43);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.15, screenheight*0.05, "Balls");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.49);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.15, screenheight*0.05, "Runs");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.55);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.15, screenheight*0.05, "Strike Rate");
                text_5.setLayoutX(screenwidth*0.3);
                text_5.setLayoutY(screenheight*0.61);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.2,screenheight*0.05,HandleEvents.gameBean.getPlayer_data().get(0).getPlayer_name(),Color.WHITE,Color.rgb(39, 62, 68));
                text_5.setLayoutX(screenwidth*0.452);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.2,screenheight*0.05,MultiPlayerScreen.skill_levels.get((HandleEvents.gameBean.getPlayer_data().get(0).getSkill_level()-1)*2).getValue(),Color.WHITE,Color.rgb(39, 62, 68));
                text_5.setLayoutX(screenwidth*0.452);
                text_5.setLayoutY(screenheight*0.43);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.2,screenheight*0.05,HandleEvents.gameBean.getPlayer_data().get(0).getBall_count()+"/"+(HandleEvents.gameBean.getNo_of_overs_each()*6),Color.WHITE,Color.rgb(39, 62, 68));
                text_5.setLayoutX(screenwidth*0.452);
                text_5.setLayoutY(screenheight*0.49);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.2,screenheight*0.05,HandleEvents.gameBean.getPlayer_data().get(0).getTotal_score()+"",Color.WHITE,Color.rgb(39, 62, 68));
                text_5.setLayoutX(screenwidth*0.452);
                text_5.setLayoutY(screenheight*0.55);
                root.getChildren().add(text_5);
                
                int strikerate = 0;
                if(HandleEvents.gameBean.getPlayer_data().get(0).getBall_count()!=0 && HandleEvents.gameBean.getPlayer_data().get(0).getTotal_score()!=0)
                    strikerate = (int)((((float)HandleEvents.gameBean.getPlayer_data().get(0).getTotal_score())*100)/(float)HandleEvents.gameBean.getPlayer_data().get(0).getBall_count());
                
                text_5 = new TextType5(screenwidth*0.2,screenheight*0.05,strikerate+"",Color.WHITE,Color.rgb(39, 62, 68));
                text_5.setLayoutX(screenwidth*0.452);
                text_5.setLayoutY(screenheight*0.61);
                root.getChildren().add(text_5);               
            }else if(HandleEvents.game_mode==Variables.game_mode_mp){
                GameBean gameBean = HandleEvents.gameBean;
                
                text_5 = new TextType5(screenwidth*0.14, screenheight*0.05, "Player");
                text_5.setLayoutX(screenwidth*0.2);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.12, screenheight*0.05, "Level");
                text_5.setLayoutX(screenwidth*0.345);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.1, screenheight*0.05, "Balls");
                text_5.setLayoutX(screenwidth*0.47);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.1, screenheight*0.05, "Runs");
                text_5.setLayoutX(screenwidth*0.575);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                text_5 = new TextType5(screenwidth*0.1, screenheight*0.05, "Strike Rate");
                text_5.setLayoutX(screenwidth*0.68);
                text_5.setLayoutY(screenheight*0.37);
                root.getChildren().add(text_5);
                
                int noOfPlayers = gameBean.getNo_of_players();
                int[] player_scores = new int[noOfPlayers];
                int maxScore = 0;
                ArrayList<Integer> winners = new ArrayList<>();
                
                for (int j = 0; j < noOfPlayers; j++) {
                    PlayerGameBean wplayergamebean = gameBean.getPlayer_data().get(j);
                    player_scores[j] = wplayergamebean.getTotal_score();
                    if (player_scores[j] > maxScore) {
                        maxScore = player_scores[j];
                    }
                }
                
                for (int w = 0; w < player_scores.length; w++) {
                    if (player_scores[w] == maxScore) {
                        winners.add(w);
                    }
                }
                
                for(int i=0;i<gameBean.getNo_of_players();i++){
                    PlayerGameBean playergamebean = gameBean.getPlayer_data().get(i);
                    String name= playergamebean.getPlayer_name();
                    Color bgcolor = Color.rgb(39, 62, 68);
                    if(i == gameBean.getSeq_pos()){
                        bgcolor = Color.rgb(255, 22, 22);
                        name=name+"*";
                    }
                    
                    if(game_ended){
                        text_5 = new TextType5(screenwidth*0.14, screenheight*0.05, "Top Scorers");
                        text_5.setLayoutX(screenwidth*0.2);
                        text_5.setLayoutY(screenheight*0.31);
                        root.getChildren().add(text_5);
                        
                        if (winners.contains(i)) {
                            bgcolor = Color.rgb(0, 100, 0);
                        }else{
                            bgcolor = Color.rgb(39, 62, 68);
                        }                        

                    }
                        /*
                        text_5 = new TextType5(screenwidth*0.1, screenheight*0.05, "Top Scorers");
                        text_5.setLayoutX(screenwidth*0.2);
                        text_5.setLayoutY(screenheight*0.31);
                        root.getChildren().add(text_5);
                        int max=0;
                        for(int j=0;j<gameBean.getNo_of_players();j++){
                            PlayerGameBean wplayergamebean = gameBean.getPlayer_data().get(j);
                            
                            int[] player_scores = new int[gameBean.getNo_of_players()];
                            player_scores[j]=wplayergamebean.getTotal_score();

                            
                            for(int score : player_scores){
                                if(score > max)
                                    max = score;
                            }
                            System.out.println(max+" is the max value");
                            System.out.println(player_scores.length+ " lenght of paluyer scores");
                            ArrayList<Integer> winners = new ArrayList<>();
                            for (int w=0;w<player_scores.length;w++){
                                if(player_scores[w] == max){
                                    winners.add(w);
                                    System.out.println(max+" "+w+" "+player_scores[w]+" "+winners.toString());
                                }
                            }
                            System.out.println(winners.toString()+" "+winners.size());
                            

//                            if(winners.get(j)==j){
    //                            text_5 = new TextType5(screenwidth*0.14,screenheight*0.05,gameBean.getPlayer_data().get(win).getPlayer_name(),Color.WHITE,bgcolor);
    //                            text_5.setLayoutX(screenwidth*0.2);
    //                            text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
    //                            root.getChildren().add(text_5);
    //                            playergamebean = gameBean.getPlayer_data().get(win);
//                                bgcolor = Color.rgb(0,100,0);

                            if(winners.size()>1){
                                for (int winner : winners){
                                    if(i==winner)
                                        bgcolor = Color.rgb(0,100,0);
                                    else{
                                        bgcolor = Color.rgb(39, 62, 68);
                                    }
                                }
                            }else{
                                if(i==winners.get(0))
                                    bgcolor = Color.rgb(0,100,0);
                                else
                                    bgcolor = Color.rgb(39, 62, 68);
                            }
                        }*/
//                        handlePause();
//                        Thread.sleep(2500);
//                        handlePlay();
//                    }
                    
                    text_5 = new TextType5(screenwidth*0.14,screenheight*0.05,name,Color.WHITE,bgcolor);
                    text_5.setLayoutX(screenwidth*0.2);
                    text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
                    root.getChildren().add(text_5);
                    
                    text_5 = new TextType5(screenwidth*0.12,screenheight*0.05,MultiPlayerScreen.skill_levels.get((playergamebean.getSkill_level()-1)*2).getValue(),Color.WHITE,bgcolor);
                    text_5.setLayoutX(screenwidth*0.345);
                    text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
                    root.getChildren().add(text_5);
                    
                    text_5 = new TextType5(screenwidth*0.1,screenheight*0.05,playergamebean.getBall_count()+"/"+(HandleEvents.gameBean.getNo_of_overs_each()*6),Color.WHITE,bgcolor);
                    text_5.setLayoutX(screenwidth*0.47);
                    text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
                    root.getChildren().add(text_5);
                    
                    text_5 = new TextType5(screenwidth*0.1,screenheight*0.05,playergamebean.getTotal_score()+"",Color.WHITE,bgcolor);
                    text_5.setLayoutX(screenwidth*0.575);
                    text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
                    root.getChildren().add(text_5);
                    
                    int strikerate = 0;
                    if(playergamebean.getBall_count()!=0 && playergamebean.getTotal_score()!=0)
                        strikerate = (int)((((float)playergamebean.getTotal_score())*100)/(float)playergamebean.getBall_count());
                    
                    text_5 = new TextType5(screenwidth*0.1,screenheight*0.05,strikerate+"",Color.WHITE,bgcolor);
                    text_5.setLayoutX(screenwidth*0.68);
                    text_5.setLayoutY(screenheight*(0.43+(i*0.06)));
                    root.getChildren().add(text_5);
                }
                
                /*rect = new Rectangle(screenwidth*0.2, screenheight*0.195, screenwidth*0.6, screenheight*0.05);
                rect.setFill(Color.BLACK);
                root.getChildren().add(rect);
                f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,screenheight*0.05);
                match = "Game Stats";
                text1 = new TextType3_0(screenwidth/2, screenheight*0.22, match, f_type1, root,Color.WHITE);
                game = " No of Overs " + gameBean.getNo_of_overs_each();
                no_of_players = "Players : "+gameBean.getNo_of_players();

                teama_head = new Rectangle(screenwidth*0.2, screenheight*0.251, screenwidth*0.6, screenheight*0.08);
                teama_head.setFill(Color.rgb(253, 2, 61));
                root.getChildren().add(teama_head);

                //teama_name = new TextType1(screenwidth*0.12, screenheight*0.31, MatchScreen.matchBean.getTeama_name(), f_type1, root, Color.BLACK);       

                for(int i=0;i<gameBean.getNo_of_players();i++){
                    PlayerGameBean playergamebean = gameBean.getPlayer_data().get(i);
                    playera_rect = new Rectangle(screenwidth*0.2, screenheight*(0.332+(i*0.081)), screenwidth*0.6, screenheight*0.08);
                    if(i%2==0)
                        playera_rect.setFill(Color.WHITE);
                    else
                        playera_rect.setFill(Color.rgb(255, 215, 227));
                    root.getChildren().add(playera_rect);
                    namea = playergamebean.getPlayer_name()+"("+MultiPlayerScreen.skill_levels.get(playergamebean.getSkill_level()-1)+")";
                    Color text_colorA = Color.BLACK;
                    if(i == gameBean.getSeq_pos()){
                        namea = namea+"*";
                        text_colorA = Color.RED;
                        playera_rect.setFill(Color.LIGHTSKYBLUE);
                    }                   
                    playera_name = new TextType1(screenwidth*0.22, screenheight*(0.332+(i*0.081)+0.06), namea, f_type1, root, text_colorA);
                    
                    if(gameBean.getSeq_pos()>=i){
                        String scorea = "";
                        scorea=playergamebean.getTotal_score()+"("+playergamebean.getBall_count()+"/"+(gameBean.getNo_of_overs_each()*6 )+")";                            
                        playera_score = new TextType1(screenwidth*0.65, screenheight*(0.332+(i*0.081)+0.06), scorea, f_type1, root, Color.BLACK);
                    }
                     
                }*/               
            }
            if(HandleEvents.generalSettings.isTest_mode()){
                //System.out.println( AutoScoring.score_data+" \n "+AutoScoring.display_string);
                text_5 = new TextType5(screenwidth*0.9, screenheight*0.05, AutoScoring.score_data+" , "+AutoScoring.score_time,Color.BLACK,Color.WHITE);
                text_5.setLayoutX(screenwidth*0.05);
                text_5.setLayoutY(screenheight*0.8);
                root.getChildren().add(text_5);
                text_5 = new TextType5(screenwidth*0.9, screenheight*0.05, AutoScoring.display_result,Color.BLACK,Color.WHITE);
                text_5.setLayoutX(screenwidth*0.05);
                text_5.setLayoutY(screenheight*0.9);                
                root.getChildren().add(text_5);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
//        public void handleWinner(){
//        try {
//            clearScreen();            
//            video = new File(HandleEvents.bowler_path, "/Media/match/WinnerScreen.mp4");
//            loadVideo(video);           
//            video_seq.clear();
//            video_seq.add(HandleEvents.workingDir+ "/Media/match/TargetScreen.mp4");            
//            doMediaSeq();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//    }
    
    public static File current_video = null;
    static DoubleProperty width =null;
    static DoubleProperty height = null;
    public boolean loadVideo(File video){
        try {
           current_video = video;
           m = new Media(video.toURI().toString());
           if(m.getError()==null){
               m.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in Media object.
                        System.out.println("Handle asynchronous error in Media object");
                    }
                });
               try {
                    mp.pause();
                    mp.stop();
                    mp.dispose();
                    mp=null;
                    mv.setMediaPlayer(null);
                    mp = new MediaPlayer(m);           
                    mv.setMediaPlayer(mp);
                } catch (Exception e) {
                    e.printStackTrace();
                }              
               width = mv.fitWidthProperty();
               height = mv.fitHeightProperty();
               width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
               height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
           }else{
               System.out.println("error loading media");
           }            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /*
    2

    So I installed the windows media feature pack in order to get adobe premiere pro working (because it required a dll file from windows media player 
    (which I didn't had installed because I run an N version of windows) and now the video does play for me.
    I believe installing ffdshow tryouts will provide the necessary codecs to play FLV in Windows Media Player. (Although I thought all versions of K-lite codec pack included FLV support)
    */
    public boolean doMediaLoop(){
        try {
            mp.play();
            //System.out.println("Video triggered");
            mp.setCycleCount(2);            
            mp.setOnError(new Runnable() {
                @Override
                public void run() {                    
                    try {
                        if(current_video!=null){
                            loadVideo(current_video);
                            doMediaLoop();                            
                        }                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                    
                }                    
            });
            mp.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    try {
                        if(mp.getCurrentCount()>=mp.getCycleCount()-1){
                            mp.setCycleCount(mp.getCurrentCount()+2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }                    
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean doinitMedia(){
        try {
            try {
                if(root.getChildren().contains(mv)){
                    root.getChildren().remove(mv);
                }
                if(mp!=null){
                    mp.pause();
                    mp.stop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            current_video = new File(HandleEvents.workingDir, "/Media/match/home/1.mp4");
            m = new Media(current_video.toURI().toString());
            if(m.getError()==null){
               m.setOnError(new Runnable() {
                    public void run() {
                        // Handle asynchronous error in Media object.
                        System.out.println("Handle asynchronous error in Media object");
                    }
                });
                mp = new MediaPlayer(m); 
                mv = new MediaView(mp);
                /*mv.setOnError(new EventHandler() {
                     public void handle(MediaErrorEvent t) {
                         // Handle asynchronous error in MediaView.
                         System.out.println("Handle asynchronous error in MediaView: "+ t.getMediaError());
                     }

                     @Override
                     public void handle(Event arg0) {
                         // TODO Auto-generated method stub
                         System.out.println("Handle asynchronous error in MediaView arg0: "+arg0.toString());
                     }
                 });*/
                width = mv.fitWidthProperty();
                height = mv.fitHeightProperty();
                width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
                mv.setPreserveRatio(false);                    
                root.getChildren().add(mv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        return true;
    }
    public boolean doDeviceReInit(){        
        try {            
            /*root.getChildren().clear();
            root = new Pane();
            //Rectangle2D bounds = ZaPCricketSimulator.screens.get(HandleEvents.generalSettings.getProjector_screen_index()).getVisualBounds();
            //screenwidth=bounds.getWidth();
            //screenheight = bounds.getHeight();
            
            scene = new Scene(root, bounds.getHeight(), bounds.getWidth());
            scene.setFill(Color.BLACK);
            this.setScene(scene);        
            this.setMaximized(true);
            this.setX(bounds.getMinX());
            this.setY(bounds.getMinY());
            this.setFullScreen(true);
            //this.initStyle(StageStyle.UNDECORATED);
            this.setAlwaysOnTop(true);
            //this.show();*/
             m = new Media(current_video.toURI().toString());
             mp = new MediaPlayer(m); 
             mv = new MediaView(mp);
             width = mv.fitWidthProperty();
            height = mv.fitHeightProperty();
            width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
            mv.setPreserveRatio(false);                    
            root.getChildren().add(mv);
            doMediaSeq();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    static double screenwidth,screenheight;   
    static Scene scene = null;
    static Rectangle2D bounds = null;
    public boolean doDeviceInit(){
        try {
            bounds = ZaPCricketSimulator.screens.get(HandleEvents.generalSettings.getProjector_screen_index()).getVisualBounds();
            screenwidth=bounds.getWidth();
            screenheight = bounds.getHeight();
            scene = new Scene(root, bounds.getHeight(), bounds.getWidth());
            scene.setFill(Color.BLACK);
            this.setScene(scene);        
            this.setMaximized(true);
            this.setX(bounds.getMinX());
            this.setY(bounds.getMinY());
            this.setFullScreen(true);
            this.initStyle(StageStyle.UNDECORATED);
            this.setAlwaysOnTop(true);
            this.show();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    /*
    Example code
        // Set the Times of the Player
        player.setStartTime(Duration.minutes(1));
        player.setStopTime(Duration.minutes(2));
    
        player.getStatus() == Status.PLAYING
    
    */
}
