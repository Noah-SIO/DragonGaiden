package io.github.Noah_SIO;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Monster {
    private float x;
    private float y;
    private int size;
    private int type; ///1,2,3
    Texture monsterImg;
    Sprite monster;
    int step=0;

    public Monster(float x, float y, int size, int type){
        this.x=x;
        this.y=y;
        this.size=size;
        this.type=type;
        monsterImg = new Texture("dragon.png");
        monster = new Sprite(monsterImg);
        monster.setSize(size*4, size * 4);
        monster.setPosition(x, y);
    }



    public void move(float x, float y){
        this.x += x;
        this.y += y;
        monster.setPosition(this.x, this.y);
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

    public int getType(){
        return type;
    }

    public int getStep(){
        return step;
    }

    public void nextStep(){
        step=step+1;
    }

    public void resetStep(){
        step=1;
    }

    public void draw(SpriteBatch batch){
        monster.draw(batch);
    }

    public Sprite returnSprite(){
        return monster;
    }


}
