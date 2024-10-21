/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.MatchBean;
import com.sdt.displaycomponents.BowlerButton;
import com.sdt.displaycomponents.CSpinner;
import com.sdt.displaycomponents.CTextField_Data;
import com.sdt.displaycomponents.EmgButton;
import com.sdt.displaycomponents.GameButton;
import com.sdt.displaycomponents.KeypadView;
import com.sdt.displaycomponents.PanButton;
import com.sdt.displaycomponents.ResultButton;
import com.sdt.displaycomponents.RunSelector1;
import com.sdt.displaycomponents.SettingsButton;
import com.sdt.displaycomponents.SpeedButton;
import com.sdt.displaycomponents.TextType1;
import com.sdt.displaycomponents.TextType3_0;
import com.sdt.displaycomponents.TiltButton1;
import com.sdt.displaycomponents.TiltPanButton;
import com.sdt.displaycomponents.UpdateButton;
import java.io.File;
import java.io.FileInputStream;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class MatchScreen {
    public static MatchBean matchBean = new MatchBean();
    public static MatchScreen this_obj=null;
    
    Pane pane = null;
    double width =0;
    double height = 0;
    
    public MatchScreen(Pane pane, double width , double height){
        this_obj=this;
        this.pane = pane;
        this.width=width;
        this.height=height;
        //System.out.println(width+","+height);
        showScreen();
    }
    
    public static void refreshData(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        this_obj.bteamaname.setText(matchBean.getTeama_name());
        this_obj.bteamamob.setText(matchBean.getTeama_mob());
        this_obj.bteambname.setText(matchBean.getTeamb_name());
        this_obj.bteambmob.setText(matchBean.getTeamb_mob());
        if(HandleEvents.game_status==Variables.game_status_started){
            if(matchBean.getTeama_pos()<matchBean.getNo_of_players()){
                String name = "";
                if(matchBean.getTeamA_player_names()!=null && matchBean.getTeamA_player_names().size()>matchBean.getTeama_pos())
                    name = matchBean.getTeamA_player_names().get(matchBean.getTeama_pos());
                else
                    name = "Player "+(matchBean.getTeama_pos()+1);
                if(matchBean.getTeamA_player_scores()!=null && matchBean.getTeamA_player_scores().size()>matchBean.getTeama_pos())
                    name = name+"("+matchBean.getTeamA_player_scores().get(matchBean.getTeama_pos())+")";
                this_obj.playera_name.setText(name);
            }
            this_obj.teama_score.setText(matchBean.getTeama_score()+"");
            this_obj.teama_overs.setText((matchBean.getTeama_balls()/6)+"."+(matchBean.getTeama_balls()%6));
            
            if(matchBean.getTeamb_pos()<matchBean.getNo_of_players()){
                String name = "";
                if(matchBean.getTeamB_player_names()!=null && matchBean.getTeamB_player_names().size()>matchBean.getTeamb_pos())
                    name = matchBean.getTeamB_player_names().get(matchBean.getTeamb_pos());
                else
                    name = "Player "+(matchBean.getTeama_pos()+1);
                if(matchBean.getTeamB_player_scores()!=null && matchBean.getTeamB_player_scores().size()>matchBean.getTeamb_pos())
                    name = name+"("+matchBean.getTeamB_player_scores().get(matchBean.getTeamb_pos())+")";
                this_obj.playerb_name.setText(name);
            }
            this_obj.teamb_score.setText(matchBean.getTeamb_score()+"");
            this_obj.teamb_overs.setText((matchBean.getTeamb_balls()/6)+"."+(matchBean.getTeamb_balls()%6));
        }
    }
    public static int scores [] = {1,2,3,4,6};
    RunSelector1 runselector[] = new RunSelector1[5];
    public static CTextField_Data bteamaname= null;
    public static CTextField_Data bteamamob= null;
    public static CTextField_Data playera_name=null;
    public static TextField teama_score= null;
    public static TextField teama_overs=null;
    public static CTextField_Data bteambname=null;
    public static CTextField_Data bteambmob=null;
    public static CTextField_Data playerb_name=null;
    public static TextField teamb_score=null;
    public static TextField teamb_overs=null;
    public void showScreen(){
        pane.getChildren().clear();
        String workingDir = System.getProperty("user.dir");
        try {
            String bg_path = "/Media/images/BG4.png";
            FileInputStream bg_img = new FileInputStream(new File(workingDir, bg_path));
            final Image bg_image = new Image(bg_img);
            ImagePattern imgptrn = new ImagePattern(bg_image);
            Rectangle bg = new Rectangle(0, 0, width, height);
            bg.setFill(imgptrn);        
            pane.getChildren().add(bg);

            double header_font_size = height*0.04;
            Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,header_font_size);
            TextType3_0 header = new TextType3_0((width/2)+20, header_font_size, "MATCH MODE", f_type1, pane,Color.WHITE);

             double regular_font_size =height*0.03;        
            Font f_type2 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,regular_font_size);
            TextType1 t_teama_name = new TextType1(width*0.03, (height*0.07)+regular_font_size, "TEAM A NAME", f_type2, pane,Color.WHITE);
            bteamaname= new CTextField_Data(matchBean.getTeama_name(),16);
            bteamaname.setLayoutX(width*0.2);
            bteamaname.setLayoutY(height*0.075);
            pane.getChildren().add(bteamaname);
            TextType1 t_teama_mob = new TextType1(width*0.03, (height*0.12)+regular_font_size, "TEAM A MOB", f_type2, pane,Color.WHITE);
            bteamamob= new CTextField_Data(matchBean.getTeama_mob(),13);
            bteamamob.setLayoutX(width*0.2);
            bteamamob.setLayoutY(height*0.125);
            pane.getChildren().add(bteamamob);
            
            TextType1 t_player1_score = new TextType1(width*0.03, (height*0.16)+regular_font_size, "TEAM A PLAYER", f_type2, pane,Color.WHITE);
            //t_player1_score.setWrappingWidth(width*0.1);
            playera_name= new CTextField_Data(25);
            playera_name.setLayoutX(width*0.2);
            playera_name.setLayoutY(height*0.165);
            pane.getChildren().add(playera_name);
            
            TextType1 t_teama_score = new TextType1(width*0.03, (height*0.2)+regular_font_size, "TEAM A SCORE", f_type2, pane,Color.WHITE);
            //t_teama_score.setWrappingWidth(width*0.1);
            teama_score= new TextField(0+"");
            teama_score.setEditable(false);
            teama_score.setLayoutX(width*0.2);
            teama_score.setLayoutY(height*0.205);
            pane.getChildren().add(teama_score);
            TextType1 t_teama_overs = new TextType1(width*0.03, (height*0.24)+regular_font_size, "TEAM A OVERS", f_type2, pane,Color.WHITE);
            //t_teama_overs.setWrappingWidth(width*0.1);
            teama_overs= new TextField(0+"");
            teama_overs.setEditable(false);
            teama_overs.setLayoutX(width*0.2);
            teama_overs.setLayoutY(height*0.245);
            pane.getChildren().add(teama_overs);

            UpdateButton update1 = new UpdateButton(Variables.button_type_match_update_a,width*0.08,height*0.08);
            update1.setLayoutX((width*0.34));
            update1.setLayoutY(height*0.1);
            pane.getChildren().add(update1);


            TextType1 t_teamb_name = new TextType1(width*0.51, (height*0.07)+regular_font_size, "TEAM B NAME", f_type2, pane,Color.WHITE);
            bteambname= new CTextField_Data(matchBean.getTeama_name(),16);
            bteambname.setLayoutX(width*0.68);
            bteambname.setLayoutY(height*0.075);
            pane.getChildren().add(bteambname);
            TextType1 t_teamb_mob = new TextType1(width*0.51, (height*0.12)+regular_font_size, "TEAM B MOB", f_type2, pane,Color.WHITE);
            bteambmob= new CTextField_Data(matchBean.getTeama_mob(),13);
            bteambmob.setLayoutX(width*0.68);
            bteambmob.setLayoutY(height*0.125);
            pane.getChildren().add(bteambmob);
            
            TextType1 t_playerb_score = new TextType1(width*0.51, (height*0.16)+regular_font_size, "TEAM B PLAYER", f_type2, pane,Color.WHITE);
            //t_player2_score.setWrappingWidth(width*0.1);
            playerb_name= new CTextField_Data(25);
            playerb_name.setLayoutX(width*0.68);
            playerb_name.setLayoutY(height*0.165);
            pane.getChildren().add(playerb_name);           

            TextType1 t_teamb_score = new TextType1(width*0.51, (height*0.2)+regular_font_size, "TEAM B SCORE", f_type2, pane,Color.WHITE);
            //t_teamb_score.setWrappingWidth(width*0.1);
            teamb_score= new TextField(0+"");
            teamb_score.setEditable(false);
            teamb_score.setLayoutX(width*0.68);
            teamb_score.setLayoutY(height*0.205);
            pane.getChildren().add(teamb_score);
            
            TextType1 t_teamb_overs = new TextType1(width*0.51, (height*0.24)+regular_font_size, "TEAM B OVERS", f_type2, pane,Color.WHITE);
            //t_teamb_overs.setWrappingWidth(width*0.1);
            teamb_overs= new TextField(0+"");
            teamb_overs.setEditable(false);
            teamb_overs.setLayoutX(width*0.68);
            teamb_overs.setLayoutY(height*0.245);
            pane.getChildren().add(teamb_overs);

            UpdateButton update2 = new UpdateButton(Variables.button_type_match_update_b,width*0.08,height*0.08);
            update2.setLayoutX((width*0.8));
            update2.setLayoutY(height*0.1);
            pane.getChildren().add(update2);

            SettingsButton settings = new SettingsButton(3);
            settings.setLayoutX((width*0.9));
            settings.setLayoutY(height*0.1);
            settings.setScaleX(width/1480);
            settings.setScaleY(height/780);
            pane.getChildren().add(settings);     
            
            KeypadView keypad = new KeypadView(width*0.04, height*0.06);
            keypad.setLayoutX((width*0.95));
            keypad.setLayoutY(height*0.02);
            pane.getChildren().add(keypad);
            
            /*TextType1 t_bowler_selection = new TextType1(width*0.1, (height*0.26)+regular_font_size, "BOWLER SELECTION", f_type2, pane,Color.WHITE);

            Rectangle bowler_rect = new Rectangle(width*0.08, height*0.30, width*0.40, height*0.25);
            bowler_rect.setFill(Color.rgb(247, 150, 70));
            bowler_rect.setArcWidth(50);
            bowler_rect.setArcHeight(50);
            pane.getChildren().add(bowler_rect);*/

            for(int i = 0 ; i < 8;i++){
                BowlerButton bowler = new BowlerButton(i+1, width*0.08, height*0.07);
                bowler.setLayoutX(width*0.05+((i%4)*(width*0.113)));
                bowler.setLayoutY(height*0.32+((i/4)*(height*0.12)));

                pane.getChildren().add(bowler);
            }       

            


            TextType3_0 t_play = new TextType3_0(width*0.59, (height*0.30)+regular_font_size, "PLAY", f_type2, pane,Color.WHITE);
            GameButton play_button = new GameButton(Variables.button_type_play);
            play_button.setLayoutX((width*0.59));
            play_button.setLayoutY(height*0.42);
            play_button.setScaleX(width/1200);
            play_button.setScaleY(height/640);        
            pane.getChildren().add(play_button);
            TextType3_0 t_pause = new TextType3_0(width*0.74, (height*0.30)+regular_font_size, "PAUSE", f_type2, pane,Color.WHITE);
            GameButton pause_button = new GameButton(Variables.button_type_pause);
            pause_button.setLayoutX((width*0.74));
            pause_button.setLayoutY(height*0.42);
            pause_button.setScaleX(width/1200);
            pause_button.setScaleY(height/640);        
            pane.getChildren().add(pause_button);
            TextType3_0 t_start = new TextType3_0(width*0.89, (height*0.30)+regular_font_size, "START", f_type2, pane,Color.WHITE);
            GameButton start_button = new GameButton(Variables.button_type_start);
            start_button.setLayoutX((width*0.89));
            start_button.setLayoutY(height*0.42);
            start_button.setScaleX(width/1200);
            start_button.setScaleY(height/640);        
            pane.getChildren().add(start_button);

            /*GameButton play_button = new GameButton(Variables.button_type_play);
            play_button.setLayoutX((width*0.6));
            play_button.setLayoutY(height*0.63);
            play_button.setScaleX(width/1200);
            play_button.setScaleY(height/640);        
            pane.getChildren().add(play_button);
            GameButton pause_button = new GameButton(Variables.button_type_pause);
            pause_button.setLayoutX((width*0.7));
            pause_button.setLayoutY(height*0.63);
            pause_button.setScaleX(width/1200);
            pause_button.setScaleY(height/640);        
            pane.getChildren().add(pause_button);
            GameButton start_button = new GameButton(Variables.button_type_start);
            start_button.setLayoutX((width*0.8));
            start_button.setLayoutY(height*0.63);
            start_button.setScaleX(width/1200);
            start_button.setScaleY(height/640);        
            pane.getChildren().add(start_button);*/

            /*TextType1 t_comments = new TextType1(width*0.52, (height*0.6)+regular_font_size, "COMMENTS", f_type2, pane,Color.WHITE);
            CTextField_Data bcomments= new CTextField_Data(matchBean.getComments(),25);
            bcomments.setPrefWidth(width*0.20);
            bcomments.setLayoutX(width*0.62);
            bcomments.setLayoutY(height*0.605);
            pane.getChildren().add(bcomments);*/


            /*Rectangle score_rect = new Rectangle(width*0.08, height*0.78, width*0.40, height*0.25);
            score_rect.setFill(Color.rgb(247, 150, 70));
            score_rect.setArcWidth(50);
            score_rect.setArcHeight(50);
            pane.getChildren().add(score_rect);*/

            for(int i = 0 ; i < 8;i++){
                ResultButton rbutton = new ResultButton(Variables.button_type_result_norun+i, width*0.08, height*0.08);
                rbutton.setLayoutX(width*0.05+((i%4)*(width*0.11)));
                rbutton.setLayoutY(height*0.72+((i/4)*(height*0.10)));            
                pane.getChildren().add(rbutton);
            }   


            for(int i=0;i<5;i++){
                runselector[i]=new RunSelector1((width*0.077)+((height*0.028)*(i*6)), (height*0.625), height*0.03, height*0.06, scores[i]);
                pane.getChildren().add(runselector[i]);
            }

            /*TextType1 t_machinecontrol = new TextType1(width*0.52, (height*0.74)+regular_font_size, "MACHINE CONTROL", f_type2, pane,Color.WHITE);
            Rectangle machine_rect = new Rectangle(width*0.5, height*0.78, width*0.40, height*0.25);
            machine_rect.setFill(Color.rgb(247, 150, 70));
            machine_rect.setArcWidth(50);
            machine_rect.setArcHeight(50);
            pane.getChildren().add(machine_rect);*/

            TextType1 t_spped = new TextType1(width*0.55, (height*0.71)+regular_font_size, "SPEED", f_type2, pane,Color.WHITE);
            //TextType1 t_tilt = new TextType1(width*0.64, (height*0.80)+regular_font_size, "TILT", f_type2, pane,Color.WHITE);
            //TextType1 t_pan = new TextType1(width*0.73, (height*0.80)+regular_font_size, "PAN", f_type2, pane,Color.WHITE);
            TextType1 t_emg = new TextType1(width*0.87, (height*0.71)+regular_font_size, "EMG", f_type2, pane,Color.WHITE);

            SpeedButton speedbtn = new SpeedButton(width*0.06, height*0.16);
            speedbtn.setLayoutX(width*0.545);
            speedbtn.setLayoutY(height*0.75);            
            pane.getChildren().add(speedbtn);
            
            TiltPanButton tiltpan = new TiltPanButton(width*0.09, height*0.16);
            tiltpan.setLayoutX(width*0.69);
            tiltpan.setLayoutY(height*0.73);            
            pane.getChildren().add(tiltpan);

            /*TiltButton1 tiltbtn = new TiltButton1(width*0.06, height*0.16);
            tiltbtn.setLayoutX(width*0.635);
            tiltbtn.setLayoutY(height*0.84);            
            pane.getChildren().add(tiltbtn);

            PanButton panbtn = new PanButton(width*0.06, height*0.16);
            panbtn.setLayoutX(width*0.725);
            panbtn.setLayoutY(height*0.84);            
            pane.getChildren().add(panbtn);*/

            EmgButton emg_button = new EmgButton(Variables.button_type_emg);
            emg_button.setLayoutX((width*0.89));
            emg_button.setLayoutY(height*0.835);
            emg_button.setScaleX(width/1200);
            emg_button.setScaleY(height/640);        
            pane.getChildren().add(emg_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
}
