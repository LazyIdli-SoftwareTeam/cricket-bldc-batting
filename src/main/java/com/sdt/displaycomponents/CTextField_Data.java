/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author srikanth.possim
 */
public class CTextField_Data extends TextField{
    int len = 0;
    public CTextField_Data(int length){
        
        super();
        this.len = length;
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                 if(newValue.length() > len)
                    setText(newValue.substring(0, len));
                
            }
        
        });
        
        
    }
    public CTextField_Data(String value,int length){
        super(value);
        this.len = length;
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                 if(newValue.length() > len){
                    setText(newValue.substring(0, len));
                 }
            }
        
        });
        
    }
    Text lentext = null;
    public CTextField_Data(String value,int length,Text text){
        super(value);
        this.len = length;
        this.lentext = text;
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                 if(newValue.length() > len){
                    setText(newValue.substring(0, len));                    
                 }else{
                     lentext.setText("("+newValue.length()+")");
                 }
            }
        
        });
        
    }

   
    
}
