/*
* Project "Solar system" for Bergen Games.
* Made by Tatiana Grigoreva, 2021/05/02.
 */

package com.test.solarsystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Game implements ApplicationListener {

    // States for actors movement
    public enum STATE{
        PLAY,
        STOP
    }

    private Button button;
    private Stage stage;
    private ShapeRenderer shape;
    private CelestialBody[][] solarSystem = new CelestialBody[3][2];
    private STATE currentState;

    public void create() {
        Viewport vp = new FitViewport(800,480);
        stage = new Stage(vp);

        //Setting planets

        // CelestialBody(Texture, Speed)
        solarSystem[0][0] = new CelestialBody(new Texture(Assets.SUN), 0f);
        solarSystem[1][0] = new CelestialBody(new Texture(Assets.EARTH), 1f);
        solarSystem[1][1] = new CelestialBody(new Texture(Assets.MOON), 3f);
        solarSystem[2][0] = new CelestialBody(new Texture(Assets.MARS), 2f);

        solarSystem[1][0].setScale(0.75f);
        solarSystem[2][0].setScale(0.7f);
        solarSystem[1][1].setScale(0.2f);

        solarSystem[0][0].setPosition(
                stage.getViewport().getWorldWidth()  / 2 - solarSystem[0][0].getWidth()  / 2,
                stage.getViewport().getWorldHeight() / 2 - solarSystem[0][0].getHeight() / 2);
        solarSystem[1][0].setPosition(solarSystem[0][0].getX() - 100, solarSystem[0][0].getY());
        solarSystem[2][0].setPosition(solarSystem[0][0].getX() - 250, solarSystem[0][0].getY());
        solarSystem[1][1].setPosition(solarSystem[1][0].getX() -  30, solarSystem[1][0].getY());

        solarSystem[1][0].setRadius(getRadius(solarSystem[0][0],solarSystem[1][0]));
        solarSystem[2][0].setRadius(getRadius(solarSystem[0][0],solarSystem[2][0]));
        solarSystem[1][1].setRadius(getRadius(solarSystem[1][0],solarSystem[1][1]));

        solarSystem[0][0].setParent(new Vector2(solarSystem[0][0].getX(), solarSystem[0][0].getY()));
        solarSystem[1][0].setParent(new Vector2(solarSystem[0][0].getX(), solarSystem[0][0].getY()));
        solarSystem[2][0].setParent(new Vector2(solarSystem[0][0].getX(), solarSystem[0][0].getY()));
        solarSystem[1][1].setParent(new Vector2(solarSystem[1][0].getX(), solarSystem[1][0].getY()));

        //setting a button and a current state up
        currentState = STATE.PLAY;
        button = new Button(currentState);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.changeState();
                switch (currentState){
                    case PLAY:
                        pause();
                        break;
                    case STOP:
                        resume();
                        break;
                    default:
                        break;
                }
            }
        });

        button.setPosition( 20 , 20);

        stage.addActor(solarSystem[0][0]);
        stage.addActor(solarSystem[1][0]);
        stage.addActor(solarSystem[1][1]);
        stage.addActor(solarSystem[2][0]);
        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);
        shape = new ShapeRenderer();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw an orbits
        shape.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < solarSystem.length; i++) {
            drawCircle(shape, solarSystem[0][0],solarSystem[i][0].getRadius());
            if (solarSystem[i][1] != null)
                drawCircle(shape, solarSystem[i][0],solarSystem[i][1].getRadius());
        }
        shape.end();

        //draw planets
        stage.draw();

        //checks whether planets can be moved
        if(currentState == STATE.PLAY) {
            stage.act(delta);
            updateLocation();
        }
    }

    @Override
    public void pause() {
        this.currentState = STATE.STOP;

    }

    @Override
    public void resume() {
        this.currentState = STATE.PLAY;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void drawCircle(ShapeRenderer shape, CelestialBody actorA, float radius) {
        /* Draws an Orbit,
        * where ActorA-location is the center of an Orbit
        * and ActorB is for a radius to be calculated */

        shape.setColor(Color.WHITE);

        shape.circle(actorA.getX() + actorA.getWidth()/2 ,
                     actorA.getY() + actorA.getHeight()/2,
                        radius);

    }

    public float getRadius(Actor actorA, Actor actorB){
        /* Returns Radius from 2 points in a vector system.
           ActorA is the center */
        return (float) Math.sqrt((actorB.getX() - actorA.getX()) * (actorB.getX() - actorA.getX()) +
                                 (actorB.getY() - actorA.getY()) * (actorB.getY() - actorA.getY()));
    }


    //this is acquired for moons, so moons' planet location will be updated
    public void updateLocation(){
        solarSystem[1][1].setParent(new Vector2(solarSystem[1][0].getX(), solarSystem[1][0].getY()));
    }



}