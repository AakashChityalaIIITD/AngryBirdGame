package com.AngryBirdsGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class InGameScreen implements Screen {
    private Main game;
    private Stage stage;
    private World world;
    private Body body;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite lower;
    private Sprite bird1_red;
    private Sprite bird2_blue;
    private Sprite bird3_black;
    private Sprite pig1;
    private Sprite pig2;
    private Sprite pig3;
    private Sprite catapol;
    private ImageButton pause;
    public InGameScreen(Main main){
        this.game = main;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        pause=new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause.png"))));
        lower=new Sprite(new Texture("Stage.png"));
        bird1_red=new Sprite(new Texture("Red.png"));
        bird2_blue=new Sprite(new Texture("blue.png"));
        bird3_black=new Sprite(new Texture("black.png"));
        catapol=new Sprite(new Texture("Catapol.png"));
        pig1=new Sprite(new Texture("pig1.png"));
        pig2=new Sprite(new Texture("pig2.png"));
        pig3=new Sprite(new Texture("pig3.png"));
        this.world = new World(new Vector2(0f,9.8f), true);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type=BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        stage.addActor(pause);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clicked");
                game.setScreen(new PauseMenuScreen(game));
            }
        });

        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(50f,50f);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = boxShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 1f;

        body.createFixture(fixtureDef);

        batch = new SpriteBatch();
        background = new Sprite(new Texture("GamePage.jpg"));
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float x=20f;
        bird1_red.setPosition(20,108);
        bird1_red.setSize(44,47);
        bird2_blue.setSize(44,47);
        bird2_blue.setPosition(100,108);
        bird3_black.setSize(42,51);
        bird3_black.setPosition(180,108);
        catapol.setSize(130,130);
        catapol.setPosition(250,108);
        pause.setSize(50,60);
        pause.setPosition(Gdx.graphics.getWidth()-pause.getWidth(),Gdx.graphics.getHeight()-pause.getHeight());
        batch.begin();
        background.draw(batch);
        pause.draw(batch, 1f);
        bird1_red.draw(batch);
        bird2_blue.draw(batch);
        bird3_black.draw(batch);
        catapol.draw(batch);
        batch.end();
        world.step(1/60f,6,2);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        background.setSize(width, height);
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
        world.dispose();
    }
}
