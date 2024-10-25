package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;

class AngryBird {
    public String name;
    private int posX;
    private int posY;
    public Sprite sprite;
    public AngryBird(String name,int posX,int posY,Sprite sprite){
        this.name=name;
        this.posX=posX;
        this.posY=posY;
        this.sprite=sprite;
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
    public Sprite getSprite(){
        return sprite;
    }

}
