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
public class MatchDataBean {
    
    String player_name="Player1";
    int player_type=1;//1 for right handed batsman 2 for left handed bats man
    int target_runs=36;
    int match_balls=18;
    int match_wickets=6;
    int current_runs=0;
    int current_balls=0;
    int current_wickets=0;
    int current_speed=50;
    int boler_type=0;
    int boler_id =0;
    int boler_ms = 1800;
    int ball_result = -1;
    int ball_result_dir=0;

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getPlayer_type() {
        return player_type;
    }

    public void setPlayer_type(int player_type) {
        this.player_type = player_type;
    }

    public int getTarget_runs() {
        return target_runs;
    }

    public void setTarget_runs(int target_runs) {
        this.target_runs = target_runs;
    }

    public int getMatch_balls() {
        return match_balls;
    }

    public void setMatch_balls(int match_balls) {
        this.match_balls = match_balls;
    }

    public int getMatch_wickets() {
        return match_wickets;
    }

    public void setMatch_wickets(int match_wickets) {
        this.match_wickets = match_wickets;
    }

    public int getCurrent_runs() {
        return current_runs;
    }

    public void setCurrent_runs(int current_runs) {
        this.current_runs = current_runs;
    }
    
    public void addtoCurrent_runs(int current_runs) {
        this.current_runs = this.current_runs+current_runs;
    }

    public int getCurrent_balls() {
        return current_balls;
    }

    public void setCurrent_balls(int current_balls) {
        this.current_balls = current_balls;
    }
    public void incrementCurrent_balls() {
        current_balls++;
    }

    public int getCurrent_wickets() {
        return current_wickets;
    }

    public void setCurrent_wickets(int current_wickets) {
        this.current_wickets = current_wickets;
    }
    public void incrementCurrent_wickets() {
        current_wickets++;
    }

    public int getCurrent_speed() {
        return current_speed;
    }

    public void setCurrent_speed(int current_speed) {
        this.current_speed = current_speed;
    }

    public int getBoler_type() {
        return boler_type;
    }

    public void setBoler_type(int boler_type) {
        this.boler_type = boler_type;
    }

    public int getBoler_ms() {
        return boler_ms;
    }

    public void setBoler_ms(int boler_ms) {
        this.boler_ms = boler_ms;
    }

    public int getBall_result() {
        return ball_result;
    }

    public void setBall_result(int ball_result) {
        this.ball_result = ball_result;
    }

    public int getBall_result_dir() {
        return ball_result_dir;
    }

    public void setBall_result_dir(int ball_result_dir) {
        this.ball_result_dir = ball_result_dir;
    }

    public int getBoler_id() {
        return boler_id;
    }

    public void setBoler_id(int boler_id) {
        this.boler_id = boler_id;
    }
    
    
    
}
