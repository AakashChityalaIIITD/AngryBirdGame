package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

class AngryBird {
    public String name;
    private int posX;
    private int posY;
    public Sprite sprite;
    public int impact;
    public float speed;
    public Body body;

    public AngryBird(String name,int posX,int posY,Sprite sprite){
        this.name=name;
        this.posX=posX;
        this.posY=posY;
        this.sprite=sprite;

    }

    public void updateSprite(float delta){
        sprite.setPosition(body.getPosition().x*delta - sprite.getWidth()/2, body.getPosition().y*delta - sprite.getHeight()/2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
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
