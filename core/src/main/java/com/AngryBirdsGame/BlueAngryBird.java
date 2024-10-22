package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlueAngryBird {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public BlueAngryBird(){
        this.sprite = new Sprite(new Texture("blue.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }

}