package io.github.Noah_SIO;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Shoot {
    private float x;
    private float y;
    private float largeur;
    private float hauteur;
    Texture shootImg;
    Sprite shoot;

    public Shoot(float x, float y, float largeur, float hauteur, int type){
        this.x=x;
        this.y=y;
        this.hauteur=hauteur;
        this.largeur=largeur;
        if(type==1){
        shootImg = new Texture("shoot.png");
        }if(type==2){
        shootImg = new Texture("shootmonster.png");
        }
        shoot = new Sprite(shootImg);
        shoot.setSize(largeur * 4, hauteur * 4);
        shoot.setPosition(x, y);
    }



    public void move(float x, float y){
        this.x += x;
        this.y += y;
        shoot.setPosition(this.x, this.y);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void draw(SpriteBatch batch){
        shoot.draw(batch);
    }

    public Sprite returnSprite(){
        return shoot;
    }
}
