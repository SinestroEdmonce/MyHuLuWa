package space;
import creature.*;

public class PositionInfo {
    private Coordinate creatureCrdn;
    private Creature holder=null;

    public PositionInfo(){
        this.creatureCrdn=new Coordinate(0,0);
    }

    public PositionInfo(int tmpx, int tmpy){
        this.creatureCrdn=new Coordinate(tmpx,tmpy);
    }

    public PositionInfo(Coordinate tmppt){
        this.creatureCrdn=tmppt;
    }

    public void setCreatureCrdn(Coordinate tmppt){
        creatureCrdn=tmppt;
    }

    public void setHolder(Creature creaturetmp){
        this.holder=creaturetmp;
    }

    public Creature getHolder(){
        return this.holder;
    }

    public Coordinate getCreatureCrdn(){
        return this.creatureCrdn;
    }

    public boolean isHolderEmpty(){
        if (this.holder==null){
            return true;
        }
        else{
            return false;
        }
    }
}
