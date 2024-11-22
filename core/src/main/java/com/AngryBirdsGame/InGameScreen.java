package com.AngryBirdsGame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input;
import dev.lyze.gdxUnBox2d.GameObject;

import java.util.ArrayList;




public class InGameScreen extends ApplicationAdapter implements Screen {
    private Main game;
    private Stage stage;
    private World world;
    private Body body;
    private SpriteBatch batch;
    private Sprite background;
    private Sprite lower;
    private RedAngryBird bird_red;
    private BlackAngryBird bird_black;
    private BlueAngryBird bird_blue;
    private fattyPig pig_fatty;
    private smallpig pig_smallpig;
    private kingpig pig_king;
    private catapol cata;
    private ImageButton pause;
    private woodenBrick brick_wooden1;
    private woodenBrick brick_wooden2;
    private woodenBrick brick_wooden3;
    private woodenBrick brick_wooden4;
    private GlassBlock glass_block1;
    private GlassBlock glass_block2;
    private SteelBlock steel_block;
    private final float PPM=100f;
    private ArrayList<Body> bodies;
    Box2DDebugRenderer debugRenderer;

    //SET Position of Bodies that I made
    //Make chnages in wood,glass steel such as setsize and update sprite
    private Body create(AngryBird gameObject, BodyDef.BodyType bodyType, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            gameObject.sprite.getX() / PPM + gameObject.sprite.getWidth() / 2 / PPM,
            gameObject.sprite.getY() / PPM + gameObject.sprite.getHeight() / 2 / PPM
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameObject.sprite.getWidth() / 2 / PPM, gameObject.sprite.getHeight() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);

        shape.dispose(); // Dispose of the shape to avoid memory leaks

        return body;
    }
    private Body create1(catapol gameObject, BodyDef.BodyType bodyType, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            gameObject.sprite.getX() / PPM + gameObject.sprite.getWidth() / 2 / PPM,
            gameObject.sprite.getY() / PPM + gameObject.sprite.getHeight() / 2 / PPM
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameObject.sprite.getWidth() / 2 / PPM, gameObject.sprite.getHeight() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);

        shape.dispose(); // Dispose of the shape to avoid memory leaks

        return body;
    }
    private Body create2(Blocks gameObject, BodyDef.BodyType bodyType, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            gameObject.sprite.getX() / PPM + gameObject.sprite.getWidth() / 2 / PPM,
            gameObject.sprite.getY() / PPM + gameObject.sprite.getHeight() / 2 / PPM
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameObject.sprite.getWidth() / 2 / PPM, gameObject.sprite.getHeight() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);

        shape.dispose(); // Dispose of the shape to avoid memory leaks

        return body;
    }
    private Body create3(Pigs gameObject, BodyDef.BodyType bodyType, float density, float friction, float restitution) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(
            gameObject.sprite.getX() / PPM + gameObject.sprite.getWidth() / 2 / PPM,
            gameObject.sprite.getY() / PPM + gameObject.sprite.getHeight() / 2 / PPM
        );

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(gameObject.sprite.getWidth() / 2 / PPM, gameObject.sprite.getHeight() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        body.setUserData(gameObject);

        shape.dispose(); // Dispose of the shape to avoid memory leaks

        return body;
    }


    public InGameScreen(Main main){
        world = new World(new Vector2(0, -9.81f), true);
        bodies = new ArrayList<Body>();
        this.game = main;
        this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio
        Gdx.input.setInputProcessor(stage);
        pause=new ImageButton(new SpriteDrawable(new Sprite(new Texture("pause.png"))));
        lower=new Sprite(new Texture("Stage.png"));
        bird_red=new RedAngryBird();
        bird_red.sprite.setPosition(20,108);
        bird_red.body=create(bird_red,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(bird_red.body);
        bird_blue=new BlueAngryBird();
        bird_blue.sprite.setPosition(100,108);
        bird_blue.body = create(bird_blue, BodyDef.BodyType.DynamicBody, 1f, 0.5f, 0.3f);
        bodies.add(bird_blue.body);
        bird_black=new BlackAngryBird();
        bird_black.sprite.setPosition(180,108);
        bird_black.body=create(bird_black, BodyDef.BodyType.DynamicBody, 1f, 0.5f, 0.3f);
        bodies.add(bird_black.body);
        pig_fatty=new fattyPig();
        pig_fatty.sprite.setPosition(850,108+90+50+65);
        pig_fatty.body=create3(pig_fatty,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(pig_fatty.body);
        pig_king=new kingpig();
        pig_king.sprite.setPosition(890,108+90+20+10);
        pig_king.body=create3(pig_king,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(pig_king.body);
        pig_smallpig=new smallpig();
        pig_smallpig.sprite.setPosition(860,108);
        pig_smallpig.body=create3(pig_smallpig,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(pig_smallpig.body);
        brick_wooden1=new woodenBrick();
        brick_wooden1.sprite.setPosition(800,108);
        brick_wooden1.body=create2(brick_wooden1,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(brick_wooden1.body);
        brick_wooden2=new woodenBrick();
        brick_wooden2.sprite.setPosition(1000,108);
        brick_wooden2.body=create2(brick_wooden2,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(brick_wooden2.body);
        brick_wooden3=new woodenBrick();
        brick_wooden3.sprite.setPosition(850,108+90);
        brick_wooden3.body=create2(brick_wooden3,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(brick_wooden3.body);
        brick_wooden4=new woodenBrick();
        brick_wooden4.sprite.setPosition(850,108+90);
        brick_wooden4.body=create2(brick_wooden4,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(brick_wooden4.body);
        glass_block1=new GlassBlock();
        glass_block1.sprite.setPosition(780,180);
        glass_block1.body=create2(glass_block1,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(glass_block1.body);
        glass_block2=new GlassBlock();
        glass_block2.sprite.setPosition(820,180+90+20);
        glass_block2.body=create2(glass_block2,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(glass_block2.body);
        steel_block=new SteelBlock();
        steel_block.sprite.setPosition(870,108+90+5);
        steel_block.body=create2(steel_block,BodyDef.BodyType.DynamicBody,1f,0.5f,0.3f);
        bodies.add(steel_block.body);
        debugRenderer = new Box2DDebugRenderer();
        cata=new catapol();
        cata.sprite.setPosition(250,108);
        cata.body = create1(cata, BodyDef.BodyType.StaticBody, 1f, 0.5f, 0.1f);
        bodies.add(cata.body);
        stage.addActor(pause);
        pause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PauseMenuScreen(game));
            }
        });

        batch = new SpriteBatch();
        background = new Sprite(new Texture("GamePage.jpg"));
        //this.stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));  // FitViewport maintains aspect ratio

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.step(1/60f, 6, 2);
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)){
            // Your code here to handle when the "W" key is pressed
            this.game.setScreen(new VictoryScreen(game));
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.L)){
            // Your code here to handle when the "W" key is pressed
            this.game.setScreen(new LoseScreen(game));
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float x=20f;
        for (Body body : bodies) {
            if (body.getUserData() instanceof AngryBird) {
                ((AngryBird) body.getUserData()).updateSprite(PPM);
            } else if (body.getUserData() instanceof Blocks) {
                ((Blocks) body.getUserData()).updateSprite(PPM);
            }
            else if(body.getUserData() instanceof Pigs){
                ((Pigs) body.getUserData()).updateSprite(PPM);
            }
            else if(body.getUserData() instanceof  catapol){
                ((catapol) body.getUserData()).updateSprite(PPM);
            }
        }

//        bird_red.sprite.setPosition(20,108);
//        bird_red.sprite.setSize(44,47);
//        brick_wooden1.sprite.setPosition(800,108);
//        brick_wooden2.sprite.setPosition(1000,108);
//        brick_wooden2.sprite.setSize(20,80);
//        pig_smallpig.sprite.setPosition(860,108);
//        pig_smallpig.sprite.setSize(65,68);
//        pig_king.sprite.setPosition(890,108+90+20+10);
//        pig_king.sprite.setSize(45,50);
//        pig_fatty.sprite.setPosition(890,108+90+50+65);
//        pig_fatty.sprite.setSize(60,50);
//        brick_wooden3.sprite.setPosition(850,108+90);
//        brick_wooden3.sprite.setSize(20,100);
//        brick_wooden4.sprite.setPosition(950,108+90);
//        brick_wooden4.sprite.setSize(20,100);
//        brick_wooden1.sprite.setSize(20,80);
//        glass_block1.sprite.setPosition(780,180);
//        glass_block1.sprite.setSize(260,30);
//        glass_block2.sprite.setPosition(820,180+90+20);
//        glass_block2.sprite.setSize(210,30);
//        steel_block.sprite.setPosition(870,108+90+5);
//        steel_block.sprite.setSize(80,93);
//        bird_blue.sprite.setSize(44,47);
//        bird_blue.sprite.setPosition(100,108);
//        bird_black.sprite.setSize(42,51);
//        bird_black.sprite.setPosition(180,108);
//        cata.sprite.setSize(130,130);
//        cata.sprite.setPosition(250,108);

        pause.setSize(50,60);

        pause.setPosition(Gdx.graphics.getWidth()-pause.getWidth(),Gdx.graphics.getHeight()-pause.getHeight());
        batch.begin();
        background.draw(batch);
        pause.draw(batch, 1f);
        bird_red.sprite.draw(batch);
        bird_blue.sprite.draw(batch);
        bird_black.sprite.draw(batch);
        pig_fatty.sprite.draw(batch);
        pig_king.sprite.draw(batch);
        brick_wooden1.sprite.draw(batch);
        brick_wooden2.sprite.draw(batch);
        brick_wooden3.sprite.draw(batch);
        brick_wooden4.sprite.draw(batch);
        glass_block2.sprite.draw(batch);
        glass_block1.sprite.draw(batch);
        steel_block.sprite.draw(batch);
        pig_smallpig.sprite.draw(batch);
        cata.sprite.draw(batch);
        batch.end();
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
        world.dispose();  // Dispose the world to free memory
        batch.dispose();  // Dispose the SpriteBatch
        stage.dispose();
    }
}
