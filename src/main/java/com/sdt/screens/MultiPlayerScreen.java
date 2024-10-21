/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.GameBean;
import com.sdt.data.KeyValueBean;
import com.sdt.data.MatchBean;
import com.sdt.data.PlayerGameBean;
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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class MultiPlayerScreen {
    //public static MatchBean matchBean = new MatchBean();
    public static MultiPlayerScreen this_obj=null;

    Pane pane = null;
    double width =0;
    double height = 0;
    
    public MultiPlayerScreen(Pane pane, double width , double height){
        this_obj=this;
        this.pane = pane;
        this.width=width;
        this.height=height;
        //System.out.println(width+","+height);
        showScreen();
    }
    public static ArrayList<KeyValueBean> skill_levels = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(1, "Backyard",1),new KeyValueBean(1, "Backyard",2),new KeyValueBean(2, "Local",1),new KeyValueBean(2, "Local",2),new KeyValueBean(3, "First Class",1),new KeyValueBean(3, "First Class",2),new KeyValueBean(4, "International",1),new KeyValueBean(4, "International",2)));
    public void refreshData(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        for(int i=0;i<bplayer_ids.size();i++){
            try {
                pane.getChildren().remove(bplayer_ids.get(i));
                pane.getChildren().remove(bplayer_names.get(i));
                pane.getChildren().remove(bplayer_skills.get(i));
                pane.getChildren().remove(outlines.get(i*3));
                pane.getChildren().remove(outlines.get((i*3)+1));
                pane.getChildren().remove(outlines.get((i*3)+2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } 
        String workingDir = System.getProperty("user.dir");
        String img_filename = "/Media/images/ip_bg1.png";
        try {
           for(int i=0;i<gameBean.getNo_of_players();i++){
                //System.out.println();
                PlayerGameBean playergamebean = gameBean.getPlayer_data().get(i);
                if(i<bplayer_ids.size()){
                    pane.getChildren().add(outlines.get(i*3));
                    pane.getChildren().add(bplayer_ids.get(i));                     
                }else{ 
                     FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
                     Image img = new Image(img_file);
                     Rectangle rect = new Rectangle(width*0.0425,height*(0.435+(i*0.05)),width*0.09,height*0.06);
                     rect.setFill(new ImagePattern(img));
                     pane.getChildren().add(rect);
                     outlines.add(rect);  
                     
                     CTextField_Data bplayer_id= new CTextField_Data(playergamebean.getPlayer_id(),13);
                     bplayer_id.setPrefWidth(width*0.075);
                     bplayer_ids.add(bplayer_id);
                     bplayer_id.setLayoutX(width*0.05);
                     bplayer_id.setLayoutY(height*(0.45+(i*0.05)));
                     pane.getChildren().add(bplayer_id);
                }
                if(i<bplayer_names.size()){
                     pane.getChildren().add(outlines.get((i*3)+1));
                     pane.getChildren().add(bplayer_names.get(i));
                }else{ 
                    FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
                    Image img = new Image(img_file);
                    Rectangle rect = new Rectangle(width*0.1325,height*(0.435+(i*0.05)),width*0.09,height*0.06);
                    rect.setFill(new ImagePattern(img));
                    pane.getChildren().add(rect);
                    outlines.add(rect);
                    
                     CTextField_Data bplayer_name= new CTextField_Data(playergamebean.getPlayer_name(),13);
                     bplayer_name.setPrefWidth(width*0.075);
                     bplayer_names.add(bplayer_name);
                     bplayer_name.setLayoutX(width*0.14);
                     bplayer_name.setLayoutY(height*(0.45+(i*0.05)));
                     pane.getChildren().add(bplayer_name);
                }

                if(i<bplayer_skills.size()){
                    pane.getChildren().add(outlines.get((i*3)+2));
                    pane.getChildren().add(bplayer_skills.get(i));
                }else{ 
                    FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
                    Image img = new Image(img_file);
                    Rectangle rect = new Rectangle(width*0.241,height*(0.435+(i*0.05)),width*0.085,height*0.06);
                    rect.setFill(new ImagePattern(img));
                    pane.getChildren().add(rect);
                    outlines.add(rect);
                     CChoiceBox_KeyValue bplayer_skill= new CChoiceBox_KeyValue(skill_levels,playergamebean.getSkill_level(),playergamebean.getHand_usage());
                     bplayer_skills.add(bplayer_skill);
                     bplayer_skill.setLayoutX(width*0.25);
                     bplayer_skill.setLayoutY(height*(0.45+(i*0.05)));
                     pane.getChildren().add(bplayer_skill);
                }
           } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    //public static int scores [] = {1,2,3,4,6};
    //RunSelector1 runselector[] = new RunSelector1[5];
    public static ArrayList<Rectangle> outlines = new ArrayList<>();
    public static ArrayList<CTextField_Data> bplayer_ids = new ArrayList<>();
    public static ArrayList<CTextField_Data> bplayer_names = new ArrayList<>();
    public static ArrayList<CChoiceBox_KeyValue> bplayer_skills = new ArrayList<>();
    //public static CTextField_Data bplayer_id= null;
    //public static CTextField_Data bplayer_name= null;
    //public static CTextField_Data bPlayer_mob= null;
    //public static CTextField_Data bplayer_email=null;
    //public static TextField bplayer_score= null;
    public static CSpinner<Integer> no_of_overs=null;
    public static CChoiceBox_KeyValue bno_of_players = null;
    public static CChoiceBox_KeyValue bboler_selection = null;
    
    public void showScreen(){
        pane.getChildren().clear();
        String workingDir = System.getProperty("user.dir");
        try {
            gameBean = new GameBean();
            gameBean.init(gameBean.getNo_of_players());
            String bg_path = "/Media/images/MultiPlayerBG.png";
            FileInputStream bg_img = new FileInputStream(new File(workingDir, bg_path));
            final Image bg_image = new Image(bg_img);
            ImagePattern imgptrn = new ImagePattern(bg_image);
            Rectangle bg = new Rectangle(0, 0, width, height);
            bg.setFill(imgptrn);        
            pane.getChildren().add(bg);

            double header_font_size = height*0.04;
            Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,header_font_size);
            //TextType3_0 header = new TextType3_0((width/2)+20, height*0.035, "Multi Player", f_type1, pane,Color.WHITE);

             double regular_font_size =height*0.03;        
            Font f_type2 = Font.font("sans-serif", FontWeight.NORMAL, FontPosture.REGULAR,regular_font_size);
            
            String img_filename = "/Media/images/ip_bg.png";
            FileInputStream img_file = new FileInputStream(new File(workingDir, img_filename));
            
            ArrayList<KeyValueBean> players_list = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(2, "2"),new KeyValueBean(3, "3"),new KeyValueBean(4, "4"),new KeyValueBean(5, "5"),new KeyValueBean(6, "6"),new KeyValueBean(7, "7"),new KeyValueBean(8, "8")));
            TextType4 t_palyers_count = new TextType4(width*0.175, (height*0.175)+regular_font_size, "No Of Players :", f_type2, pane,Color.WHITE);
            
            Image img = new Image(img_file);
            Rectangle rect = new Rectangle(width*0.18,height*0.170,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bno_of_players= new CChoiceBox_KeyValue(players_list, gameBean.getNo_of_players());
            bno_of_players.setLayoutX(width*0.2);
            bno_of_players.setLayoutY(height*0.180);
            pane.getChildren().add(bno_of_players);
            bno_of_players.valueProperty().addListener(new ChangeListener<KeyValueBean>() {
                public void changed(ObservableValue<? extends KeyValueBean> ov,
                    KeyValueBean old_val, KeyValueBean new_val) {
                    if(new_val.getKey()>gameBean.getNo_of_players()){
                        for(int i=gameBean.getNo_of_players();i<new_val.getKey();i++){
                            gameBean.getPlayer_data().add(new PlayerGameBean(i+1));
                        }
                        gameBean.setNo_of_players(new_val.getKey());
                    }else if(new_val.getKey()<gameBean.getNo_of_players()){
                        for(int i=gameBean.getNo_of_players();i>new_val.getKey();i--){
                            gameBean.getPlayer_data().remove(i-1);
                        }
                        gameBean.setNo_of_players(new_val.getKey());
                    }
                    refreshData();
                }
            });
            
            
            
            TextType4 t_play_overs = new TextType4(width*0.175, (height*0.25)+regular_font_size, "Overs :", f_type2, pane,Color.WHITE);
            
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.245,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            no_of_overs= new CSpinner(1, 8, gameBean.getNo_of_overs_each(), 1);
            no_of_overs.setEditable(true);
            no_of_overs.setLayoutX(width*0.2);
            no_of_overs.setLayoutY(height*0.255);
            pane.getChildren().add(no_of_overs);
            
            TextType4 t_bowler_select = new TextType4(width*0.175, (height*0.325)+regular_font_size, "Bowling Mode :", f_type2, pane,Color.WHITE);
            //ArrayList<KeyValueBean> boler_select = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(0, "Manual"),new KeyValueBean(1, "Auto"),new KeyValueBean(2, "Sequence 1"),new KeyValueBean(3, "Sequence 2")));
            
            img_file = new FileInputStream(new File(workingDir, img_filename));
            img = new Image(img_file);
            rect = new Rectangle(width*0.18,height*0.32,width*0.15,height*0.05);
            rect.setFill(new ImagePattern(img));
            pane.getChildren().add(rect);
            
            bboler_selection= new CChoiceBox_KeyValue(SinglePlayerScreen.boler_select,gameBean.getBowler_selection());
            bboler_selection.setLayoutX(width*0.2);
            bboler_selection.setLayoutY(height*0.33);
            pane.getChildren().add(bboler_selection);           
            
            TextType1 t_player_ID = new TextType1(width*0.05, (height*0.4)+regular_font_size, "Player ID", f_type2, pane,Color.WHITE);
            TextType1 t_player_name = new TextType1(width*0.14, (height*0.4)+regular_font_size, "Player Name", f_type2, pane,Color.WHITE);
            TextType1 t_Player_Skill = new TextType1(width*0.25, (height*0.4)+regular_font_size, "Player Skill", f_type2, pane,Color.WHITE);           
            img_filename = "/Media/images/ip_bg1.png";
            //ArrayList<KeyValueBean> skill_levels = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(1, "Beginner"),new KeyValueBean(2, "Intermediate"),new KeyValueBean(3, "Club Player"),new KeyValueBean(4, "Professional")));
            for(int i=0;i<gameBean.getNo_of_players();i++){
               //System.out.println();
                img_file = new FileInputStream(new File(workingDir, img_filename));
                img = new Image(img_file);
                rect = new Rectangle(width*0.0425,height*(0.435+(i*0.05)),width*0.09,height*0.06);
                rect.setFill(new ImagePattern(img));
                pane.getChildren().add(rect);
                outlines.add(rect);
                
                PlayerGameBean playergamebean = gameBean.getPlayer_data().get(i);
                CTextField_Data bplayer_id= new CTextField_Data(playergamebean.getPlayer_id(),13);
                bplayer_id.setPrefWidth(width*0.075);
                bplayer_ids.add(bplayer_id);
                bplayer_id.setLayoutX(width*0.05);
                bplayer_id.setLayoutY(height*(0.45+(i*0.05)));
                pane.getChildren().add(bplayer_id);
                
                img_file = new FileInputStream(new File(workingDir, img_filename));
                img = new Image(img_file);
                rect = new Rectangle(width*0.1325,height*(0.435+(i*0.05)),width*0.09,height*0.06);
                rect.setFill(new ImagePattern(img));
                pane.getChildren().add(rect);
                outlines.add(rect);
                
                CTextField_Data bplayer_name= new CTextField_Data(playergamebean.getPlayer_name(),13);
                bplayer_name.setPrefWidth(width*0.075);
                bplayer_names.add(bplayer_name);
                bplayer_name.setLayoutX(width*0.14);
                bplayer_name.setLayoutY(height*(0.45+(i*0.05)));
                pane.getChildren().add(bplayer_name);
                
                img_file = new FileInputStream(new File(workingDir, img_filename));
                img = new Image(img_file);
                rect = new Rectangle(width*0.241,height*(0.435+(i*0.05)),width*0.085,height*0.06);
                rect.setFill(new ImagePattern(img));
                pane.getChildren().add(rect);
                outlines.add(rect);
                
                CChoiceBox_KeyValue bplayer_skill= new CChoiceBox_KeyValue(skill_levels,playergamebean.getSkill_level(),playergamebean.getHand_usage());
                bplayer_skills.add(bplayer_skill);
                bplayer_skill.setLayoutX(width*0.25);
                bplayer_skill.setLayoutY(height*(0.45+(i*0.05)));
                pane.getChildren().add(bplayer_skill);
            }
            /*bplayer_id= new CTextField_Data("",16);
            bplayer_id.setLayoutX(width*0.225);
            bplayer_id.setLayoutY(height*0.455);
            pane.getChildren().add(bplayer_id);
            
            bplayer_name= new CTextField_Data("",13);
            bplayer_name.setLayoutX(width*0.225);
            bplayer_name.setLayoutY(height*0.530);
            pane.getChildren().add(bplayer_name);
            
            ArrayList<KeyValueBean> skill_levels = new ArrayList<KeyValueBean>(Arrays.asList(new KeyValueBean(1, "Beginner"),new KeyValueBean(2, "Amature"),new KeyValueBean(3, "Intermediate"),new KeyValueBean(4, "Club Player"),new KeyValueBean(5, "Professional")));
            bplayer_skill= new CChoiceBox_KeyValue(skill_levels,2);
            bplayer_skill.setLayoutX(width*0.225);
            bplayer_skill.setLayoutY(height*0.605);
            pane.getChildren().add(bplayer_skill);*/
            
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
