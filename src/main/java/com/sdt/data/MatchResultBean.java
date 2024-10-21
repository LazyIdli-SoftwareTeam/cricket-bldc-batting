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
public class MatchResultBean {
    int type=0;
    String Name;
    boolean endgame;
    boolean gamewon;
    int target_runs;
    int current_runs;
    int total_balls;
    int current_balls;
    String Comment;

    public boolean isEndgame() {
        return endgame;
    }

    public void setEndgame(boolean endgame) {
        this.endgame = endgame;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isGamewon() {
        return gamewon;
    }

    public void setGamewon(boolean gamewon) {
        this.gamewon = gamewon;
    }

    public int getTarget_runs() {
        return target_runs;
    }

    public void setTarget_runs(int target_runs) {
        this.target_runs = target_runs;
    }

    public int getCurrent_runs() {
        return current_runs;
    }

    public void setCurrent_runs(int current_runs) {
        this.current_runs = current_runs;
    }

    public int getTotal_balls() {
        return total_balls;
    }

    public void setTotal_balls(int total_balls) {
        this.total_balls = total_balls;
    }

    public int getCurrent_balls() {
        return current_balls;
    }

    public void setCurrent_balls(int current_balls) {
        this.current_balls = current_balls;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }
    
    
    
    
}
