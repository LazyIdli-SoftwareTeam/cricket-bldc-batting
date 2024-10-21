/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;


import com.sdt.displaycomponents.HomeScreenButton1;
import com.sdt.displaycomponents.TextType3_0;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
public class HomeScreen1 {
    public static HomeScreen1 this_obj = null;
    public Pane pane = null;
    public double width =0;
    public double height = 0;
    public HomeScreen1(Pane pane, double width , double height){
        this_obj=this;
        this.pane = pane;
        this.width=width;
        this.height=height;
        displayOptions();
    }
    
    public void displayOptions(){
        pane.getChildren().clear();
        
        String workingDir = System.getProperty("user.dir");
        try {
            String bg_path = "/Media/images/BG.png";
            FileInputStream bg_img = new FileInputStream(new File(workingDir, bg_path));
            final Image bg_image = new Image(bg_img);
            ImagePattern imgptrn = new ImagePattern(bg_image);
            Rectangle bg = new Rectangle(0, 0, width, height);
            bg.setFill(imgptrn);
            pane.getChildren().add(bg);
            
            double header_font_size = height*0.04;
            Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,header_font_size);
            //TextType3_0 header = new TextType3_0((width/2)+20, header_font_size, "MODES", f_type1, pane,Color.WHITE);
            
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.02);
            
            String option11 = "/Media/images/sp1.png";
            FileInputStream img11 = new FileInputStream(new File(workingDir, option11));
            String option12 = "/Media/images/sp2.png";
            FileInputStream img12 = new FileInputStream(new File(workingDir, option12));
            final Image image11 = new Image(img11);
            final Image image12 = new Image(img12);
            HomeScreenButton1 sp_option = new HomeScreenButton1(image11, image12,(width/4),(height/2));
            sp_option.setLayoutX((width/4));
            sp_option.setLayoutY((height/4));
            pane.getChildren().add(sp_option);
            sp_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_sp;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_sp);
                    //new TargetScreen(pane,width,height);
                    new SinglePlayerScreen(pane,width,height);
                }
            });
            
            String option21 = "/Media/images/mp1.png";
            FileInputStream img21 = new FileInputStream(new File(workingDir, option21));
            final Image image21 = new Image(img21);
            String option22 = "/Media/images/mp2.png";
            FileInputStream img22 = new FileInputStream(new File(workingDir, option22));
            final Image image22 = new Image(img22);
            HomeScreenButton1 mp_option = new HomeScreenButton1(image21,image22,(width/4),(height/2));
            mp_option.setLayoutX((width/2));
            mp_option.setLayoutY((height/4));
            pane.getChildren().add(mp_option);
            mp_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_mp;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_mp);
                    //new PracticeScreen(pane,width,height);
                    new MultiPlayerScreen(pane,width,height);
                }
            });
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void changeScreen(int mode){
        if(mode==Variables.game_mode_sp){
            HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_sp);
            new SinglePlayerScreen(pane,width,height);
        }else if(mode==Variables.game_mode_mp){
            HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_mp);
            new MultiPlayerScreen(pane,width,height);
        }
    }
}
