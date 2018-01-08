package space;

public class Coordinate {
    private int ptX;
    private int ptY;

    public Coordinate(){
        this.ptX=0;
        this.ptY=0;
    }

    public Coordinate(int tmpx,int tmpy){
        this.ptX=tmpx;
        this.ptY=tmpy;
    }

    public int getPtX(){
        return this.ptX;
    }

    public int getPtY(){
        return this.ptY;
    }

    public void setPtCrdn(int tmpx,int tmpy){
        this.ptX=tmpx;
        this.ptY=tmpy;
    }
}
