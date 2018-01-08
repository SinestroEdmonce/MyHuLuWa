package uiboard;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Thing2D {
    protected int x;
    protected int y;
    protected int thing2dWidth;
    protected int thing2dLength;
    protected Image image;

    public Thing2D(String url, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.thing2dWidth = width;
        this.thing2dLength = height;
        URL loc = this.getClass().getClassLoader().getResource(url);
        ImageIcon icon = new ImageIcon(loc);
        Image image = icon.getImage();
        this.setImage(image);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth(){
        return this.thing2dWidth;
    }

    public int getLength(){
        return this.thing2dLength;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageByUrl(String url){
        URL loc = this.getClass().getClassLoader().getResource(url);
        ImageIcon icon = new ImageIcon(loc);
        Image image = icon.getImage();
        this.setImage(image);
    }
}
