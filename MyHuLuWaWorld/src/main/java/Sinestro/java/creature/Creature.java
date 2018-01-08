package creature;
import space.*;
import uiboard.*;

import java.util.Random;

public abstract class Creature extends Thing2D implements Runnable{
    protected String creatureName;
    protected PositionInfo positionInfo;

    public Role role;
    private int ID;
    public boolean rightRole;
    
    protected int xCenter;
    protected int yCenter;
    
    protected int creatureWidth;
    protected int creatureLength;
    
    protected int incx;
    protected int incy;
    
    protected boolean attack;
    
    public boolean isAlive;
    public int HP;
    // 进程控制标志
    public boolean runningFlag;

    // 需要与战役控制后台进行交互
    protected Battle battle;

    protected Random rand;
    
    public void setPositionInfo(PositionInfo positionfuture){
        if (positionfuture.isHolderEmpty()==false){
            System.out.println("Error:Initilization Failed.");
            return;
        }
        this.positionInfo=positionfuture;
        positionfuture.setHolder(this);
    }

    public PositionInfo getPositionInfo(){
        return this.positionInfo;
    }

    public String getCreatureName(){
        return this.creatureName;
    }

    protected Creature(String url, int x, int y, int width, int height, Role role, Battle battle, int id) {
        super(url, x, y, width, height);
        this.creatureWidth = width;
        this.creatureLength = height;
        this.role = role;
        this.isAlive = true;
        this.runningFlag = true;
        this.battle = battle;
        this.ID = id;
        this.update();
    }

    public abstract void run();

    public synchronized void update(){
        if(role == Role.XIEZI || role == Role.SHE) {
            this.creatureWidth = getWidth();
            this.creatureLength = getLength();
        } else {
            this.creatureWidth = getWidth();
            this.creatureLength =  getLength();
        }
        this.xCenter = this.getX() + (int) (getCreatureWidth() /2.0);
        this.yCenter = this.getY() + (int) (getCreatureLength() /2.0);
    }

    protected boolean willHit(int x1, int y1, int x2, int y2){
        return Math.abs(y1-y2)<10 && Math.abs(x1-x2)<15;
    }

    public int getXCenter(){
        return this.xCenter;
    }

    public int getYCenter(){
        return this.yCenter;
    }

    public int getCreatureWidth() {
        return this.creatureWidth;
    }

    public int getCreatureLength() {
        return this.creatureLength;
    }

    public boolean isRightRole() {
        return this.rightRole;
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public boolean isAttacking(){
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setLive(){
        this.isAlive = true;
        runningFlag = true;
    }

    public void setDie(){
        this.isAlive = false;
        runningFlag = false;
    }

    public int getID() {
        return ID;
    }
}
