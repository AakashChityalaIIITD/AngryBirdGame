package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RedAngryBird {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public RedAngryBird(){
        this.sprite=new Sprite(new Texture("Red.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
