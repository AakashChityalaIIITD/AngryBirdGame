package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class fattyPig {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public fattyPig() {
        this.sprite=new Sprite(new Texture("pig3.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
