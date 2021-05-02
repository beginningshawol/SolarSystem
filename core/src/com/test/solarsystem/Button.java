package com.test.solarsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.test.solarsystem.Game.STATE;


public class Button extends Actor {

    STATE currentState;
    TextureRegion texture;

    public Button(STATE state){
        currentState = state;
        updateTexture();

        setBounds(getX(), getY(), this.texture.getRegionWidth(), this.texture.getRegionHeight());
        setOrigin(Align.center);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation());
    }

    // updates a texture depending on a current state.
    // it shows an opposite asset
    public void updateTexture(){
        switch (currentState){
            case PLAY:
                texture = new TextureRegion(new Texture(Assets.STOP));
                break;
            case STOP:
                texture = new TextureRegion(new Texture(Assets.PLAY));
                break;
            default:
                break;
        }
    }

    public void changeState(){
        switch (currentState){
            case PLAY:
                currentState = STATE.STOP;
                break;
            case STOP:
                currentState = STATE.PLAY;
                break;
            default:
                break;
        }
        updateTexture();
    }

    //in case if any getter-setter is needed
    public void setCurrentState(STATE state){
        currentState = state;
        updateTexture();
    }

    public STATE getCurrentState(){return currentState;}



}