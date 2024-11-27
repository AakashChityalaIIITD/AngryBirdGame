package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SquareGlasses extends Blocks {
    private int positionX;
    private int positionY;


    public SquareGlasses(){
        super("Glassy",0,0,2);
        this.sprite=new Sprite(new Texture("glassSquare.png"));
    }
}
