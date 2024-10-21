/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.scene.control.ChoiceBox;

/**
 *
 * @author srikanth.possim
 */
public class CChoiceBox_EN_DE extends ChoiceBox<String>{
    
    public CChoiceBox_EN_DE(boolean status){
        getItems().addAll("Disable","Enable");
        if(status)
            getSelectionModel().select(1);
        else
            getSelectionModel().select(0);
    }
    
    public void selectValue(boolean status){
        if(status)
            getSelectionModel().select(1);
        else
            getSelectionModel().select(0);
    }
    
    public boolean getboolValue(){
        if(getSelectionModel().getSelectedIndex()==1)
            return true;
        return false;
    }
    
}
