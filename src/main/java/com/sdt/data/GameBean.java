/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

import java.util.ArrayList;

/**
 *
 * @author Srikanth
 */
public class GameBean {
    String game_id = "1509022001";
    boolean update_status = false;
    int type = 0;//single player , multui player
    int batting_type = 0;//right hand , left hand
    int no_of_players = 2;
    int no_of_overs_each = 2;
    int bowler_selection = 0;//manual , auto
    int seq_pos = 0;
    ArrayList<PlayerGameBean> player_data = new ArrayList<>();

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNo_of_overs_each() {
        return no_of_overs_each;
    }

    public void setNo_of_overs_each(int no_of_overs_each) {
        this.no_of_overs_each = no_of_overs_each;
    }

    public int getBowler_selection() {
        return bowler_selection;
    }

    public void setBowler_selection(int bowler_selection) {
        this.bowler_selection = bowler_selection;
    }

    public ArrayList<PlayerGameBean> getPlayer_data() {
        return player_data;
    }

    public void setPlayer_data(ArrayList<PlayerGameBean> player_data) {
        this.player_data = player_data;
    }

    public int getNo_of_players() {
        return no_of_players;
    }

    public void setNo_of_players(int no_of_players) {
        this.no_of_players = no_of_players;
    }

    public int getBatting_type() {
        return batting_type;
    }

    public void setBatting_type(int batting_type) {
        this.batting_type = batting_type;
    }

    public int getSeq_pos() {
        return seq_pos;
    }

    public void setSeq_pos(int seq_pos) {
        this.seq_pos = seq_pos;
    }    

    public boolean isUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(boolean update_status) {
        this.update_status = update_status;
    }
    
    
    public void init(int player_count){
        seq_pos=0;
        update_status=false;
        no_of_players = player_count;
        if(player_data.size()>0)
            player_data.clear();
        for(int i=0;i<player_count;i++)
            player_data.add(new PlayerGameBean(i+1));
    }
    
    
}
