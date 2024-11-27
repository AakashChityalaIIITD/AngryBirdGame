package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class fattyPig extends Pigs{
    private int positionX;
    private int positionY;

    public fattyPig() {
        super(0,0,new Sprite(new Texture("pig3.png")),7);
        this.sprite=new Sprite(new Texture("pig3.png"));
        sprite.setSize(40,50);
        this.health=4+1+2;
    }

}
