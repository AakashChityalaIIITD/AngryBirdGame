package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Blocks {

    public String name;
    public Sprite sprite;
    private int posX;
    private int posY;
    public int durability;
    public Body body;

    public Blocks(String name,int posX,int posY,int durability){
        this.name=name;
        this.posX=posX;
        this.posY=posY;
        this.durability=durability+10;
    }
    public void updateSprite(float delta){
        sprite.setPosition(body.getPosition().x*delta - sprite.getWidth()/2, body.getPosition().y*delta - sprite.getHeight()/2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }
    public void takeDamage(float damage){
        durability-=damage;
    }
    public boolean isDestroyed(){
        return durability<=0;
    }
    public Sprite getSprite(){
        return sprite;
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
    public Body getBody() {
        return body;
    }
}
