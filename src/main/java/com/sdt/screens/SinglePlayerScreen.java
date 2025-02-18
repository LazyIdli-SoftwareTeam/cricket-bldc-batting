/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.GameBean;
import com.sdt.data.KeyValueBean;
import com.sdt.data.MatchBean;
import com.sdt.displaycomponents.BowlerButton;
import com.sdt.displaycomponents.CChoiceBox_KeyValue;
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
import com.sdt.displaycomponents.SpeedButton1;
import com.sdt.displaycomponents.TextType1;
import com.sdt.displaycomponents.TextType3_0;
import com.sdt.displaycomponents.TextType4;
import com.sdt.displaycomponents.TiltButton1;
import com.sdt.displaycomponents.TiltPanButton;
import com.sdt.displaycomponents.TiltPanButton1;
import com.sdt.displaycomponents.UpdateButton;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import com.sdt.serial.USB_Com;
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
import static zapcricketsimulator.HandleEvents.gameBean;

/**
 *
 * @author possi
 */

public class SinglePlayerScreen {
    //public static MatchBean matchBean = new MatchBean();    
    public static SinglePlayerScreen this_obj=null;

    Pane pane = null;
    double width =0;
    double height = 0;

    public SinglePlayerScreen(Pane pane, double width , double height){
        this_obj=this;
        this.pane = pane;
        this.width=width;
        this.height=height;
//         new Receive();
        //System.out.println(width+","+height);
        showScreen();
    }

    public static void refreshData(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
       
    }
    //public static int scores [] = {1,2,3,4,6};
    //RunSelector1 runselector[] = new RunSelector1[5];
    public static CTextField_Data bplayer_id= null;
    public static CTextField_Data bplayer_name= null;
    //public static CTextField_Data bPlayer_mob= null;
    //public static CTextField_Data bplayer_email=null;
    //public static TextField bplayer_score= null;
    public static CSpinner<Integer> no_of_overs=null;
    public static CChoiceBox_KeyValue bplayer_skill = null;
    public static CChoiceBox_KeyValue bboler_selection = null;
    public static ArrayList<KeyValueBean> boler_select = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(0, "Manual"),new KeyValueBean(1, "Auto"),new KeyValueBean(2, "Sequence 1"),new KeyValueBean(3, "Sequence 2")));
    //public static ArrayList<KeyValueBean> skill_levels = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(1, "Beginner"),new KeyValueBean(2, "Intermediate"),new KeyValueBean(3, "Club Player"),new KeyValueBean(4, "Professional")));
    public void showScreen(){
        pane.getChildren().clear();
        String workingDir = System.getProperty("user.dir");
        try {
            gameBean = new GameBean();
            gameBean.init(1);
            String bg_path = "/Media/images/SinglePlayerBG.png";
            FileInputStream bg_img = new FileInputStream(new File(workingDir, bg_path));
            final Image bg_image = new Image(bg_img);
            ImagePattern imgptrn = new ImagePattern(bg_image);
            Rectangle bg = new Rectangle(0, 0, width, height);
            bg.setFill(imgptrn);        
            pane.getChildren().add(bg);

            double header_font_size = height*0.04;
            Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,header_font_size);
            //TextType3_0 header = new TextType3_0((width/2)+20, height*0.035, "Single Player", f_type1, pane,Color.WHITE);

             double regular_font_size =height*0.03;        
            Font f_type2 = Font.font("sans-serif", FontWeight.NORMAL, FontPosture.REGULAR,regular_font_size);
            
            String img_filename = "/Media/images/ip_bg.png";
            FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
            
            TextType4 t_play_overs = new TextType4(width*0.175, (height*0.3)+regular_font_size, "OVERS :", f_type2, pane,Color.WHITE);            
            
            Image img = new Image(img_file);
            Rectangle rect = new Rectangle(width*0.18,height*0.295,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            no_of_overs= new CSpinner(1, 8, gameBean.getNo_of_overs_each(), 1);
            no_of_overs.setEditable(true);
            no_of_overs.setLayoutX(width*0.2);
            no_of_overs.setLayoutY(height*0.305);
            pane.getChildren().add(no_of_overs);
            
            TextType4 t_bowler_select = new TextType4(width*0.175, (height*0.375)+regular_font_size, "BOWLING MODE :", f_type2, pane,Color.WHITE);
            
            
            //img_filename = "/Media/images/ip_bg.png";
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.37,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bboler_selection= new CChoiceBox_KeyValue(boler_select,gameBean.getBowler_selection());
            bboler_selection.setLayoutX(width*0.2);
            bboler_selection.setLayoutY(height*0.38);
            pane.getChildren().add(bboler_selection);
            
            
            TextType4 t_player_ID = new TextType4(width*0.175, (height*0.45)+regular_font_size, "PLAYER ID :", f_type2, pane,Color.WHITE);
            TextType4 t_player_name = new TextType4(width*0.175, (height*0.525)+regular_font_size, "PLAYER NAME :", f_type2, pane,Color.WHITE);
            TextType4 t_Player_Skill = new TextType4(width*0.175, (height*0.6)+regular_font_size, "PLAYER SKILL :", f_type2, pane,Color.WHITE);
            //TextType1 t_Player_Mob = new TextType1(width*0.1, (height*0.6)+regular_font_size, "Mobile No", f_type2, pane,Color.WHITE);
            //TextType1 t_Player_email = new TextType1(width*0.1, (height*0.675)+regular_font_size, "Email Address", f_type2, pane,Color.WHITE);
            //TextType1 t_Player_score = new TextType1(width*0.1, (height*0.75)+regular_font_size, "Player Score", f_type2, pane,Color.WHITE);
            
            
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.445,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bplayer_id= new CTextField_Data(gameBean.getPlayer_data().get(0).getPlayer_id(),16);
            bplayer_id.setLayoutX(width*0.2);
            bplayer_id.setLayoutY(height*0.455);
            pane.getChildren().add(bplayer_id);
            
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.52,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bplayer_name= new CTextField_Data(gameBean.getPlayer_data().get(0).getPlayer_name(),16);
            bplayer_name.setLayoutX(width*0.2);
            bplayer_name.setLayoutY(height*0.530);
            pane.getChildren().add(bplayer_name);
            
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.595,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bplayer_skill= new CChoiceBox_KeyValue(MultiPlayerScreen.skill_levels,gameBean.getPlayer_data().get(0).getSkill_level(),gameBean.getPlayer_data().get(0).getHand_usage());
            bplayer_skill.setLayoutX(width*0.2);
            bplayer_skill.setLayoutY(height*0.605);
            pane.getChildren().add(bplayer_skill);
            System.out.println("player skill " + bplayer_skill.getValue());
            /*bPlayer_mob= new CTextField_Data("",16);
            bPlayer_mob.setLayoutX(width*0.225);
            bPlayer_mob.setLayoutY(height*0.605);
            pane.getChildren().add(bPlayer_mob);
            
            bplayer_email= new CTextField_Data("",25);
            bplayer_email.setLayoutX(width*0.225);
            bplayer_email.setLayoutY(height*0.680);
            pane.getChildren().add(bplayer_email);
            
            //t_teama_score.setWrappingWidth(width*0.1);
            bplayer_score= new TextField(0+"");
            bplayer_score.setEditable(false);
            bplayer_score.setLayoutX(width*0.225);
            bplayer_score.setLayoutY(height*0.755);
            pane.getChildren().add(bplayer_score);*/
            

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
            
            //TextType1 t_bowler_selection = new TextType1(width*0.55, (height*0.21)+regular_font_size, "BOWLER SELECTION", f_type2, pane,Color.WHITE);
            img_filename = "/Media/images/BowlerBG.png";
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            Rectangle bowler_rect = new Rectangle(width*0.415, height*0.25, width*0.50, height*0.25);
            bowler_rect.setFill(new ImagePattern(img));
            bowler_rect.setArcWidth(50);
            bowler_rect.setArcHeight(50);
            pane.getChildren().add(bowler_rect);

            for(int i = 0 ; i < 8;i++){
                BowlerButton bowler = new BowlerButton(i+1, width*0.09, height*0.08);
                bowler.setLayoutX(width*0.44+((i%4)*(width*0.12)));
                bowler.setLayoutY(height*0.28+((i/4)*(height*0.12)));
                pane.getChildren().add(bowler);
            }       

           /* TextType3_0 t_play = new TextType3_0(width*0.59, (height*0.30)+regular_font_size, "PLAY", f_type2, pane,Color.WHITE);
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
            pane.getChildren().add(pause_button);*/
            
            /*GameButton start_button = new GameButton(Variables.button_type_start);
            start_button.setLayoutX((width/2));
            start_button.setLayoutY(height*0.2);
            start_button.setScaleX(width/1200);
            start_button.setScaleY(height/640);        
            pane.getChildren().add(start_button);*/
            
            //TextType3_0 t_start = new TextType3_0(width*0.43, (height*0.38)+regular_font_size, "START", f_type2, pane,Color.WHITE);
            //TextType3_0 t_pause = new TextType3_0(width*0.43, (height*0.58)+regular_font_size, "PAUSE", f_type2, pane,Color.WHITE);
            //TextType3_0 t_play = new TextType3_0(width*0.43, (height*0.78)+regular_font_size, "PLAY", f_type2, pane,Color.WHITE);

            GameButton start_button = new GameButton(Variables.button_type_start);
            start_button.setLayoutX((width*0.05));
            start_button.setLayoutY(height*0.85);
            start_button.setScaleX(width/1200);
            start_button.setScaleY(height/640);        
            pane.getChildren().add(start_button);
            
            GameButton pause_button = new GameButton(Variables.button_type_pause);
            pause_button.setLayoutX((width*0.20));
            pause_button.setLayoutY(height*0.85);
            pause_button.setScaleX(width/1200);
            pause_button.setScaleY(height/640);        
            pane.getChildren().add(pause_button);
            
            GameButton play_button = new GameButton(Variables.button_type_play);
            play_button.setLayoutX((width*0.35));
            play_button.setLayoutY(height*0.85);
            play_button.setScaleX(width/1200);
            play_button.setScaleY(height/640);        
            pane.getChildren().add(play_button);

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

            /*for(int i = 0 ; i < 8;i++){
                ResultButton rbutton = new ResultButton(Variables.button_type_result_norun+i, width*0.08, height*0.08);
                rbutton.setLayoutX(width*0.05+((i%4)*(width*0.11)));
                rbutton.setLayoutY(height*0.72+((i/4)*(height*0.10)));            
                pane.getChildren().add(rbutton);
            } */  


            /*for(int i=0;i<5;i++){
                runselector[i]=new RunSelector1((width*0.077)+((height*0.028)*(i*6)), (height*0.625), height*0.03, height*0.06, scores[i]);
                pane.getChildren().add(runselector[i]);
            }*/
            
            /*ResultButton rbutton = new ResultButton(Variables.button_type_result_norun, width*0.08, height*0.08);
            rbutton.setLayoutX(width*0.05);
            rbutton.setLayoutY(height*0.72);            
            pane.getChildren().add(rbutton);*/

            //TextType1 t_machinecontrol = new TextType1(width*0.55, (height*0.54)+regular_font_size, "MACHINE CONTROL", f_type2, pane,Color.WHITE);
            img_filename = "/Media/images/BowlerBG.png";
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            Rectangle machine_rect = new Rectangle(width*0.415, height*0.58, width*0.50, height*0.25);
            machine_rect.setFill(new ImagePattern(img));
            machine_rect.setArcWidth(50);
            machine_rect.setArcHeight(50);
            pane.getChildren().add(machine_rect);

            //TextType1 t_spped = new TextType1(width*0.55, (height*0.60)+regular_font_size, "SPEED", f_type2, pane,Color.WHITE);
            //TextType1 t_tilt = new TextType1(width*0.64, (height*0.80)+regular_font_size, "TILT", f_type2, pane,Color.WHITE);
            //TextType1 t_pan = new TextType1(width*0.73, (height*0.80)+regular_font_size, "PAN", f_type2, pane,Color.WHITE);
            //TextType1 t_emg = new TextType1(width*0.87, (height*0.60)+regular_font_size, "EMG", f_type2, pane,Color.WHITE);

            SpeedButton1 speedbtn = new SpeedButton1(width*0.17, height*0.18);
            speedbtn.setLayoutX(width*0.59);
            speedbtn.setLayoutY(height*0.67);            
            pane.getChildren().add(speedbtn);
            
            TiltPanButton1 tiltpan = new TiltPanButton1(width*0.15, height*0.23);
            tiltpan.setLayoutX(width*0.43);
            tiltpan.setLayoutY(height*0.59);
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
            emg_button.setLayoutX((width*0.84));
            emg_button.setLayoutY(height*0.71);
            emg_button.setScaleX(width/1200);
            emg_button.setScaleY(height/640);        
            pane.getChildren().add(emg_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
}
