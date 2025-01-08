/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zapcricketsimulator;


import com.sdt.screens.HomeScreen1;
import com.sdt.screens.MatchScreen;
import com.sdt.screens.PracticeScreen;
import com.sdt.screens.SixerChallengeScreen;
//import com.sdt.screens.StandardGame;
import com.sdt.screens.TargetScreen;
import com.sdt.serial.HandleSerial;
import com.sdt.system.ErrorAlert;
import com.sdt.tcp.TCPServerCom;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
/**
 *
 * @author possi
 */
public class ZaPCricketSimulator extends Application {
    public static String version="V2.15";
    public static ObservableList<Screen> screens = null;
    public static Stage primaryStage=null;
    public static int activepage=0;
    public static Rectangle2D bounds =null;
    //public static StandardGame standardgame=null;
    //public static int projector_index = 1;
    //public static int touchscreen_index = 0;
    @Override
    public void start(Stage primaryStage) {
        //new TCPServerCom();
        //HandleSerial.initSerial();
        HandleEvents.initData();
        new Periodictasks();
        this.primaryStage = primaryStage;
        screens = Screen.getScreens();
        bounds = ZaPCricketSimulator.screens.get(HandleEvents.generalSettings.getTouchscreen_index()).getVisualBounds();
        final Pane root = new Pane();
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        primaryStage.setX(bounds.getMinX());
        //primaryStage.setWidth(bounds.getWidth());
        //primaryStage.setMinWidth(bounds.getWidth());
        primaryStage.setMaximized(true);
        primaryStage.setY(bounds.getMinY());
        //primaryStage.setHeight(bounds.getHeight());
        primaryStage.setTitle("ZAP Cricket Simulation_"+version);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
           public void handle(WindowEvent we) {
               boolean continue_closing = ErrorAlert.confirm("Are You Sure you want to close");
               if(continue_closing){                    
                    HandleSerial.closing=true;
                    HandleSerial.handleCom(HandleSerial.power_on);
                    System.exit(0);
               }else 
                   we.consume();
           }
        });     
        new HomeScreen1(root, bounds.getWidth(), bounds.getHeight());
        //new TargetScreen(root, bounds.getWidth(), bounds.getHeight());
        //new PracticeScreen(root, bounds.getWidth(), bounds.getHeight());
        //new MatchScreen(root, bounds.getWidth(), bounds.getHeight());
        //new SixerChallengeScreen(root, bounds.getWidth(), bounds.getHeight());
        /*Button btn1 = new Button();
        btn1.setText("Standard Game");
        btn1.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                activepage = 1;
                standardgame=new StandardGame(root, bounds.getWidth(), bounds.getHeight());
            }
        });
        root.getChildren().add(btn1);
        btn1.setLayoutX((bounds.getWidth()/2)-100);
        btn1.setLayoutY((bounds.getHeight()/2)-100);
        
        Button btn2 = new Button();
        btn2.setText("Team Spirit");
        btn2.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        root.getChildren().add(btn2);
        btn2.setLayoutX((bounds.getWidth()/2)-(100));
        btn2.setLayoutY((bounds.getHeight()/2)-(50));
        
        Button btn3 = new Button();
        btn3.setText("Sixer Challenge");
        btn3.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        root.getChildren().add(btn3);
        btn3.setLayoutX((bounds.getWidth()/2)-(100));
        btn3.setLayoutY((bounds.getHeight()/2)+(0));
        
        Button btn4 = new Button();
        btn4.setText("Practise Game");
        btn4.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                
            }
        }); 
        root.getChildren().add(btn4);
        btn4.setLayoutX((bounds.getWidth()/2)-(100));
        btn4.setLayoutY((bounds.getHeight()/2)+(50));  */     
        
        
        
        primaryStage.show();
        new MediaStageNew(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public static Periodictasks periodic_obj = null;
    class Periodictasks implements Runnable{
        
        public Periodictasks(){
            periodic_obj=this;
            new Thread(this).start();
        }
        public boolean active = true;
        public void run(){
            int ctr=0;
            while(active){
                try {
                    Thread.sleep(100);
                    if(activepage==1){
                        ctr++;
                        if(ctr>=5){
                            ctr=0;
                            //standardgame.refreshComponents();
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }
    
}
