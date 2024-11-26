package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PigsTest {
    Pigs pigs;
    @BeforeEach
    void setUp() {
        Sprite sprite = new Sprite();
        pigs=new Pigs(25,25,sprite,3);
    }
    @Test
    void testPigs() {
        assertEquals(25,pigs.getPosX(), "The posX checked.");
        assertEquals(25, pigs.getPosY(), "The posY checked.");
        assertEquals(3, pigs.health, "The health checked.");
        assertNotNull(pigs.getSprite(), "The sprite checked.");
    }
}
