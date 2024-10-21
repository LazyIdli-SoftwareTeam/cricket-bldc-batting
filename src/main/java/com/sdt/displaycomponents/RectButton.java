/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
//import zapcricketsimulator.MediaStage;

/**
 *
 * @author possi
 */
public class RectButton extends Group{
    public static final int dec_offset = 10;
    static String button_text []= {"NO RUN","WIDE","NO BALL","NO BALL HEIGHT","BYE","BOWLED","STUMPED","LBW","HIT WICKET","RET HURT","C&B","WK CATCH","SLIP CATCH","DIVE CATCH","LONG CATCH","SHOT CATCH"};
    int type=0;
    Rectangle rect =null;
    public RectButton(double x_pos,double y_pos,double width,double height,int type ){
        this.type=type;
        double y = y_pos-(height/2);
        rect = new Rectangle(x_pos, y, width, height);
        getChildren().add(rect);
        if(type<5)
            rect.setFill(Color.LIGHTSEAGREEN);
        else
            rect.setFill(Color.PINK);
        rect.setArcWidth(width/10);
        rect.setArcHeight(height/10);
        Font f_type1 = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height/4);
        final TextType2 text = new TextType2(x_pos+(width/2), y_pos, button_text[type], f_type1, this);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch(type){
                    case 0:
                        break;
                }
                //MediaStageNew.matchdataBean.setBall_result(type+dec_offset);
                //MediaStage.matchdataBean.setBall_result_dir(0);
                //MediaStage.this_obj.triggerEvent(MediaStage.score_player);
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(5);
            }
        });
        
        /*this.setOnTouchPressed(new EventHandler<TouchEvent>() {
            public void handle(TouchEvent event) {
                switch(type){
                    case 0:
                        break;
                }
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(5);
            }
        });*/
    }
     public void refreshOption(){
         rect.setStroke(null);
         rect.setStrokeWidth(1);
     }
}
