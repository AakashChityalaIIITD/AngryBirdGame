package com.AngryBirdsGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AngryBirdTest {
    AngryBird ac;
    @BeforeEach
    void setUp() {
        Sprite sp = new Sprite();
        ac = new AngryBird("birdy", 25, 25, sp);
    }

    @Test
    void check_constr_AngryBird(){
        assertEquals("birdy",ac.name, "The name checked.");
        assertEquals(25,ac.getPosX(), "The posX checked.");
        assertEquals(25, ac.getPosY(), "The posY checked.");
        assertNotNull(ac.getSprite(), "The sprite checked.");
    }

}
