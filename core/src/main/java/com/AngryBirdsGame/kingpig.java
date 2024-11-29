//package com.AngryBirdsGame;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//
//public class kingpig extends Pigs{
//    private int positionX;
//    private int positionY;
//
//    public kingpig() {
//        super(0,0,new Sprite(new Texture("pig2.png")),6);
//
//        this.sprite=new Sprite(new Texture("pig2.png"));
//        sprite.setSize(40,50);
//        this.health=6;
//    }
//
//
//}



package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;

public class kingpig extends Pigs{
    private int positionX;
    private int positionY;
    private float amplitude;            // Amplitude of oscillation
    private float frequency;            // Frequency of oscillation
    private float initialY;
    private float lowY = 0f;           // Minimum Y position
    private float highY = 1000f;

    public kingpig() {
        super(0,0,new Sprite(new Texture("pig2.png")),6);

        this.sprite=new Sprite(new Texture("pig2.png"));
        sprite.setSize(40,50);
        this.health=6;
        this.amplitude = (highY - lowY) / 2; // Halfway between the low and high
        this.frequency = 15f;
        this.initialY = (lowY + highY) / 2;
    }
    public void initializePosition(float x) {
        float PPM=100f;
        // Set initial position for oscillation (Y) at the center
        this.initialY = (lowY + highY) / 2;
        this.body.setTransform(x / PPM, initialY / PPM, 0); // Set initial Box2D body position
        this.sprite.setPosition(x, initialY); // Set sprite's position
    }

    public void move(){
        float PPM=100f;
        float newY = initialY + amplitude * (float)Math.sin(frequency * (TimeUtils.millis() * 0.001f) * Math.PI * 2);

        // Ensure newY is always between lowY and highY
        newY = Math.max(lowY, Math.min(newY, highY));

        // Set the new position for the Box2D body; X remains constant
        this.body.setTransform(this.body.getPosition().x, newY / PPM, this.body.getAngle());

        // Update the sprite position to match the body's position
        sprite.setPosition(this.body.getPosition().x * PPM - sprite.getWidth() / 2,
            newY - sprite.getHeight() / 2);
    }
}
