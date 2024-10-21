/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.system;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.ButtonType;
import zapcricketsimulator.ZaPCricketSimulator;

/**
 *
 * @author srikanth.possim
 */
public class ErrorAlert {
    
    public static void alert(String Message){
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(ZaPCricketSimulator.primaryStage.getScene().getWindow());
        alert.setTitle("Error Dialog");
        //alert.setHeaderText("Look, an Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(Message);

        alert.showAndWait();
    }
    
    public static void info(String Message){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(ZaPCricketSimulator.primaryStage.getScene().getWindow());
        alert.setTitle("Information Dialog");
        //alert.setHeaderText("Look, an Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(Message);

        alert.showAndWait();
    }
    
    public static void warning(String Message){
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(ZaPCricketSimulator.primaryStage.getScene().getWindow());
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(Message);
        alert.showAndWait();
    }
    
    public static boolean confirm(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message,ButtonType.YES , ButtonType.NO);
        alert.initOwner(ZaPCricketSimulator.primaryStage.getScene().getWindow());
        alert.showAndWait();
        boolean result = false;
        if (alert.getResult() == ButtonType.YES) {
            //do stuff
            result=true;
        }
        return result;
    }
}
