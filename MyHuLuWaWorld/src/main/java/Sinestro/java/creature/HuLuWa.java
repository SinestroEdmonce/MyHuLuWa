package creature;
import compare.*;

import java.util.Random;
import uiboard.*;

public class HuLuWa extends Creature implements Compare{
    private int rankValue;
    private Color selfColor;
    
    protected int huluwaStartX;
    protected int huluwaStartY;
    private Battle battle;

    protected long startAttack;
    protected long stopAttack;

    public int getRankValue(){
        return this.rankValue;
    }

    public Color getSelfColor(){
        return this.selfColor;
    }

    @Override
    public boolean isLargerThan(Compare other){
        if (this.rankValue < ((HuLuWa)other).rankValue){
            return false;
        }
        else{
            return true;
        }
    }


    public HuLuWa(String url, int getX, int getY, Role role, int huluwaStartX, int huluwaStartY, Battle battle, int id){
        super(url,getX, getY, 300,300,role, battle, id);
        rightRole = true;
        rand = new Random();
        this.huluwaStartX = huluwaStartX;
        this.huluwaStartY = huluwaStartY;
        HP = 3;
        this.battle = battle;
    }

    private boolean preMove(){
        int flag=0;
        int aimx=0, aimy=0;
        int minD=9999;
        int i=0;
        for(Creature roleCrt: battle.roles) {
            if (!roleCrt.isRightRole()){
                flag=1;
                int d = Math.abs(roleCrt.getXCenter()-getXCenter()) + Math.abs(roleCrt.getYCenter()-getYCenter());
                if(d<minD){
                    minD = d;
                    aimx = roleCrt.getXCenter();
                    aimy = roleCrt.getYCenter();
                }
            }
        }
        if(flag==0)
            return false;
        if(aimx >= getXCenter()){
            incx = 1;
        } else {
            incx = -1;
        }
        if(aimy >= getYCenter()){
            incy = rand.nextInt(2);
        } else {
            incy = -1;
        }
        return true;
    }

    private synchronized void move() throws InterruptedException {
        update();

        for(Creature roleCrt: battle.roles) {
            if(willHit(getXCenter(), getYCenter(), roleCrt.getXCenter(),roleCrt.getYCenter())) {

                if (!roleCrt.isRightRole()) {
                    if (isAttacking()) {
                        if (roleCrt.role == Role.XIEZI || roleCrt.role == Role.SHE) {
                            roleCrt.HP--;
                        } else if (battle.mainEvilDeadFlag) {
                            roleCrt.setX(roleCrt.getX() + 1600);
                            roleCrt.setY(rand.nextInt(350));
                            roleCrt.HP = 0;
                            roleCrt.setDie();
                        }

                    } else {
                        if (role == Role.QI&& (roleCrt.role == Role.XIEZI || roleCrt.role == Role.SHE)) {
                            if(roleCrt.HP<=0)
                                roleCrt.setDie();
                            else
                                roleCrt.HP = roleCrt.HP-5;;
                        } else {
                            HP--;
                            if(HP>0){
                                setX(-120);
                                setY(rand.nextInt(350));
                            } else {
                                setX(huluwaStartX);
                                setY(huluwaStartY);
                                setDie();
                                Thread.sleep(7000);
                                HP=3;
                                setLive();
                            }
                        }
                    }
                    return;

                } else if (roleCrt != this) {
                    incx *= 4;
                    incy *= 3;
                    if (roleCrt.getXCenter() > getXCenter()) {
                        roleCrt.setX(roleCrt.getX() + incx);
                    } else if (roleCrt.getXCenter() < getXCenter()) {
                        roleCrt.setX(roleCrt.getX() - incx);
                    }
                    if (roleCrt.getYCenter() > getYCenter()) {
                        roleCrt.setY(roleCrt.getY() + incy);
                    } else if (roleCrt.getYCenter() < getYCenter()) {
                        roleCrt.setY(roleCrt.getY() - incy);
                    }
                }

            }
        }
        int addx = getX()+incx;
        int addy = getY()+incy;
        if(addy<120) {
            addy += 3;
            setY(addy);
            incx = incy = 0;
            update();
            return;
        }
        else if(addy>800)
            addy=800;
        if(addx<-150)
            addx = -150;
        else if(addx>1510)
            addx=1510;
        setX(addx);
        setY(addy);
        update();
    }

    private synchronized void attack(){
        if(role== Role.QI) return;
        if(!isAttacking() && System.currentTimeMillis()-stopAttack>3000) {
            attack = true;
            startAttack = System.currentTimeMillis();
        } else if (attack && System.currentTimeMillis()-startAttack>2000) {
            attack = false;
            stopAttack = System.currentTimeMillis();
        }
    }

    public synchronized void run() {
        while (!Thread.interrupted() && runningFlag) {
            try {
                if(preMove()){
                    move();
                    attack();
                }
                Thread.sleep(rand.nextInt(10));
                this.battle.repaint();
            } catch (Exception e) {

            }
        }
    }
}
