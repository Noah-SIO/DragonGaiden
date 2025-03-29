package io.github.Noah_SIO;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private float x;
    private float y;
    private int size;
    Texture characterImg;
    Sprite character;

    public Player(float x, float y, int size){
        this.x=x;
        this.y=y;
        this.size=size;
        characterImg = new Texture("ship.png");
        character = new Sprite(characterImg);
        character.setSize(size * 2, size * 2);
        character.setPosition(x, y);
    }



    public void move(float x, float y){
        this.x += x;
        this.y += y;
        character.setPosition(this.x, this.y);
    }

    public void setPosition(float x, float y){
        character.setPosition(x, y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(int newx){
        x=newx;
    }

    public void setY(int newy){
        y=newy;
    }

    public float getSize(){
        return size;
    }

    public void draw(SpriteBatch batch){
        character.draw(batch);
    }

    public Sprite returnSprite(){
        return character;
    }
}
