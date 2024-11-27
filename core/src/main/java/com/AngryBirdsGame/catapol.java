package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

class catapol{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public Body body;
    public catapol(){
        positionX = 0;
        positionY = 0;
        sprite = new Sprite(new Texture("Catapol.png"));
        sprite.setSize(130,130);
        sprite.setOriginCenter();
    }
    public void updateSprite(float delta){
        sprite.setPosition(body.getPosition().x*delta - sprite.getWidth()/2, body.getPosition().y*delta - sprite.getHeight()/2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }
    public Body getBody(){
        return body;
    }
}
