package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameHandler {
    private int score;
    private String filename;
    private int currentLevel; // Track the current level
    private int remainingBirds; // Track remaining birds
    private List<Pigs> pigs; // List of pigs in the game
    private List<woodenBrick> woodBlocks; // List of wooden blocks
    private List<GlassBlock> glassBlocks; // List of glass blocks
    private List<SteelBlock> steelBlocks; // List of stone blocks
    private List<AngryBird> birds; // List of active birds

    public GameHandler(String filename) {
        pigs = new ArrayList<Pigs>();
        this.filename = filename;
        woodBlocks = new ArrayList<woodenBrick>();
        glassBlocks = new ArrayList<GlassBlock>();
        steelBlocks = new ArrayList<SteelBlock>();
        birds = new ArrayList<AngryBird>();
        remainingBirds = 3; // Example starting number of birds
    }

    public void saveGameState(List<Pigs> pigs1, List<woodenBrick> bricks1, List<GlassBlock> glassBlocks1, List<SquareGlasses> squareGlasses1, List<SteelBlock> steelBlocks1, int noOfBirds) {
        GameState gameState = new GameState();
        gameState.score = this.score;
        gameState.level = this.currentLevel;
        gameState.remainingBirds = noOfBirds;

        // Save pigs
        for (Pigs pig : pigs1) {
            GameState.PigState pigState;
            if (pig.sprite != null && pig.body!=null && pig.sprite.getX()>=0 && pig.sprite.getY()>=0) {
                pigState = new GameState.PigState((int) pig.sprite.getX(), (int) pig.sprite.getY(), pig.health);
//                System.out.println(pig.body.getPosition().x);
//                System.out.println(pig.body.getPosition().y);
            } else {
                pigState = new GameState.PigState(-1, -1, pig.health);
            }
            gameState.pigs.add(pigState);

        }

        // Save wood blocks
        for (woodenBrick wood : bricks1) {
            GameState.BlockState blockState;
            if (wood.sprite != null && wood.body!=null && wood.sprite.getX()>=0 && wood.sprite.getY()>=0) {
                blockState = new GameState.BlockState((int) wood.sprite.getX(), (int) wood.sprite.getY(), "Wood", wood.durability);
            } else {
                blockState = new GameState.BlockState(-1, -1, "Wood", wood.durability);
            }
            gameState.blocks.add(blockState);


        }

        for (SquareGlasses glass : squareGlasses1) {
            GameState.BlockState blockState;
            if (glass.sprite != null && glass.body!=null && glass.sprite.getX()>=0 && glass.sprite.getY()>=0) {
                blockState = new GameState.BlockState((int) glass.sprite.getX(), (int) glass.sprite.getY(), "Wood", glass.durability);
            } else {
                blockState = new GameState.BlockState(-1, -1, "Glass", glass.durability);
            }
            gameState.blocks.add(blockState);


        }

        // Save glass blocks
        for (GlassBlock glass : glassBlocks1) {
            GameState.BlockState blockState;
            if (glass.sprite != null && glass.body!=null && glass.sprite.getX()>=0 && glass.sprite.getY()>=0) {
                blockState = new GameState.BlockState((int) glass.sprite.getX(), (int) glass.sprite.getY(), "Glass", glass.durability);
            } else blockState = new GameState.BlockState(-1, -1, "Glass", glass.durability);
            gameState.blocks.add(blockState);
        }

        for (SteelBlock steel : steelBlocks1) {
            GameState.BlockState blockState;
            if (steel.sprite != null && steel.body!=null && steel.sprite.getX()>=0 && steel.sprite.getY()>=0) {
                blockState = new GameState.BlockState((int) steel.sprite.getX(), (int) steel.sprite.getY(), "Glass", steel.durability);
            } else blockState = new GameState.BlockState(-1, -1, "Steel", steel.durability);
            gameState.blocks.add(blockState);
        }

        // Prepare to write to file
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {

            fileOut = new FileOutputStream(filename);
            out = new ObjectOutputStream(fileOut);
            out.writeObject(gameState); // Write the GameState object

            System.out.println("Game state has been serialized and saved.");
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } finally {
            // Close streams in the finally block
            try {
                if (out != null) {
                    out.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Handle closing exceptions
            }
        }


        System.out.println("Saving state: " + gameState.serialize());
    }

    public boolean loadGameState(List<Pigs> pigs1, List<woodenBrick> bricks1, List<GlassBlock> glassBlocks1
        , List<SteelBlock> steelBlocks1, List<AngryBird> birds1) {

        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        FileHandle file = Gdx.files.local(filename);

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("Save file does not exist or is empty. Starting a new game.");
            return false; // Exit method, no state to load
        }

        try {
            fileIn = new FileInputStream(file.file()); // Use Gdx.file() for compatibility
            in = new ObjectInputStream(fileIn);
            GameState gameState = (GameState) in.readObject();
//            if (gameState == null) {
//                return false;
//            }
//
//            // Restore the game state
//            this.score = gameState.score;
//            this.currentLevel = gameState.level;
//            this.remainingBirds = gameState.remainingBirds; // Restore remaining birds
//            pigs1.clear();
//            bricks1.clear();
//            glassBlocks1.clear();
//            steelBlocks1.clear();
//            birds1.clear();
//
//
//            // Restore pigs
//            for (GameState.PigState pigState : gameState.pigs) {
//
//                smallpig pig = new smallpig(); // Create a new pig at saved position
//                pig.health = pigState.durability; // Set durability for the pig
//                pigs1.add(pig); // Add to the game
//            }
//            // Restore wood blocks
//            for (GameState.BlockState blockState : gameState.blocks) {
//                if ("Wood".equals(blockState.type)) {
//                    woodenBrick wood = new woodenBrick(); // Create wood block
//                    wood.durability = blockState.durability; // Set durability for wood block
//                    wood.setPosY((int) blockState.x);
//                    wood.setPosY((int) blockState.y);
//                    bricks1.add(wood);
//                } else if ("Glass".equals(blockState.type)) {
//                    GlassBlock glass = new GlassBlock(); // Create glass block
//                    glass.durability = blockState.durability; // Set durability for glass block
//                    this.glassBlocks.add(glass); // Add to the game
//                    glass.setPosX((int) blockState.x);
//                    glass.setPosY((int) blockState.y);
//                    glassBlocks1.add(glass);
//                } else if ("Stone".equals(blockState.type)) {
//                    SteelBlock stone = new SteelBlock(); // Create stone block
//                    stone.durability = blockState.durability; // Set durability for stone block
//                    this.steelBlocks.add(stone); // Add to the game
//                    stone.setPosX((int) blockState.x);
//                    stone.setPosY((int) blockState.y);
//                    steelBlocks1.add(stone);
//
//                }
//            }
//
//            // Restore all active birds
//            for (GameState.BirdState birdState : gameState.birds) {
//                AngryBird bird;
//                if ("RedAngryBird".equals(birdState.type)) {
//                    bird = new RedAngryBird(); // Create an instance of RedAngryBird
//                } else if ("BlackAngryBird".equals(birdState.type)) {
//                    bird = new BlackAngryBird(); // Create an instance of BlackAngryBird
//                } else if ("BlueAngryBird".equals(birdState.type)) {
//                    bird = new BlueAngryBird(); // Create an instance of BlueAngryBird
//                } else {
//                    bird = null; // Handle unknown bird types if necessary
//                }
//
//                // Position the bird if it exists
//                if (bird != null) {
//                    bird.setPosX((int) birdState.x); // Set bird position
//                    bird.setPosY((int) birdState.y);
//                    birds1.add(bird); // Add to the list of active birds
//                }
//
//            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
        // Additional methods for managing game state, score, etc., can be added here
    }



    public List<GameState.PigState> loadPigs() {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        FileHandle file = Gdx.files.local(filename);

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("Save file does not exist or is empty. Starting a new game.");
            return new ArrayList<GameState.PigState>(); // Exit method, no state to load
        }

        try {
            fileIn = new FileInputStream(file.file()); // Use Gdx.file() for compatibility
            in = new ObjectInputStream(fileIn);
            GameState gameState = (GameState) in.readObject();  // Read the GameState object
            return gameState.pigs;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public List<GameState.BlockState> loadBlocks() {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        FileHandle file = Gdx.files.local(filename);

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("Save file does not exist or is empty. Starting a new game.");
            return new ArrayList<GameState.BlockState>(); // Exit method, no state to load
        }

        try {
            fileIn = new FileInputStream(file.file()); // Use Gdx.file() for compatibility
            in = new ObjectInputStream(fileIn);
            GameState gameState = (GameState) in.readObject();  // Read the GameState object
            return gameState.blocks;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int loadNoOfBirds() {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        FileHandle file = Gdx.files.local(filename);

        // Check if the file exists and is not empty
        if (!file.exists() || file.length() == 0) {
            System.out.println("Save file does not exist or is empty. Starting a new game.");
            return 3; // Exit method, no state to load
        }

        try {
            fileIn = new FileInputStream(file.file()); // Use Gdx.file() for compatibility
            in = new ObjectInputStream(fileIn);
            GameState gameState = (GameState) in.readObject();  // Read the GameState object
            return gameState.remainingBirds;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
