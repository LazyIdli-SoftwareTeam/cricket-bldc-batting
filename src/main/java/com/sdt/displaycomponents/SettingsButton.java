/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.serial.HandleSerial;
import com.sdt.serial.USB_Com;
import com.sdt.xml.HandleFile;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.Pair;
import zapcricketsimulator.HandleEvents;
import com.sdt.data.KeyValueBean;
import com.sdt.screens.TabletCom;
import java.util.Arrays;
import javafx.scene.control.ScrollPane;
import zapcricketsimulator.ZaPCricketSimulator;

/**
 *
 * @author possi
 */
public class SettingsButton extends Group{
    int type=0;
    public SettingsButton(int type ){
        try {
            this.type=type;
            final Rectangle rect = new Rectangle(80, 90);
            String workingDir = System.getProperty("user.dir");
            String option1 = "/Media/images/settings.png";
            FileInputStream img1 = new FileInputStream(new File(workingDir, option1));
            final Image image = new Image(img1);
            rect.setFill(new ImagePattern(image));
            getChildren().add(rect);
            setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {                    
                    openSettings();
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setStroke(Color.BLACK);
                }
            });
            
            setOnMouseExited(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    rect.setStroke(null);
                }
            });
        } catch (Exception e) {
        }
        
    }
    public static Dialog<Pair<String, String>> dialog = null;
    public static TabPane tabPane =null;
    String tab_name[]= {"General","Target Mode","Practice Mode","Match Mode","Sixer Challenge"};
    public TextField bowler_path[] =new TextField[8];
    CSpinner<Integer> b_relese_pos[]=new CSpinner[8];
    CSpinner<Integer> b_speed[][]=new CSpinner[8][4];
    CChoiceBox_KeyValue b_bowling_type[]=new CChoiceBox_KeyValue[8];
    Button con_btn=null;
    
    public void openSettings(){
        dialog = new Dialog<>();  
        dialog.initOwner(ZaPCricketSimulator.primaryStage.getScene().getWindow());
        //double width = ZaPCricketSimulator.bounds.getWidth();
        //double height = ZaPCricketSimulator.bounds.getHeight();
        //Window window = dialog.getDialogPane().getScene().getWindow();
        //window.setX(ZaPCricketSimulator.bounds.getMinX()+500-dialog.getWidth());
        //window.setY(ZaPCricketSimulator.bounds.getMinY()+500-dialog.getHeight());
        dialog.setTitle("Settings");
        dialog.setHeaderText("Please Update Below Settings");
        final ButtonType ButtonType1 = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType1, ButtonType.CANCEL);
        
        tabPane = new TabPane();
        tabPane.setRotateGraphic(false);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setSide(Side.TOP);       
        
        GridPane grid[] = new GridPane[5];
        final Tab tab[] = new Tab[5];
        
        grid[0] = new GridPane();
        grid[0].setHgap(10);
        grid[0].setVgap(10);
        grid[0].setPadding(new Insets(20, 150, 10, 10));
        int row1 = 1;
        
        grid[0].add(new CLabel("Enable Keypad"), 1, row1);
        final CChoiceBox_EN_DE keypad = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isKeypad_enable());
        grid[0].add(keypad, 2, row1++);
        
        grid[0].add(new CLabel("Test Mode"), 1, row1);
        final CChoiceBox_EN_DE testmode = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isTest_mode());
        grid[0].add(testmode, 2, row1++);
            
        ArrayList<KeyValueBean> video_formats = new ArrayList(Arrays.asList(new KeyValueBean(0, "libx264"),new KeyValueBean(1, "libx265"),new KeyValueBean(2, "libxvid"),new KeyValueBean(3, "jpeg2000"),new KeyValueBean(4, "mpeg4"),new KeyValueBean(5, "libravle"),new KeyValueBean(6, "libkvazaar"),new KeyValueBean(7, "libtheora")));
        grid[0].add(new CLabel("Enable Replay"), 1, row1);
        final CChoiceBox_EN_DE replay = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isReplay_enable());
        final CChoiceBox_KeyValue replay_format = new CChoiceBox_KeyValue(video_formats,HandleEvents.generalSettings.getFormat());
        grid[0].add(replay, 2, row1);
        grid[0].add(replay_format, 3, row1++);
        
        grid[0].add(new CLabel("Camera User"), 1, row1);
        final TextField camera_userid = new TextField(HandleEvents.generalSettings.getCamera_user_id());
        grid[0].add(camera_userid, 2, row1++);
        grid[0].add(new CLabel("Camera Password"), 1, row1);
        final TextField camera_password = new TextField(HandleEvents.generalSettings.getCamera_password());
        grid[0].add(camera_password, 2, row1++);
        grid[0].add(new CLabel("Camera IP Address"), 1, row1);
        final CTextField_IPAddress camera_ip = new CTextField_IPAddress(HandleEvents.generalSettings.getCamera_ip());
        grid[0].add(camera_ip, 2, row1++);
        grid[0].add(new CLabel("Camera Port"), 1, row1);
        final CSpinner<Integer> camera_port = new CSpinner<>(1, 20000, HandleEvents.generalSettings.getCamera_port(), 1);
        grid[0].add(camera_port, 2, row1++);
        
        grid[0].add(new CLabel("Replay Duration"), 1, row1);
        final CSpinner<Integer> replay_duration = new CSpinner<>(500, 20000, HandleEvents.generalSettings.getReplayduration_ms(), 1);
        grid[0].add(replay_duration, 2, row1);
        Button testvideo = new Button("Test");
        testvideo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) 
            {                  
                 try {
                    String videoMRL = "rtsp://"+camera_userid.getText()+":"+camera_password.getText()+"@"+camera_ip.getText()+":"+camera_port.getValue()+"//Streaming/Channels/1";
                    int recordTime = replay_duration.getValue()/1000;
                    String videoPath = System.getProperty("user.dir") + "/test.mp4";
                    String command = "C:/ffmpeg/bin/ffmpeg -y -i " + "\"" + videoMRL + "\"" +" -t " + recordTime + " -s hd480 -acodec copy -vcodec " +replay_format.getValue().getValue()+ " \"" + videoPath + "\"";
                    Process p = Runtime.getRuntime().exec(command);                    
                } catch (Exception ex) {
                }
            } 
        });
        grid[0].add(testvideo, 3, row1++);
        
        grid[0].add(new CLabel("Min File Size"), 1, row1);
        final CSpinner<Integer> t_min_file_size = new CSpinner<>(10000, 10000000, HandleEvents.generalSettings.getMin_file_size(), 1);
        grid[0].add(t_min_file_size, 2, row1++); 
        
        grid[0].add(new CLabel("Replay Wait Time"), 1, row1);
        final CSpinner<Integer> t_max_replay_wait = new CSpinner<>(100, 10000000, HandleEvents.generalSettings.getMax_replay_delay(), 1);
        grid[0].add(t_max_replay_wait, 2, row1++);
        
        grid[0].add(new CLabel("Sixer Video"), 1, row1);
        final CChoiceBox_EN_DE sixer_video = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isPlay_sixer_video());
        grid[0].add(sixer_video, 2, row1++);
        
        grid[0].add(new CLabel("Wicket Video"), 1, row1);
        final CChoiceBox_EN_DE wicket_video = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isPlay_wicket_video());
        grid[0].add(wicket_video, 2, row1++);
        
        grid[0].add(new CLabel("Cloud Reporting"), 1, row1);
        final CChoiceBox_EN_DE cloud_loging = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isCloud_reporting());
        grid[0].add(cloud_loging, 2, row1++);
        
        //ArrayList<KeyValueBean> en_de = new ArrayList(Arrays.asList(new KeyValueBean(false, "Disable"),new KeyValueBean(true, "Enable")));
        grid[0].add(new CLabel("Skill Test"), 1, row1);
        final CChoiceBox_EN_DE t_skill_test = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isSkill_test());
        grid[0].add(t_skill_test, 2, row1++);
        
        grid[0].add(new CLabel("Skill Test Vlaue"), 1, row1);
        final CSpinner<Integer> skilltest_value = new CSpinner<>(0, 30, HandleEvents.generalSettings.getSkill_test_value(), 1);
        grid[0].add(skilltest_value, 2, row1++);
        
        /*grid[0].add(new CLabel("Default Bowler"), 1, row1);
        final TextField defaultbowler = new TextField(HandleEvents.generalSettings.getDefault_bowler_path());
        defaultbowler.setEditable(false);
        defaultbowler.setPrefWidth(400);
        grid[0].add(defaultbowler, 2, row1);
        Button selectbowler = new Button("Select");
        selectbowler.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) 
            { 
                 File folder = chosefolder(HandleEvents.generalSettings.getDefault_bowler_path());
                 if(folder!=null && folder.isDirectory())
                     defaultbowler.setText(folder.getAbsolutePath());
            } 
        });
        grid[0].add(selectbowler, 3, row1++);*/        
        /*final CSpinner<Integer> d_relese_pos2 = new CSpinner<>(50, 20000, HandleEvents.generalSettings.getDefault_bowler_relese_pos2(), 1);
        grid[0].add(d_relese_pos2, 3, row1++); */ 
        
        grid[0].add(new CLabel("Default Bowler"), 1, row1);
        final CSpinner<Integer> d_bowler = new CSpinner<>(1, 8, HandleEvents.generalSettings.getDefault_bowler(), 1);
        grid[0].add(d_bowler, 2, row1++);    
        
        grid[0].add(new CLabel("Default Speed"), 1, row1);
        final CSpinner<Integer> d_speed = new CSpinner<>(35, 100, HandleEvents.generalSettings.getDefault_speed(), 1);
        grid[0].add(d_speed, 2, row1++);    
        
        grid[0].add(new CLabel("Init Time"), 1, row1);
        final CSpinner<Integer> d_init_time = new CSpinner<>(5, 200, HandleEvents.generalSettings.getPlayer_init_time(), 1);
        grid[0].add(d_init_time, 2, row1++);    
        
        grid[0].add(new CLabel("Touch Scrren Pos"), 1, row1);
        final CSpinner<Integer> t_screen_pos = new CSpinner<>(0, 1, HandleEvents.generalSettings.getTouchscreen_index(), 1);
        grid[0].add(t_screen_pos, 2, row1++);      
        grid[0].add(new CLabel("Projector Scrren Pos"), 1, row1);
        final CSpinner<Integer> p_screen_pos = new CSpinner<>(0, 1, HandleEvents.generalSettings.getProjector_screen_index(), 1);
        grid[0].add(p_screen_pos, 2, row1++); 
        
        grid[0].add(new CLabel("Com Port"), 1, row1);
        ArrayList<String> comlist = new ArrayList<>();
        for(int i=0;i<USB_Com.getPortList().length;i++){
            comlist.add(USB_Com.getPortList()[i]);
        }
        final ChoiceBox<String> com_port = new ChoiceBox<>();
        com_port.getItems().addAll(comlist);
        com_port.getSelectionModel().select(HandleEvents.generalSettings.getCom_port());
        grid[0].add(com_port, 2, row1); 
        String conn_status = "Connect";
        if(USB_Com.status)
            conn_status = "Disconnect";
        con_btn = new Button(conn_status);
        grid[0].add(con_btn, 3, row1++); 
        con_btn.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(!USB_Com.status){

                    if(com_port.getValue()!=null)
                        HandleSerial.initSerial(com_port.getValue());
                }else{
                    USB_Com.disconnect();
                }
                
                if(USB_Com.status)
                    con_btn.setText("Disconnect");
                else
                    con_btn.setText("Connect");  
            }
        });
        
        grid[0].add(new CLabel("Baud Rate"), 1, row1);
        final CSpinner<Integer> t_baudrate = new CSpinner<>(9600, 256000, HandleEvents.generalSettings.getBaudrate(), 1);
        grid[0].add(t_baudrate, 2, row1++); 
        
        grid[0].add(new CLabel("Match -Ve Scoring"), 1, row1);
        final CSpinner<Integer> t_negativescoring = new CSpinner<>(0, 100, HandleEvents.generalSettings.getMatch_negative_scoring(), 1);
        grid[0].add(t_negativescoring, 2, row1++); 
        
        grid[0].add(new CLabel("Tablet Mode"), 1, row1);
        final CChoiceBox_EN_DE tablet = new CChoiceBox_EN_DE(HandleEvents.generalSettings.isTablet_mode_enable());
        grid[0].add(tablet, 2, row1++);
        
        grid[0].add(new CLabel("Tablet Port"), 1, row1);
        final CSpinner<Integer> t_tcp_port = new CSpinner<>(1, 65535, HandleEvents.generalSettings.getTcp_port(), 1);
        grid[0].add(t_tcp_port, 2, row1++); 
        
        ArrayList<KeyValueBean> autoscoring_sel = new ArrayList(Arrays.asList(new KeyValueBean(0,"Disable"),new KeyValueBean(1,"Enable"),new KeyValueBean(2,"Manual")));
        grid[0].add(new CLabel("Auto Scoring"), 1, row1);
        final CChoiceBox_KeyValue autoscoring = new CChoiceBox_KeyValue(autoscoring_sel,HandleEvents.generalSettings.getAuto_scoring_enable());
        grid[0].add(autoscoring, 2, row1++);
        
        grid[0].add(new CLabel("Auto Scoring"), 1, row1);
        Button auto_scoring_config  = new Button("Config");
        grid[0].add(auto_scoring_config, 2, row1++);
        auto_scoring_config.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                dialog.getDialogPane().setContent(AutoScoringSettings.changeSettings());
            }
        });
          
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(800, 500);
        sp.setContent(grid[0]);
        tab[0] = new Tab(tab_name[0]);
        tab[0].setContent(sp);
        tabPane.getTabs().add(tab[0]);
        tab[0].setOnSelectionChanged(new EventHandler<Event>(){
            public void handle(Event event) {                    
                Tab tab = (Tab)event.getSource();
            }
        });
        if(true){        
            ScrollPane sp1 = new ScrollPane();
            sp1.setPrefSize(900, 500);
            grid[0] = new GridPane();
            sp1.setContent(grid[0]);
            grid[0].setHgap(10);
            grid[0].setVgap(10);
            grid[0].setPadding(new Insets(20, 150, 10, 10));
            int row = 0;
            ArrayList<KeyValueBean> Bowling_Types = new ArrayList(Arrays.asList(new KeyValueBean(3, "Speed"),new KeyValueBean(6, "Off_Spin"),new KeyValueBean(7, "Leg_Spin"),new KeyValueBean(8, "Seem_In"),new KeyValueBean(9, "Seem_Out"),new KeyValueBean(12, "Seem_Magic"),new KeyValueBean(13, "Spin_Magic"),new KeyValueBean(14, "Bowler_Magic")));
            for(int j=0;j<8;j++){
                grid[0].add(new CLabel("Bowler "+(j+1)+" Path"), 1, row);
                bowler_path[j]= new TextField(HandleEvents.generalSettings.getModeData().getBowler_path()[j]);
                bowler_path[j].setEditable(false);
                bowler_path[j].setPrefWidth(400);
                grid[0].add(bowler_path[j], 2, row,3,1);
                Button bowlserbutton = new Button("Select");
                final int bowlerpos = j;
                bowlserbutton.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e) 
                    { 
                         File folder = chosefolder(HandleEvents.generalSettings.getModeData().getBowler_path()[bowlerpos]);
                         if(folder!=null && folder.isDirectory())
                             bowler_path[bowlerpos].setText(folder.getAbsolutePath());
                    } 
                });
                grid[0].add(bowlserbutton, 5, row++);

                grid[0].add(new CLabel("Ball Relese Pos"), 1, row);
                b_relese_pos[j] = new CSpinner<>(50, 20000, HandleEvents.generalSettings.getModeData().getTrigger_interval()[j], 1);
                grid[0].add(b_relese_pos[j], 2, row++);  
                
                grid[0].add(new CLabel("Ball Speed(B,I,C,P)"), 1, row);
                b_speed[j][0] = new CSpinner<Integer>(30, 180, HandleEvents.generalSettings.getModeData().getBowling_speed()[j][0], 1);
                grid[0].add(b_speed[j][0], 2, row);
                b_speed[j][1] = new CSpinner<Integer>(30, 180, HandleEvents.generalSettings.getModeData().getBowling_speed()[j][1], 1);
                grid[0].add(b_speed[j][1], 3, row);
                b_speed[j][2] = new CSpinner<Integer>(30, 180, HandleEvents.generalSettings.getModeData().getBowling_speed()[j][2], 1);
                grid[0].add(b_speed[j][2], 4, row);
                b_speed[j][3] = new CSpinner<Integer>(30, 180, HandleEvents.generalSettings.getModeData().getBowling_speed()[j][3], 1);
                grid[0].add(b_speed[j][3], 5, row++);
                
                grid[0].add(new CLabel("Bowling Type"), 1, row);
                b_bowling_type[j] = new CChoiceBox_KeyValue(Bowling_Types,HandleEvents.generalSettings.getModeData().getBowling_type()[j]);
                grid[0].add(b_bowling_type[j], 2, row++);  
            }            
            tab[0] = new Tab("Bowler Settings");
            tab[0].setContent(sp1);
            tabPane.getTabs().add(tab[0]);
        }
        
        Node Button1 = dialog.getDialogPane().lookupButton(ButtonType1);
        dialog.getDialogPane().setContent(tabPane);
        dialog.setResultConverter(new Callback<ButtonType, Pair<String,String>>() {
            @Override
            public Pair<String,String> call(ButtonType b) {

                if (b == ButtonType1) {
                    return new Pair<String,String>("", "");
                }
                return null;
            }
        });
                
         Optional<Pair<String, String>> result = dialog.showAndWait();
        if(result.isPresent()){
            HandleEvents.generalSettings.setKeypad_enable(keypad.getboolValue());
            if(!HandleEvents.generalSettings.isTablet_mode_enable() && tablet.getboolValue()){
                new TabletCom();
            }else if(HandleEvents.generalSettings.isTablet_mode_enable() && !tablet.getboolValue()){
                TabletCom.closeCom();
            }
            HandleEvents.generalSettings.setTablet_mode_enable(tablet.getboolValue());
            HandleEvents.generalSettings.setTcp_port(t_tcp_port.getValue());
            HandleEvents.generalSettings.setTest_mode(testmode.getboolValue());
            HandleEvents.generalSettings.setReplay_enable(replay.getboolValue());
            HandleEvents.generalSettings.setFormat(replay_format.getValue().getValue());
            HandleEvents.generalSettings.setCamera_user_id(camera_userid.getText());
            HandleEvents.generalSettings.setCamera_password(camera_password.getText());
            HandleEvents.generalSettings.setCamera_ip(camera_ip.getText());
            HandleEvents.generalSettings.setCamera_port(camera_port.getValue());
            HandleEvents.generalSettings.setReplayduration_ms(replay_duration.getValue());
            HandleEvents.generalSettings.setMin_file_size(t_min_file_size.getValue());
            HandleEvents.generalSettings.setMax_replay_delay(t_max_replay_wait.getValue());
            HandleEvents.generalSettings.setSkill_test(t_skill_test.getboolValue());
            HandleEvents.generalSettings.setSkill_test_value(skilltest_value.getValue());
            /*if(t_skill_test.getValue().getKey()!=HandleEvents.generalSettings.getSkill_test()){
                HandleEvents.generalSettings.setSkill_test(t_skill_test.getValue().getKey());
                HandleSerial.handleCom(HandleSerial.skill_test);
            }*/
            HandleEvents.generalSettings.setPlay_sixer_video(sixer_video.getboolValue());
            HandleEvents.generalSettings.setPlay_wicket_video(wicket_video.getboolValue());
            HandleEvents.generalSettings.setCloud_reporting(cloud_loging.getboolValue());
            //HandleEvents.generalSettings.setBall_type(t_ball_type.getValue().getKey());
            HandleEvents.generalSettings.setDefault_speed(d_speed.getValue());
            HandleEvents.generalSettings.setDefault_bowler(d_bowler.getValue());
            HandleEvents.generalSettings.setPlayer_init_time(d_init_time.getValue());
            //HandleEvents.generalSettings.setDefault_bowler_relese_pos(d_relese_pos.getValue());
            HandleEvents.generalSettings.setTouchscreen_index(t_screen_pos.getValue());
            HandleEvents.generalSettings.setProjector_screen_index(p_screen_pos.getValue());
            HandleEvents.generalSettings.setMatch_negative_scoring(t_negativescoring.getValue());
            HandleEvents.generalSettings.setBaudrate(t_baudrate.getValue());
            if(com_port.getValue()!=null)
                HandleEvents.generalSettings.setCom_port(com_port.getValue());
            for(int j=0;j<8;j++){
                HandleEvents.generalSettings.getModeData().getBowler_path()[j]=bowler_path[j].getText();
                HandleEvents.generalSettings.getModeData().getTrigger_interval()[j]=b_relese_pos[j].getValue();
                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][0]=b_speed[j][0].getValue();
                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][1]=b_speed[j][1].getValue();
                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][2]=b_speed[j][2].getValue();
                HandleEvents.generalSettings.getModeData().getBowling_speed()[j][3]=b_speed[j][3].getValue();
                HandleEvents.generalSettings.getModeData().getBowling_type()[j]=b_bowling_type[j].getValue().getKey();
            }
            HandleEvents.generalSettings.setAuto_scoring_enable(autoscoring.getValue().getKey());
            if(AutoScoringSettings.tabpane!=null){
                try {
                     HandleEvents.generalSettings.getAutoScotringBean().setSerial_no(AutoScoringSettings.serialno.getText());
                     if(isNumeric(AutoScoringSettings.portno.getText()))
                         HandleEvents.generalSettings.getAutoScotringBean().setPort_no(Integer.parseInt(AutoScoringSettings.portno.getText()));
                     HandleEvents.generalSettings.getAutoScotringBean().setDi_count(AutoScoringSettings.input_count.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setWicket_input(AutoScoringSettings.stumps.getboolValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setWicket_sensor(AutoScoringSettings.stumps_val.getValue().getKey());
                     HandleEvents.generalSettings.getAutoScotringBean().setSolenoid_input(AutoScoringSettings.solenoid.getboolValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSolenoid_sensor(AutoScoringSettings.solenoid_val.getValue().getKey());
                     HandleEvents.generalSettings.getAutoScotringBean().setBall_sensor_1(AutoScoringSettings.ball_sensor_1.getValue().getKey());
                     HandleEvents.generalSettings.getAutoScotringBean().setBall_sensor_2(AutoScoringSettings.ball_sensor_2.getValue().getKey());
                     HandleEvents.generalSettings.getAutoScotringBean().setMax_prev_pole_time_factor(AutoScoringSettings.speed_factor.getValue());
                     if(AutoScoringSettings.com_port.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setCom_port(AutoScoringSettings.com_port.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setBaudrate(AutoScoringSettings.t_baudrate.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSix_output(AutoScoringSettings.t_six_output.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setFour_output(AutoScoringSettings.t_four_output.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setTwo_output(AutoScoringSettings.t_two_output.getValue());
                     
                     if(AutoScoringSettings.di1.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi1_pole(AutoScoringSettings.di1.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi1_pole(0);
                     if(AutoScoringSettings.di2.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi2_pole(AutoScoringSettings.di2.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi2_pole(0);
                     if(AutoScoringSettings.di3.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi3_pole(AutoScoringSettings.di3.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi3_pole(0);
                     if(AutoScoringSettings.di4.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi4_pole(AutoScoringSettings.di4.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi4_pole(0);
                     if(AutoScoringSettings.di5.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi5_pole(AutoScoringSettings.di5.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi5_pole(0);
                     if(AutoScoringSettings.di6.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi6_pole(AutoScoringSettings.di6.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi6_pole(0);
                     if(AutoScoringSettings.di7.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi7_pole(AutoScoringSettings.di7.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi7_pole(0);
                     if(AutoScoringSettings.di8.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi8_pole(AutoScoringSettings.di8.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi8_pole(0);
                     if(AutoScoringSettings.di9.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi9_pole(AutoScoringSettings.di9.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi9_pole(0);
                     if(AutoScoringSettings.di10.getValue()!=null)
                        HandleEvents.generalSettings.getAutoScotringBean().setDi10_pole(AutoScoringSettings.di10.getValue().getKey());
                     else
                         HandleEvents.generalSettings.getAutoScotringBean().setDi10_pole(0);
                     
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_1(AutoScoringSettings.level_1_time_1.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_2(AutoScoringSettings.level_1_time_2.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_score_time_3(AutoScoringSettings.level_1_time_3.getValue());
                     
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_1(AutoScoringSettings.level_2_time_1.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_2(AutoScoringSettings.level_2_time_2.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_score_time_3(AutoScoringSettings.level_2_time_3.getValue());
                     
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_1(AutoScoringSettings.level_3_time_1.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_2(AutoScoringSettings.level_3_time_2.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_score_time_3(AutoScoringSettings.level_3_time_3.getValue());
                     
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_1(AutoScoringSettings.level_4_time_1.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_2(AutoScoringSettings.level_4_time_2.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_score_time_3(AutoScoringSettings.level_4_time_3.getValue());
                     
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_1_wicket_time(AutoScoringSettings.level_1_stumptime.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_2_wicket_time(AutoScoringSettings.level_2_stumptime.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_3_wicket_time(AutoScoringSettings.level_3_stumptime.getValue());
                     HandleEvents.generalSettings.getAutoScotringBean().setSkill_4_wicket_time(AutoScoringSettings.level_4_stumptime.getValue());
                        
                } catch (Exception e) {
                }
            }
            HandleFile.saveData();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    
                }
            });
        }
        
    }
    
    public static boolean isNumeric(String str){
        boolean numearic = false;
        try {
            Integer.parseInt(str);
            numearic = true;
        } catch (Exception e) {
        }        
        return numearic;
    }
    public static String workingDir = null;
    public static String parentDir = null;
    public File chosefolder(String currentval){
        if(workingDir==null)
            workingDir = System.getProperty("user.dir");
        /*if(currentval!=null && currentval.length()!=0){
            try {
                File dir = new File(currentval);
                workingDir=dir.getParent();
            } catch (Exception e) {
                e.printStackTrace();
            }            
        }*/
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if(parentDir != null){
            directoryChooser.setInitialDirectory(new File(parentDir));
        }else {
            directoryChooser.setInitialDirectory(new File(workingDir));
        }
            
        File selectedDirectory = directoryChooser.showDialog(ZaPCricketSimulator.primaryStage);
        if(selectedDirectory!=null && selectedDirectory.isDirectory()){
            parentDir = selectedDirectory.getParent();
        }
        return selectedDirectory;
    }
}
