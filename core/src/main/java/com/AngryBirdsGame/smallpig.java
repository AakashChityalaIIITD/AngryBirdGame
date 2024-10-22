package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class smallpig {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public smallpig() {
        this.sprite=new Sprite(new Texture("pig1.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
