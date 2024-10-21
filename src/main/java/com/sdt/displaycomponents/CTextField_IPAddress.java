/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author srikanth.possim
 */
public class CTextField_IPAddress extends TextField{
    
    public CTextField_IPAddress(){
        super();
        
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                
                setText(TextFormating.doIPAddress(newValue));
            }
        
        });
    }
    
    public CTextField_IPAddress(String ipaddress){
        super();        
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                
                setText(TextFormating.doIPAddress(newValue));
            }
        
        });
        setText(ipaddress);
    }
    
    public char [] getIPChar(){
        return DisplayFormating.iPAddressStrToChar(getText());
    }
    public void setIPChar(char [] value){
        setText(DisplayFormating.charToIPAddress(value));
    }
    

}
