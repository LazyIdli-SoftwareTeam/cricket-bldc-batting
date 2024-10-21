/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdt.data;

import java.util.ArrayList;
import zapcricketsimulator.Variables;

/**
 *
 * @author possi
 */
public class MatchBean {
    boolean game_over = false;
    int game_type = Variables.match_type_one_over_each;
    int no_of_players = 4;
    int no_of_overs = 4;
    String teama_name="Team A";
    String teama_mob="";
    String teamb_name="Team B";
    String teamb_mob="";
    ArrayList<String> teamA_player_names=null;
    ArrayList<String> teamB_player_names=null;
    
    int playing_team = Variables.match_playing_team_A;
    int current_playing_team = Variables.match_playing_team_A;
    int teama_pos=0;
    int teamb_pos=0;
    int teama_balls=0;
    int teamb_balls=0;
    int teama_score=0;
    int teamb_score=0;
    ArrayList<Integer> teamA_player_scores = null;
    ArrayList<Integer> teamB_player_scores = null;
    
    String comments = "";
    
    public MatchBean(){
        initMatch();
    } 
    
    public void initMatch(){
        game_over = false;
        teama_pos=0;
        teamb_pos=0;
        teama_score=0;
        teamb_score=0;
        teama_balls=0;
        teamb_balls=0;
        teamA_player_scores = new ArrayList<>();
        teamB_player_scores = new ArrayList<>();
        current_playing_team = playing_team;
        for(int i=0;i<no_of_players;i++){
            teamA_player_scores.add(0);
            teamB_player_scores.add(0);
        }
    }

    public boolean isGame_over() {
        return game_over;
    }

    public void setGame_over(boolean game_over) {
        this.game_over = game_over;
    }
    
    

    
    public String getTeama_name() {
        return teama_name;
    }

    public void setTeama_name(String teama_name) {
        this.teama_name = teama_name;
    }

    public String getTeama_mob() {
        return teama_mob;
    }

    public void setTeama_mob(String teama_mob) {
        this.teama_mob = teama_mob;
    }

    public String getTeamb_name() {
        return teamb_name;
    }

    public void setTeamb_name(String teamb_name) {
        this.teamb_name = teamb_name;
    }

    public String getTeamb_mob() {
        return teamb_mob;
    }

    public void setTeamb_mob(String teamb_mob) {
        this.teamb_mob = teamb_mob;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getGame_type() {
        return game_type;
    }

    public void setGame_type(int game_type) {
        this.game_type = game_type;
    }

    public int getNo_of_players() {
        return no_of_players;
    }

    public void setNo_of_players(int no_of_players) {
        this.no_of_players = no_of_players;
    }

    public int getNo_of_overs() {
        return no_of_overs;
    }

    public void setNo_of_overs(int no_of_overs) {
        this.no_of_overs = no_of_overs;
    }

    public ArrayList<String> getTeamA_player_names() {
        return teamA_player_names;
    }

    public void setTeamA_player_names(ArrayList<String> teamA_player_names) {
        this.teamA_player_names = teamA_player_names;
    }

    public ArrayList<String> getTeamB_player_names() {
        return teamB_player_names;
    }

    public void setTeamB_player_names(ArrayList<String> teamB_player_names) {
        this.teamB_player_names = teamB_player_names;
    }

    public int getPlaying_team() {
        return playing_team;
    }

    public void setPlaying_team(int playing_team) {
        this.playing_team = playing_team;
    }

    public int getTeama_pos() {
        return teama_pos;
    }

    public void setTeama_pos(int teama_pos) {
        this.teama_pos = teama_pos;
    }

    public int getTeamb_pos() {
        return teamb_pos;
    }

    public void setTeamb_pos(int teamb_pos) {
        this.teamb_pos = teamb_pos;
    }

    public int getTeama_score() {
        return teama_score;
    }

    public void setTeama_score(int teama_score) {
        this.teama_score = teama_score;
    }

    public int getTeamb_score() {
        return teamb_score;
    }

    public void setTeamb_score(int teamb_score) {
        this.teamb_score = teamb_score;
    }

    public ArrayList<Integer> getTeamA_player_scores() {
        return teamA_player_scores;
    }

    public void setTeamA_player_scores(ArrayList<Integer> teamA_player_scores) {
        this.teamA_player_scores = teamA_player_scores;
    }

    public ArrayList<Integer> getTeamB_player_scores() {
        return teamB_player_scores;
    }

    public void setTeamB_player_scores(ArrayList<Integer> teamB_player_scores) {
        this.teamB_player_scores = teamB_player_scores;
    }

    public int getCurrent_playing_team() {
        return current_playing_team;
    }

    public void setCurrent_playing_team(int current_playing_team) {
        this.current_playing_team = current_playing_team;
    }

    public int getTeama_balls() {
        return teama_balls;
    }

    public void setTeama_balls(int teama_balls) {
        this.teama_balls = teama_balls;
    }

    public int getTeamb_balls() {
        return teamb_balls;
    }

    public void setTeamb_balls(int teamb_balls) {
        this.teamb_balls = teamb_balls;
    }

   
    
}
