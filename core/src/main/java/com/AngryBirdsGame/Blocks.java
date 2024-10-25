package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Blocks {

    public String name;
    private int posX;
    private int posY;
    private int durability;

    public Blocks(String name,int posX,int posY,int durability){
        this.name=name;
        this.posX=posX;
        this.posY=posY;
        this.durability=durability;
    }
    public void setPosX(int posX){
        this.posX=posX;
    }
    public void setPosY(int posY){
        this.posY=posY;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public int getDurability() {
        return durability;
    }
}
