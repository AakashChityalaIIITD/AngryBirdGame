package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class kingpig extends Pigs{
    private int positionX;
    private int positionY;

    public kingpig() {
        super(0,0,new Sprite(new Texture("pig2.png")));

        this.sprite=new Sprite(new Texture("pig2.png"));
        sprite.setSize(40,50);
    }

}
