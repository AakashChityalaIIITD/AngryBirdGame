package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class kingpig {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public kingpig() {
        this.sprite=new Sprite(new Texture("pig2.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
