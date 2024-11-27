package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pigs {
    private int posX;
    private int posY;
    public Sprite sprite;
    public int health;
    public Body body;

    public Pigs(int posX, int posY, Sprite sprite,int health) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
        this.health = health;
    }



    public void updateSprite(float delta){
        sprite.setPosition(body.getPosition().x*delta - sprite.getWidth()/2, body.getPosition().y*delta - sprite.getHeight()/2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }
    public int getHealth(){
        return this.health;
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
    public Body getBody(){
        return body;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
