package io.github.Noah_SIO;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Monster {
    private float x;
    private float y;
    private int size;
    private int type; ///1,2,3


    public Monster(float x, float y, int size, int type){
        this.x=x;
        this.y=y;
        this.size=size;
        this.type=type;
    }



    public void move(float x, float y){
        this.x += x;
        this.y += y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getSize(){
        return size;
    }

    public void draw(ShapeRenderer shape){
        shape.circle(x,y,size);
    }


}
