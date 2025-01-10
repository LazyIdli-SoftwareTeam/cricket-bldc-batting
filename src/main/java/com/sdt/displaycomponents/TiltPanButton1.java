/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.logging.WriteJsonFile;
import com.sdt.serial.HandleSerial;
import java.io.File;
import java.io.FileInputStream;

import com.sdt.serial.USB_Com;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.json.simple.JSONObject;
import zapcricketsimulator.HandleEvents;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class TiltPanButton1 extends Group{
    boolean active1 = false;
    boolean active2 = false;
    boolean active3 = false;
    boolean active4 = false;
    int currentTilt = 0;
    int currentPan = 0;
    int leftMotor = 0;
    int rightMotor = 0;

    public static byte [] getCmd1(byte cmd){
        byte data[] = new byte[6];
        data[0]='#';
        data[1]=0x01;
        data[2]=cmd;
        data[3]=0x00;
        data[4]=USB_Com.getCRC(data, 3);
        data[5]='!';
        return data;
    }



    public static byte[] actCommands(String actCommand, String action) {
        try {
            byte byteval[] = new byte[9];
            //int val = 10;
            /**
             * This is bitwise
             * 0th bit --PAN
             * 1th bit -- TILT
             * 2nd bit -- left motor
             * 3rd bit -- right motor
             *
             * you can select multiple actuators simultaneously
             *
             */

            JSONObject data = WriteJsonFile.readFile();

            byteval[0] = Byte.parseByte(actCommand);
            int val = Integer.parseInt(data.get("pan").toString());
            System.out.println(val);
            byteval[1]=(byte)(val>>8);
            byteval[2]=(byte)(val&0xFF);
            //700-2400
            if (actCommand.equals("1")) {
                if (action.equals("left")) {
                    val -= 100;
                    data.put("pan", val);
                } else {
                    val += 100;
                    data.put("pan", val);
                }
            }


            val = Integer.parseInt(data.get("tilt").toString());
            if (actCommand.equals("2")) {
                if (action.equals("up")) {
                    val -= 100;
                    data.put("tilt", val);
                } else {
                    val += 100;
                    data.put("tilt", val);
                }
            }
            byteval[3]=(byte)(val>>8);
            byteval[4]=(byte)(val&0xFF);
            //100-2000/1800
            //7
            val = Integer.parseInt(data.get("rightMotor").toString());
            System.out.println(val);
            byteval[7]=(byte)(val>>8);
            byteval[8]=(byte)(val&0xFF);
            //100-2000/1800
            //8
            val = Integer.parseInt(data.get("leftMotor").toString());
            System.out.println(val);
            byteval[5]=(byte)(val>>8);
            byteval[6]=(byte)(val&0xFF);
            System.out.println("command written " +  val);
            USB_Com.WriteData(getCmd1((byte)0x82,byteval));
            WriteJsonFile.writeFile(data);

            //700-2400
            //700-3000 tested by nikhil

            //
            return byteval;
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
            return null;
        }
    }

    public static byte [] getCmd1(byte cmd,byte [] cmddata){
        byte data[] = new byte[6+cmddata.length];
        data[0]='#';
        data[1]=0x01;
        data[2]=cmd;
        data[3]=(byte)cmddata.length;
        for(int i=0;i<cmddata.length;i++)
            data[i+4] = cmddata[i];
        data[4+cmddata.length]=USB_Com.getCRC(data, 4+cmddata.length);
        data[5+cmddata.length]='!';
        return data;
    }

    public TiltPanButton1(double width , double height){
//        new Receive();
        try {
            String workingDir = System.getProperty("user.dir");
            /*String tiltpan_path = "/Media/images/tilt_pan.png";
            FileInputStream tiltpan_img = new FileInputStream(new File(workingDir, tiltpan_path));
            final Image tiltpan_image = new Image(tiltpan_img);
            ImageView tiltpan = new ImageView(tiltpan_image);
            tiltpan.setLayoutX(width*0.25);
            tiltpan.setLayoutY(height*0.25);
            tiltpan.setFitWidth(width*0.5);
            tiltpan.setFitHeight(width*0.5);
            getChildren().add(tiltpan);*/
            
            String tiltup_path = "/Media/images/tilt_up.png";
            FileInputStream tiltup_img = new FileInputStream(new File(workingDir, tiltup_path));
            final Image tiltup_image = new Image(tiltup_img);
            String tiltupc_path = "/Media/images/tilt_upc.png";
            FileInputStream tiltupc_img = new FileInputStream(new File(workingDir, tiltupc_path));
            final Image tiltupc_image = new Image(tiltupc_img);
            Circle tiltup = new Circle(width*0.5, height*0.25, width*0.1);
            getChildren().add(tiltup);
            tiltup.setFill(new ImagePattern(tiltup_image));
            /*ImageView tiltup = new ImageView(tiltup_image);
            tiltup.setLayoutX(width*0.25);
            tiltup.setLayoutY(height*0.025);
            tiltup.setFitWidth(width*0.5);
            tiltup.setFitHeight(width*0.5);
            getChildren().add(tiltup);*/
            tiltup.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    //tiltup.setScaleX(1);
                    //tiltup.setScaleY(1);
                    tiltup.setFill(new ImagePattern(tiltup_image));
//                    actCommands("2", "up");
                    HandleSerial.handleCom(HandleSerial.tilt_up);
                }
            });
            tiltup.setOnMouseReleased(new EventHandler<MouseEvent>() {

                public void handle(MouseEvent event) {

                    //button.setStroke(Color.BLACK);
                    if(active1){
                        //tiltup.setScaleX(1.1);
                        //tiltup.setScaleY(1.1);
                        tiltup.setFill(new ImagePattern(tiltupc_image));
                    }
                }
            });
            tiltup.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    tiltup.setStroke(Color.RED);
                    //tiltup.setScaleX(1.1);
                    //tiltup.setScaleY(1.1);
                    tiltup.setFill(new ImagePattern(tiltupc_image));
                    active1=true;
                }
            });

            tiltup.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    tiltup.setFill(new ImagePattern(tiltup_image));
                    active1=false;
                }
            });
            
            String tiltdown_path = "/Media/images/tilt_down.png";
            FileInputStream tiltdown_img = new FileInputStream(new File(workingDir, tiltdown_path));
            final Image tiltdown_image = new Image(tiltdown_img);
            String tiltdownc_path = "/Media/images/tilt_downc.png";
            FileInputStream tiltdownc_img = new FileInputStream(new File(workingDir, tiltdownc_path));
            final Image tiltdownc_image = new Image(tiltdownc_img);
            Circle tiltdown = new Circle(width*0.5, height*0.75, width*0.1);
            getChildren().add(tiltdown);
            tiltdown.setFill(new ImagePattern(tiltdown_image));
            /*ImageView tiltdown = new ImageView(tiltdown_image);
            tiltdown.setLayoutX(width*0.25);
            tiltdown.setLayoutY(height*0.725);
            tiltdown.setFitWidth(width*0.5);
            tiltdown.setFitHeight(width*0.5);
            getChildren().add(tiltdown);*/
            
            tiltdown.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    //tiltdown.setScaleX(1);
                    //tiltdown.setScaleY(1);
                    tiltdown.setFill(new ImagePattern(tiltdown_image));
//                    actCommands("2", "down");
                    HandleSerial.handleCom(HandleSerial.tilt_down);
                }
            });
            tiltdown.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.out.println("this happending2");

                    //button.setStroke(Color.BLACK);
                    if(active2){
                        //tiltdown.setScaleX(1.1);
                        //tiltdown.setScaleY(1.1);
                        tiltdown.setFill(new ImagePattern(tiltdownc_image));
                    }                    
                }
            });
            tiltdown.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.out.println("this happending3");
                    //button.setStroke(Color.BLACK);
                    //tiltdown.setScaleX(1.1);
                    //tiltdown.setScaleY(1.1);
                    tiltdown.setFill(new ImagePattern(tiltdownc_image));
                    active2=true;
                }
            });

            tiltdown.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //tiltdown.setScaleX(1);
                    //tiltdown.setScaleY(1);
                    tiltdown.setFill(new ImagePattern(tiltdown_image));
                    active2=false;
                }
            });
            
            String panleft_path = "/Media/images/pan_left.png";
            FileInputStream panleft_img = new FileInputStream(new File(workingDir, panleft_path));
            final Image panleft_image = new Image(panleft_img);
            String panleftc_path = "/Media/images/pan_leftc.png";
            FileInputStream panleftc_img = new FileInputStream(new File(workingDir, panleftc_path));
            final Image panleftc_image = new Image(panleftc_img);
            Circle panleft = new Circle(width*0.25, height*0.5, width*0.1);
            getChildren().add(panleft);
            panleft.setFill(new ImagePattern(panleft_image));
            /*ImageView panleft = new ImageView(panleft_image);
            panleft.setLayoutX(width*0.025);
            panleft.setLayoutY(height*0.25);
            panleft.setFitWidth(width*0.5);
            panleft.setFitHeight(width*0.5);
            getChildren().add(panleft);*/
            
            panleft.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    //panleft.setScaleX(1);
                    //panleft.setScaleY(1);
//                    actCommands("1", "left");
                    panleft.setFill(new ImagePattern(panleft_image));
                    HandleSerial.handleCom(HandleSerial.pan_left);
                }
            });
            panleft.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.out.println("this happending4");
                    //button.setStroke(Color.BLACK);
                    if(active3){
                        //panleft.setScaleX(1.1);
                        //panleft.setScaleY(1.1);
                        panleft.setFill(new ImagePattern(panleftc_image));
                    }
                }
            });
            
             panleft.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    //panleft.setScaleX(1.1);
                    //panleft.setScaleY(1.1);
                    panleft.setFill(new ImagePattern(panleftc_image));
                    active3=true;
                }
            });

            panleft.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //panleft.setScaleX(1);
                    //panleft.setScaleY(1);
                    panleft.setFill(new ImagePattern(panleft_image));
                    active3=false;
                }
            });
            
            String panright_path = "/Media/images/pan_right.png";
            FileInputStream panright_img = new FileInputStream(new File(workingDir, panright_path));
            final Image panright_image = new Image(panright_img);
            String panrightc_path = "/Media/images/pan_rightc.png";
            FileInputStream panrightc_img = new FileInputStream(new File(workingDir, panrightc_path));
            final Image panrightc_image = new Image(panrightc_img);
            Circle panright = new Circle(width*0.75, height*0.5, width*0.1);
            //panright.setStroke(Color.BLACK);
            getChildren().add(panright);
            panright.setFill(new ImagePattern(panright_image));
            /*ImageView panright = new ImageView(panright_image);
            panright.setLayoutX(width*0.725);
            panright.setLayoutY(height*0.25);
            panright.setFitWidth(width*0.5);
            panright.setFitHeight(width*0.5);
            getChildren().add(panright);*/
            
            panright.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {   
                    //panright.setScaleX(1);
                    //panright.setScaleY(1);
//                    actCommands("1", "right");
                    panright.setFill(new ImagePattern(panright_image));
                    HandleSerial.handleCom(HandleSerial.pan_right);
                }
            });
            panright.setOnMouseReleased(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(Color.BLACK);
                    if(active4){
                        //panright.setScaleX(1.1);
                        //panright.setScaleY(1.1);
                        panright.setFill(new ImagePattern(panrightc_image));
                    }
                }
            });
            panright.setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    System.out.println("this happending 5");
                    //button.setStroke(Color.BLACK);
                    //panright.setScaleX(1.1);
                    //panright.setScaleY(1.1);
                    panright.setFill(new ImagePattern(panrightc_image));
                    active4=true;
                }
            });

            panright.setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    //button.setStroke(null);
                    //panright.setScaleX(1);
                    //panright.setScaleY(1);
                    panright.setFill(new ImagePattern(panright_image));
                    active4=false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
