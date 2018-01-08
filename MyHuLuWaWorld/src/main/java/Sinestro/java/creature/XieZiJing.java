package creature;

import java.util.Random;
import uiboard.*;

public class XieZiJing extends Creature{
    protected long startAttack;
    protected long stopAttack;

    public XieZiJing(Battle battle){
        super("XieZiJing.png",1100, 300,350,350, Role.XIEZI, battle,0);
        rightRole = false;
        HP = 10;
        rand = new Random();
    }

    public boolean preMove(){
        int flag=0;
        int aimx=0, aimy=0;
        int minD=9999;
        int i=0;
        for(Creature roleCrt: battle.roles) {
            if (roleCrt.isAlive() && roleCrt.isRightRole()){
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
            incy = 1;
        } else {
            incy = -1;
        }
        return true;
    }

    public synchronized void move() {
        update();
        for(Creature roleCrt: battle.roles) {
            if(roleCrt.isAlive() && willHit(getXCenter(),getYCenter(), roleCrt.getXCenter(),roleCrt.getYCenter())) {

                if (roleCrt.isRightRole()) {
                    if (roleCrt.role == Role.YEYE) {
                        roleCrt.setDie();
                    } else if (roleCrt.role == Role.QI) {
                        HP=HP-5;
                        if(HP<=0){
                            setX(getX() + 1600);
                            setY(rand.nextInt(350));
                            HP=10;
                            return;
                        }
                    } else if (roleCrt.isAttacking()) {
                        HP--;
                        if (isAttacking()) {
                            roleCrt.HP-=3;
                        }
                        if(HP<=0){
                            setX(getX() + 1600);
                            setY(rand.nextInt(350));
                            HP=10;
                            return;
                        }
                    }
                } else if (roleCrt != this) {
                    incx *= 4;
                    incy *= 3;
                    ;
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

        if(addy<200)
            addy = 200;
        else if(addy>800)
            addy=800;
        if(addx<0)
            addx = 0;
        else if(addx>1600)
            addx=1600;
        setX(addx);
        setY(addy);
        update();
    }

    public synchronized void attack(){
        if(attack ==false && System.currentTimeMillis()-stopAttack>1000) {
            attack =true;
            startAttack = System.currentTimeMillis();
        } else if (attack ==true && System.currentTimeMillis()-startAttack>2000) {
            attack =false;
            stopAttack = System.currentTimeMillis();;
        }
    }

    public synchronized void run() {
        while (!Thread.interrupted() && runningFlag ==true && isAlive()) {
            try {
                if(preMove()) {
                    move();
                    attack();
                }
                Thread.sleep(rand.nextInt(30));
                this.battle.repaint();
            } catch (Exception e) {
            }
        }
    }
}
