package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class fattyPig extends Pigs{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public fattyPig() {
        super(0,0,new Sprite(new Texture("pig3.png")));
        this.sprite=new Sprite(new Texture("pig3.png"));
        sprite.setSize(60,50);
    }
    public Sprite getSprite(){
        return sprite;
    }
}
