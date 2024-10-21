/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.displaycomponents;

import com.sdt.data.KeyValueBean;
import java.util.ArrayList;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author possi
 */
public class CChoiceBox_KeyValue extends ChoiceBox<KeyValueBean>{
    
    public CChoiceBox_KeyValue(ArrayList<KeyValueBean> str_arr){
        getItems().addAll(str_arr);
    }
    public CChoiceBox_KeyValue(ArrayList<KeyValueBean> values,String value){
        getItems().addAll(values);
        if(value!=null && !value.equals("")){
            for(int i=0;i<values.size();i++){
                if(values.get(i).getValue().equals(value)){
                    getSelectionModel().select(i);
                }
            }
        }        
    }
    public CChoiceBox_KeyValue(ArrayList<KeyValueBean> values,int key){
        getItems().addAll(values);
        for(int i=0;i<values.size();i++){
            if(values.get(i).getKey()==key){
                getSelectionModel().select(i);
            }
        }
    }
    
    public CChoiceBox_KeyValue(ArrayList<KeyValueBean> values,int key,int type){
        getItems().addAll(values);
        for(int i=0;i<values.size();i++){
            if(values.get(i).getKey()==key){
                if(values.get(i).getType()==type)
                    getSelectionModel().select(i);
            }
        }
    }
        
    /*public CChoiceBox_KeyValue(ArrayList<KeyValueBean> values,boolean bool_key){
        getItems().addAll(values);
        for(int i=0;i<values.size();i++){
            if(values.get(i).isBool_key()==bool_key){
                getSelectionModel().select(i);
            }
        }
    }
    
    public void selectValue(boolean key){
        for(int i=0;i<getItems().size();i++){
            if(getItems().get(i).isBool_key()==key){
                getSelectionModel().select(i);
            }
        }
    }*/
    public void selectValue(int key){
        for(int i=0;i<getItems().size();i++){
            if(getItems().get(i).getKey()==key){
                getSelectionModel().select(i);
            }
        }
    }
    public void selectValue(String value){
        for(int i=0;i<getItems().size();i++){
            if(getItems().get(i).getValue().equals(value)){
                getSelectionModel().select(i);
            }
        }
    }
    
    public void selectValue(int type, int key){
        for(int i=0;i<getItems().size();i++){
            if(getItems().get(i).getKey()==key){
                if(getItems().get(i).getType()==type)
                    getSelectionModel().select(i);
            }
        }
    }
    
}
