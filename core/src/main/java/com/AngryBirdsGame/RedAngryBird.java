package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RedAngryBird extends AngryBird {
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public RedAngryBird(){
        super("Reddy",0,0,new Sprite(new Texture("Red.png")));
        this.sprite=new Sprite(new Texture("Red.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
