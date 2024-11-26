package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

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

    public int getImpactPower(){
        return impact;
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
    public Vector2 calculateLaunchVelocity(float x, float y, float z, float w) {
        float catapultX =z;
        float catapultY =w;

        float deltaX = (catapultX - x) /100f;
        float deltaY = (catapultY - y) /100f;
        float power = 15f;

        Vector2 launchVelocity = new Vector2(deltaX * power, deltaY * power);// Adjust power to control launch strength
        System.out.println("Launch Velocity Calculation: " + launchVelocity.x + ", " + launchVelocity.y);
        return new Vector2((deltaX - 0.5f) * power, (deltaY + 0.5f) * power);
    }

}
