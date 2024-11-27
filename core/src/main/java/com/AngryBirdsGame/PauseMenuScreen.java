package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;


public class PauseMenuScreen implements Screen {
    private Main game;
    private Stage stage;
    private Sprite background;
    private SpriteBatch batch;
    private ImageButton ResumeButton;
    private ImageButton BackMainMenuButton;
    private ImageButton SaveButton;
    private Sprite backgroundTexture;
    private BitmapFont font;
    private String message;
    private boolean showMessage = false;
    private float messageDisplayTime = 2f;
    private float elapsedTime = 0;
    private final InGameScreen ing3;
    private final InGameScreen2 ing2;
    private final
    InGameScreen1 ing1;
    Table table;
    String filename;
    public PauseMenuScreen(Main main, final int level, final List<Pigs> pigs1, final List<woodenBrick> bricks1, final List<GlassBlock> glassBlocks1, final List<SquareGlasses> squareGlasses1, final List<SteelBlock> steelBlocks1, final int noOfBirds,  InGameScreen1 ing11,  InGameScreen ing31,  InGameScreen2 ing21){
        this.game=main;
        this.ing1=ing11;
        this.ing2=ing21;
        this.ing3=ing31;
        this.filename = "gameSave" + level + ".dat";
        this.font = new BitmapFont();
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();
        backgroundTexture = new Sprite(new Texture("pauseBackground.png"));
        background = new Sprite(new Texture("GamePage.jpg"));

        background.setPosition(0, 0);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table= new Table();
        table.setFillParent(true);
        table.center();

        SaveButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("SaveGame.png"))));
        BackMainMenuButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("mainmenu.png"))));
        ResumeButton=new ImageButton(new SpriteDrawable(new Sprite(new Texture("resume.png"))));

        table.add(ResumeButton).size(400,100).padBottom(25).row();
        table.add(SaveButton).size(400,100).padBottom(25).row();
        table.add(BackMainMenuButton).size(400,100).row();

        stage.addActor(table);

        ResumeButton.addListener(new ClickListener() {
            @Override
           public void clicked(InputEvent event, float x, float y) {
                if(level==1){
                    game.setScreen(ing1);
                    Gdx.input.setInputProcessor(ing1.getStage());
                }
                else if(level==3){
                    game.setScreen(ing3);
                    Gdx.input.setInputProcessor(ing3.getStage());
                }
                else{
                    game.setScreen(ing2);
                    Gdx.input.setInputProcessor(ing2.getStage());

                }
           }
        });

        SaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // give code to save game
                saveGameState(pigs1, bricks1, glassBlocks1, squareGlasses1, steelBlocks1, noOfBirds);
                game.setScreen(new AfterSaveScreen(game));
            }
        });

        BackMainMenuButton.addListener(new ClickListener() {
           public void clicked(InputEvent event, float x, float y) {
             game.setScreen(new MainScreen(game));
           }
        });


    }


    public void saveGameState(java.util.List<Pigs> pigs1, java.util.List<woodenBrick> bricks1, java.util.List<GlassBlock> glassBlocks1, java.util.List<SquareGlasses> squareGlasses1, List<SteelBlock> steelBlocks1, int noOfBirds) {
        GameState gameState = new GameState();
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


    @Override
    public void show() {
        font = new BitmapFont();
        message = "Game Saved Successfully";

        SaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Show the message when SaveButton is clicked
                showMessage = true;
                elapsedTime = 0;

                // Timer to switch screens after 2 seconds
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new MainScreen(game));
                    }
                }, messageDisplayTime);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backgroundTexture.setSize(350,428);
        backgroundTexture.setPosition(Gdx.graphics.getWidth()/2-backgroundTexture.getWidth()/2, Gdx.graphics.getHeight()/2 - backgroundTexture.getHeight()/2);
        batch.begin();
        background.draw(batch);
        backgroundTexture.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
        background.setPosition(0, 0);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
