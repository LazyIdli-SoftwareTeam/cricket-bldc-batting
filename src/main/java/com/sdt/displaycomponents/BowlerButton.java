/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import java.io.File;
import java.io.FileInputStream;

import com.sdt.data.ModeDatBean;
import com.sdt.data.PlayerGameBean;
import com.sdt.screens.NextBall;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import zapcricketsimulator.ActTime;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class BowlerButton extends Group{
    int type =0;
    boolean active =false;

    public BowlerButton(int type , double width , double height){
        this.type=type;
        try {
            //double points[]={0,height*0.2,0,height,width,height,width,height*0.2,width*0.8,0,width*0.2,0,0,height*0.2};
            //final Polygon button = new Polygon(points);
            //final Rectangle button =new Rectangle(width, height)
            //ImagePattern imgptrn = new ImagePattern(btn_image);
            //button.setFill(Color.rgb(51, 106, 173));
            //button.setFill(imgptrn);
            //getChildren().add(button);
            /*Font f_type = Font.font("sans-serif", FontWeight.BOLD, FontPosture.REGULAR,height*0.3);
            Text text = new Text("BOWLER");
            text.setFont(f_type);
            getChildren().add(text);
            double t_width = text.getLayoutBounds().getWidth();
            text.setX((width/2)-(t_width/2));
            text.setY(height*0.4);

            Text text1 = new Text(width*0.47,height*0.8,type+"");
            text1.setFont(f_type);
            getChildren().add(text1);*/

            String workingDir = System.getProperty("user.dir");
            /*String overlay_path = "/Media/images/ButtonOverlay.png";
            FileInputStream overlay_img = new FileInputStream(new File(workingDir, overlay_path));
            final Image overlay_image = new Image(overlay_img);
            ImageView overlay = new ImageView(overlay_image);
            overlay.setFitWidth(width);
            overlay.setFitHeight(height);
            getChildren().add(overlay);*/

            String bowler_path = "/Media/images/Bowler"+type+".png";
            FileInputStream bowler_img = new FileInputStream(new File(workingDir, bowler_path));
            final Image bowler_image = new Image(bowler_img);
            String bowlerc_path = "/Media/images/Bowler"+type+"c.png";
            FileInputStream bowlerc_img = new FileInputStream(new File(workingDir, bowlerc_path));
            final Image bowlerc_image = new Image(bowlerc_img);
            ImageView bowler = new ImageView(bowler_image);
            bowler.setFitWidth(width);
            bowler.setFitHeight(height);
            getChildren().add(bowler);

            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.out.println("this is the button");
                    //setScaleX(1);
                    //setScaleY(1);
//                    bowler.setImage(bowler_image);
                    //seamin /offspin left wheel up right wheel full down
                    //leg spin /seam out left wheel down right wheel up
//                    if (type == 2 || type == 3) {
//                        System.out.println("Seem in");
//                        ActTime.seamIn();
//                    } else if (type == 4 ) {
////                        System.out.println("seem out");
//                        ActTime.seamOut();
//                    }
                    System.out.println("selection" + HandleEvents.gameBean.getBowler_selection());
                    ModeDatBean m = HandleEvents.generalSettings.getModeData();
//8217821484
                    //1729
                    int pos=HandleEvents.gameBean.getSeq_pos();
                    PlayerGameBean playerGameBean = HandleEvents.gameBean.getPlayer_data().get(pos);
                    int skill = playerGameBean.getSkill_level();

                    System.out.println("type" + (m.getBowling_speed()[type - 1][skill - 1]));
                    HandleEvents.machineDataBean.setSet_speed((m.getBowling_speed()[type - 1][skill - 1]));
                    HandleSerial.handleCom(HandleSerial.update_speed);
                    SpeedButton1.updateSpeed(HandleEvents.machineDataBean.getSet_speed());
//                    t_speed.setText(HandleEvents.machineDataBean.getSet_speed()+"");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (HandleEvents.gameBean.getBowler_selection() == 0) {
//                        ActTime.previousConfig(NextBall.type, type);
                        int prev_type = m.getBowling_type()[NextBall.type];
                        NextBall.type = type - 1;
//                        System.out.println("sending" + type);
                        int cov_type = m.getBowling_type()[type - 1];
                        NextBall.selectionManual = true;
//                        System.out.println(" previous " + prev_type + " conversion " + cov_type);
                        ActTime.previousConfig(cov_type, prev_type);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        ErrorAlert.alert("This is not possible in auto mode");
                    }
//                    HandleEvents.handleEvent(Variables.button_type_bowler, type);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    if(active){
                        //setScaleX(1.1);
                        //setScaleY(1.1);
                        bowler.setImage(bowlerc_image);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    //setScaleX(1.1);
                    //setScaleY(1.1);
                    bowler.setImage(bowlerc_image);
                    active=true;
                }
            });

            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //setScaleX(1);
                    //setScaleY(1);
                    bowler.setImage(bowler_image);
                    active=false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
