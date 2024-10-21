/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

/**
 *
 * @author possi
 */
public class KeyValueBean {
    boolean bool_key;
    int key;
    int index;
    int type;
    String value;
    int additional_value=-1;
    
    /*public KeyValueBean(boolean bool_key , String value){
        this.bool_key=bool_key;
        this.value=value;
    }*/
    
    public KeyValueBean(int key , String value){
        this.key=key;
//        this.index=key;
        this.value=value;
    }
    public KeyValueBean(int index , int key , String value){
        this.key=key;
        this.index=index;
        this.value=value;
    }

     public KeyValueBean( int key , String value,int type){
        this.key=key;
        this.type=type;
        this.value=value;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isBool_key() {
        return bool_key;
    }

    public void setBool_key(boolean bool_key) {
        this.bool_key = bool_key;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAdditional_value() {
        return additional_value;
    }

    public void setAdditional_value(int additional_value) {
        this.additional_value = additional_value;
    }   

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    @Override
    public String toString(){
        if(type==1)
            return value+"_Right";
        else if(type==2)
            return value+"_Left";
        else
            return value;
    }
}
