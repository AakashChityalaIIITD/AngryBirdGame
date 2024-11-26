package com.AngryBirdsGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DamageTest {
    Pigs pig;
    Blocks block;
    @BeforeEach
    void setUp() {
        pig=new Pigs(25,25,null,5);
        block=new Blocks("blocky",25,25,5);
    }

    @Test
    void getDamage_Pig() {
        int h= pig.health;
        pig.takeDamage(4);
        assertEquals(1,pig.health);
    }

    @Test
    void getDamage_Block() {
        block.takeDamage(4);
        assertEquals(11,block.durability);
    }

}
