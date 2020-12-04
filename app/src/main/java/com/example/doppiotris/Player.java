package com.example.doppiotris;

public class Player {

    private String name;
    private boolean turn;


    public Player(String name, boolean turn) {
        this.turn = turn;
        this.name = name;
    }

    public boolean isTurn() {
        return turn;
    }

    public String getName() {
        return name;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
