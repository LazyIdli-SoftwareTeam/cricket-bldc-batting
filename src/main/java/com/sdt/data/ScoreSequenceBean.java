/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

/**
 *
 * @author Srikanth
 */
public class ScoreSequenceBean {
    int bi_no = 0;
    int bi_status=0;
    int interval = 0;
    int direction = 0;
    
    public ScoreSequenceBean(){
        
    }
    
    public ScoreSequenceBean(int bi , int status , int time){
        bi_no = bi;
        bi_status = status;
        interval = time;
    }

    public int getBi_no() {
        return bi_no;
    }

    public void setBi_no(int bi_no) {
        this.bi_no = bi_no;
    }

    public int getBi_status() {
        return bi_status;
    }

    public void setBi_status(int bi_status) {
        this.bi_status = bi_status;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    
    
}
