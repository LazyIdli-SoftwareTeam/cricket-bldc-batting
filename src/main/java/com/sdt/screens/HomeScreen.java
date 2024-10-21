/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.screens;

import com.sdt.displaycomponents.HomeScreenButton;
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
public class HomeScreen {
    public static HomeScreen this_obj = null;
    public Pane pane = null;
    public double width =0;
    public double height = 0;
    public HomeScreen(Pane pane, double width , double height){
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
            TextType3_0 header = new TextType3_0((width/2)+20, header_font_size, "MODES", f_type1, pane,Color.WHITE);
            
            f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.02);
            
            String option1 = "/Media/images/option(1).png";
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            final Image image1 = new Image(img1);
            HomeScreenButton target_option = new HomeScreenButton(image1, "Target",Color.rgb(117, 84, 157));
            target_option.setLayoutX((width/2)-275);
            target_option.setLayoutY((height/2)-275);
            pane.getChildren().add(target_option);
            target_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_target;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_target);
                    new TargetScreen(pane,width,height);
                }
            });
            
            String option2 = "/Media/images/option(2).png";
            FileInputStream img2 = new FileInputStream(new File(workingDir, option2));
            final Image image2 = new Image(img2);
            HomeScreenButton practice_option = new HomeScreenButton(image2, "Practice",Color.rgb(73, 70, 165));
            practice_option.setLayoutX((width/2)-275);
            practice_option.setLayoutY((height/2)-125);
            pane.getChildren().add(practice_option);
            practice_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_practice;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_practice);
                    new PracticeScreen(pane,width,height);
                }
            });
            
            String option3 = "/Media/images/option(3).png";
            FileInputStream img3 = new FileInputStream(new File(workingDir, option3));
            final Image image3 = new Image(img3);
            HomeScreenButton match_option = new HomeScreenButton(image3, "Match",Color.rgb(60, 104, 178));
            match_option.setLayoutX((width/2)-275);
            match_option.setLayoutY((height/2)+25);
            pane.getChildren().add(match_option);
            match_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_match;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_match);
                    new MatchScreen(pane,width,height);
                }
            });
            
            String option4 = "/Media/images/option(5).png";
            FileInputStream img4 = new FileInputStream(new File(workingDir, option4));
            final Image image4 = new Image(img4);
            HomeScreenButton sixer_option = new HomeScreenButton(image4, "Sixer Challenge",Color.rgb(48, 156, 185));
            sixer_option.setLayoutX((width/2)-275);
            sixer_option.setLayoutY((height/2)+175);
            pane.getChildren().add(sixer_option);
            sixer_option.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //HandleEvents.game_mode = Variables.game_mode_match;
                    HandleEvents.handleEvent(Variables.mode_sellection, Variables.game_mode_sixer);
                    new SixerChallengeScreen(pane,width,height);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
