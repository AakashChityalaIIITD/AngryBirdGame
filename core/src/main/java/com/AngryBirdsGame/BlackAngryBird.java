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
        this.speed=1.2f;
        this.impact=2;
        sprite.setSize(42,51);

    }
    public Sprite getSprite(){
        return sprite;
    }
}
