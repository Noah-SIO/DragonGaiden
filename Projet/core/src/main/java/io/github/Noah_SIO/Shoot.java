package io.github.Noah_SIO;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Shoot {
    private float x;
    private float y;
    private float largeur;
    private float hauteur;


    public Shoot(float x, float y, float largeur, float hauteur){
        this.x=x;
        this.y=y;
        this.hauteur=hauteur;
        this.largeur=largeur;
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

    public void draw(ShapeRenderer shape){
        // Dessiner un rectangle (x, y, largeur, hauteur)
        shape.rect(x,y,largeur,hauteur);
    }
}
