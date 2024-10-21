/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.data.TargetBean;
import com.sdt.displaycomponents.BowlerButton;
import com.sdt.displaycomponents.CSpinner;
import com.sdt.displaycomponents.CTextField_Data;
import com.sdt.displaycomponents.EmgButton;
import com.sdt.displaycomponents.GameButton;
import com.sdt.displaycomponents.KeypadView;
import com.sdt.displaycomponents.PanButton;
import com.sdt.displaycomponents.ResultButton;
import com.sdt.displaycomponents.RunSelector;
import com.sdt.displaycomponents.RunSelector1;
import com.sdt.displaycomponents.SettingsButton;
import com.sdt.displaycomponents.SpeedButton;
import com.sdt.displaycomponents.TextType1;
import com.sdt.displaycomponents.TextType2;
import com.sdt.displaycomponents.TextType3_0;
import com.sdt.displaycomponents.TiltButton1;
import com.sdt.displaycomponents.TiltPanButton;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class TargetScreen {
    public static TargetScreen this_obj = null;
    Pane pane = null;
    double width =0;
    double height = 0;
    public static TargetBean targetBean = new TargetBean();
    
    public static CTextField_Data bname= null;
    public static CTextField_Data bmob= null;
    public static CSpinner<Integer> runs=null;
    public static CSpinner<Integer> balls=null;
    public static CTextField_Data bscoredisplay=null;
    
    public TargetScreen(Pane pane, double width , double height){
        this_obj=this;
        this.pane = pane;
        this.width=width;
        this.height=height;
        //System.out.println(width+","+height);
        showScreen();
    }
    public static int scores [] = {1,2,3,4,6};
    RunSelector1 runselector[] = new RunSelector1[5];
    public void showScreen(){
        pane.getChildren().clear();
        String workingDir = System.getProperty("user.dir");
        try {
            String bg_path = "/Media/images/BG1.png";
            FileInputStream bg_img = new FileInputStream(new File(workingDir, bg_path));
            final Image bg_image = new Image(bg_img);
            ImagePattern imgptrn = new ImagePattern(bg_image);
            Rectangle bg = new Rectangle(0, 0, width, height);
            bg.setFill(imgptrn);        
            pane.getChildren().add(bg);
            
            double header_font_size = height*0.04;
            Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,header_font_size);
            TextType3_0 header = new TextType3_0((width/2)+20, header_font_size, "TARGET MODE", f_type1, pane,Color.WHITE);

            double regular_font_size =height*0.03;        
            Font f_type2 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,regular_font_size);
            TextType1 t_player_name = new TextType1(width*0.1, (height*0.07)+regular_font_size, "PLAYER NAME", f_type2, pane,Color.WHITE);
            bname= new CTextField_Data(targetBean.getName(),16);
            bname.setLayoutX(width*0.25);
            bname.setLayoutY(height*0.075);
            pane.getChildren().add(bname);
            TextType1 t_player_mob = new TextType1(width*0.1, (height*0.13)+regular_font_size, "PLAYER MOB", f_type2, pane,Color.WHITE);
            bmob= new CTextField_Data(targetBean.getMob(),13);
            bmob.setLayoutX(width*0.25);
            bmob.setLayoutY(height*0.135);
            pane.getChildren().add(bmob);

            TextType1 t_target_score = new TextType1(width*0.58, (height*0.07)+regular_font_size, "TARGET SCORE", f_type2, pane,Color.WHITE);
            runs= new CSpinner(0, 200, targetBean.getTarget_score(), 1);
            runs.setEditable(true);
            runs.setLayoutX(width*0.73);
            runs.setLayoutY(height*0.075);
            pane.getChildren().add(runs);
            TextType1 t_target_balls = new TextType1(width*0.58, (height*0.13)+regular_font_size, "TARGET BALLS", f_type2, pane,Color.WHITE);
            balls= new CSpinner(0, 120, targetBean.getTarget_bals(), 1);
            balls.setEditable(true);
            balls.setLayoutX(width*0.73);
            balls.setLayoutY(height*0.135);
            pane.getChildren().add(balls);

            SettingsButton settings = new SettingsButton(1);
            settings.setLayoutX((width*0.85));
            settings.setLayoutY(height*0.05);
            settings.setScaleX(width/1480);
            settings.setScaleY(height/780);
            pane.getChildren().add(settings);

            KeypadView keypad = new KeypadView(width*0.04, height*0.06);
            keypad.setLayoutX((width*0.95));
            keypad.setLayoutY(height*0.02);
            pane.getChildren().add(keypad);
            //TextType1 t_bowler_selection = new TextType1(width*0.1, (height*0.26)+regular_font_size, "BOWLER SELECTION", f_type2, pane,Color.WHITE);

            /*Rectangle bowler_rect = new Rectangle(width*0.08, height*0.30, width*0.40, height*0.25);
            bowler_rect.setFill(Color.rgb(247, 150, 70));
            bowler_rect.setArcWidth(50);
            bowler_rect.setArcHeight(50);
            pane.getChildren().add(bowler_rect);*/

            for(int i = 0 ; i < 8;i++){
                BowlerButton bowler = new BowlerButton(i+1, width*0.08, height*0.07);
                bowler.setLayoutX(width*0.05+((i%4)*(width*0.113)));
                bowler.setLayoutY(height*0.19+((i/4)*(height*0.12)));

                pane.getChildren().add(bowler);
            }       

            /*Rectangle game_rect = new Rectangle(width*0.5, height*0.30, width*0.40, height*0.25);
            game_rect.setFill(Color.rgb(247, 150, 70));
            game_rect.setArcWidth(50);
            game_rect.setArcHeight(50);
            pane.getChildren().add(game_rect);*/
            TextType3_0 t_play = new TextType3_0(width*0.59, (height*0.18)+regular_font_size, "PLAY", f_type2, pane,Color.WHITE);
            GameButton play_button = new GameButton(Variables.button_type_play);
            play_button.setLayoutX((width*0.59));
            play_button.setLayoutY(height*0.30);
            play_button.setScaleX(width/1200);
            play_button.setScaleY(height/640);        
            pane.getChildren().add(play_button);
            TextType3_0 t_pause = new TextType3_0(width*0.74, (height*0.18)+regular_font_size, "PAUSE", f_type2, pane,Color.WHITE);
            GameButton pause_button = new GameButton(Variables.button_type_pause);
            pause_button.setLayoutX((width*0.74));
            pause_button.setLayoutY(height*0.30);
            pause_button.setScaleX(width/1200);
            pause_button.setScaleY(height/640);        
            pane.getChildren().add(pause_button);
            TextType3_0 t_start = new TextType3_0(width*0.89, (height*0.18)+regular_font_size, "START", f_type2, pane,Color.WHITE);
            GameButton start_button = new GameButton(Variables.button_type_start);
            start_button.setLayoutX((width*0.89));
            start_button.setLayoutY(height*0.30);
            start_button.setScaleX(width/1200);
            start_button.setScaleY(height/640);        
            pane.getChildren().add(start_button);

            TextType1 t_scoredisplay = new TextType1(width*0.1, (height*0.45)+regular_font_size, "SCORE DISPLAY", f_type2, pane,Color.WHITE);
            bscoredisplay= new CTextField_Data(targetBean.getComments(),16);
            bscoredisplay.setPrefWidth(width*0.15);
            bscoredisplay.setLayoutX(width*0.24);
            bscoredisplay.setLayoutY(height*0.455);
            pane.getChildren().add(bscoredisplay);

            /*TextType1 t_comments = new TextType1(width*0.60, (height*0.45)+regular_font_size, "COMMENTS", f_type2, pane,Color.WHITE);
            CTextField_Data bcomments= new CTextField_Data(targetBean.getComments(),25);
            bcomments.setPrefWidth(width*0.20);
            bcomments.setLayoutX(width*0.70);
            bcomments.setLayoutY(height*0.455);
            pane.getChildren().add(bcomments);*/


            /*Rectangle score_rect = new Rectangle(width*0.08, height*0.78, width*0.40, height*0.25);
            score_rect.setFill(Color.rgb(247, 150, 70));
            score_rect.setArcWidth(50);
            score_rect.setArcHeight(50);
            pane.getChildren().add(score_rect);*/

            for(int i = 0 ; i < 8;i++){
                ResultButton rbutton = new ResultButton(Variables.button_type_result_norun+i, width*0.08, height*0.08);
                rbutton.setLayoutX(width*0.05+((i%4)*(width*0.11)));
                rbutton.setLayoutY(height*0.72+((i/4)*(height*0.1)));            
                pane.getChildren().add(rbutton);
            }   


            for(int i=0;i<5;i++){
                runselector[i]=new RunSelector1((width*0.077)+((height*0.028)*(i*6)), (height*0.59), height*0.03, height*0.07, scores[i]);
                pane.getChildren().add(runselector[i]);
            }

            //TextType1 t_machinecontrol = new TextType1(width*0.52, (height*0.74)+regular_font_size, "MACHINE CONTROL", f_type2, pane,Color.WHITE);
            /*Rectangle machine_rect = new Rectangle(width*0.5, height*0.78, width*0.40, height*0.25);
            machine_rect.setFill(Color.rgb(247, 150, 70));
            machine_rect.setArcWidth(50);
            machine_rect.setArcHeight(50);
            pane.getChildren().add(machine_rect);*/

            TextType1 t_spped = new TextType1(width*0.55, (height*0.71)+regular_font_size, "SPEED", f_type2, pane,Color.WHITE);
            //TextType1 t_tilt = new TextType1(width*0.64, (height*0.71)+regular_font_size, "TILT", f_type2, pane,Color.WHITE);
            //TextType1 t_pan = new TextType1(width*0.73, (height*0.71)+regular_font_size, "PAN", f_type2, pane,Color.WHITE);
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
            tiltbtn.setLayoutY(height*0.75);            
            pane.getChildren().add(tiltbtn);

            PanButton panbtn = new PanButton(width*0.06, height*0.16);
            panbtn.setLayoutX(width*0.725);
            panbtn.setLayoutY(height*0.75);            
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
