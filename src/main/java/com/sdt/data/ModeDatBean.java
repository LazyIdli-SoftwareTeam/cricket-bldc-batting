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
public class ModeDatBean {
    
    String [] bowler_path=new String[8];
    int [] trigger_interval = new int[8];
    int [][] bowling_speed = new int[8][4];
    int [] bowling_type = new int[8];
    
    public ModeDatBean(){
        for(int i=0;i<8;i++)
            bowler_path[i]="";
    }

    public String[] getBowler_path() {
        return bowler_path;
    }

    public void setBowler_path(String[] bowler_path) {
        this.bowler_path = bowler_path;
    }

    public int[] getTrigger_interval() {
        return trigger_interval;
    }

    public void setTrigger_interval(int[] trigger_interval) {
        this.trigger_interval = trigger_interval;
    }

    public int[][] getBowling_speed() {
        return bowling_speed;
    }

    public void setBowling_speed(int[][] bowling_speed) {
        this.bowling_speed = bowling_speed;
    }

    public int[] getBowling_type() {
        return bowling_type;
    }

    public void setBowling_type(int[] bowling_type) {
        this.bowling_type = bowling_type;
    }
    
    
    
    
}
