package com.test.solarsystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

public class CelestialBody extends Actor {
    private float radius = 0;
    private float angle = 0;
    private float speed = 0.5f;
    private TextureRegion texture;
    private float parentX, parentY;


    public CelestialBody(Texture texture, float speed){
        this.texture = new TextureRegion(texture);
        this.speed = speed;
        setBounds(getX(), getY(), this.texture.getRegionWidth(), this.texture.getRegionHeight());
        setOrigin(Align.center);
    }

    @Override
    public void act(float delta){
        if(angle >= 360)
            angle -= 360;
        angle += delta * speed;
        setPosition(nextPointX(), nextPointY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
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

    public float nextPointX() {
        return (float) Math.cos(angle) * getRadius() + parentX;
    }

    public float nextPointY(){
        return (float) Math.sin(angle) * getRadius() + parentY;
    }

    public void setTexture(Texture texture){this.texture = new TextureRegion(texture);}
    public void setRadius(float radius){this.radius = radius;}
    public float getRadius(){return radius;}
    public void setAngle(float angle){this.angle = angle;}
    public float getAngle(){return angle;}

    // which point is the center of body's axis
    public void setParent(Vector2 parent){
        parentX = parent.x;
        parentY = parent.y;
    }

}
