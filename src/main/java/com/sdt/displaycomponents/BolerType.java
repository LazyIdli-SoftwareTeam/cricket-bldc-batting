/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

//import com.sdt.screens.StandardGame;
import java.io.File;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import zapcricketsimulator.MediaStageNew;
//import zapcricketsimulator.MediaStage;

/**
 *
 * @author possi
 */
public class BolerType extends Group{
    static String types[]={"Fast","Med Fast","slow","Inswing","Outswing","Fast Med","Offspin","Legspin","Offbreak","Legbreak"};
    static int speeds[]={85,75,50,70,70,70,65,65,60,60};
    static int bolerids[]={0,1,1,1,1,2,2,3,2,3};
    Rectangle rect= null;
    static int bolerselected = -1;
    int type=-1;
    public BolerType(double x_pos , double y_pos , double width , double height ,int type){
        this.type = type;
        double x = x_pos-(width/2);
        double y = y_pos-(height/2);
        rect= new Rectangle(x, y, width, height);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        getChildren().add(rect);
        Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.1);
        TextType2 text = new TextType2(x_pos, y+(height*0.9), types[type], f_type1, this);
        final ImageView imv = new ImageView();
        imv.setX(x+(width*0.005));
        imv.setY(y);
        imv.setFitWidth(width*0.99);
        imv.setFitHeight(height*0.8);
        try {
            String workingDir = System.getProperty("user.dir");
            String boler_s = "/Media/bowler/images/1.png";
            if(type>=5)
                boler_s = "/Media/bowler/images/2.png";
            FileInputStream img = new FileInputStream(new File(workingDir, boler_s));
            final Image image = new Image(img);
            imv.setImage(image);
        } catch (Exception e) {
        }       
        getChildren().add(imv);
        
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(MediaStageNew.screen_status==3){
                    bolerselected = type;
                    rect.setStrokeWidth(5);
                    //MediaStage.matchdataBean.setBoler_type(type);
                    //MediaStage.matchdataBean.setBoler_id(bolerids[type]);
                    //StandardGame.speed.getValueFactory().setValue(speeds[type]);
                    MediaStageNew.this_obj.triggerEvent(MediaStageNew.bowler_selected); 
                }                
            }
        });
        /*setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                bolerselected = type;
                rect.setStrokeWidth(5);
            }
        });*/
        /*if(MediaStage.matchdataBean.getBoler_type()==type){
            bolerselected = type;
            rect.setStrokeWidth(5);
        }*/
    }
    
    public void refreshOption(){
        if(bolerselected!=type){
            rect.setStrokeWidth(1);
        }
    }
    
}
