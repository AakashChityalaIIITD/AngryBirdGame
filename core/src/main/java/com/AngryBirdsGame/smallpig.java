package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class smallpig extends Pigs{
    private int positionX;
    private int positionY;
    public Sprite sprite;
    public smallpig() {
        super(0,0,new Sprite(new Texture("pig1.png")));
        this.sprite=new Sprite(new Texture("pig1.png"));
        sprite.setPosition(65,68);
    }
    public Sprite getSprite(){
        return sprite;
    }
}
