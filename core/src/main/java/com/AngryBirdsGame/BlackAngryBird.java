package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlackAngryBird extends AngryBird{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public BlackAngryBird(){
        super("Blacky",0,0,new Sprite(new Texture("Black.png")));
        this.sprite = new Sprite(new Texture("Black.png"));
    }
    public Sprite getSprite(){
        return sprite;
    }
}
