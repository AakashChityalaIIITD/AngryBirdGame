package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlueAngryBird extends AngryBird{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public BlueAngryBird(){
        super("Blueey",0,0,new Sprite(new Texture("blue.png")));
        this.sprite = new Sprite(new Texture("blue.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }

}
