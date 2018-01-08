package creature;

import java.util.Random;
import uiboard.*;

public class XiaoLouLuo extends Creature {

    public XiaoLouLuo(String url, int getX, int getY, Battle battle, int id){
        super(url, getX, getY, 160, 160, Role.LOULUO, battle, id);
        rightRole = false;
        rand = new Random();
    }
    
    private boolean preMove(){
        int flag=0;
        int aimx=0, aimy=0;
        int i=0;
        for(Creature roleCrt: battle.roles) {
            if (roleCrt.isAlive() && roleCrt.rightRole){
                flag=1;
                if(roleCrt.role== Role.YEYE &&
                        ( (Math.abs(roleCrt.getXCenter()-getXCenter())+Math.abs(roleCrt.getYCenter()-getYCenter())<200) ||  getXCenter()< roleCrt.getXCenter() ) ){
                    aimx = roleCrt.getXCenter();
                    aimy = roleCrt.getYCenter();
                    if(aimx >= getXCenter()){
                        incx = rand.nextInt(2);
                    } else {
                        incx = rand.nextInt(2)-1;
                    }
                    if(aimy >= getYCenter()){
                        incy = rand.nextInt(2);
                    } else {
                        incy = rand.nextInt(2)-1;
                    }
                    return true;
                }
            }
        }
        if(flag==0)
            return false;

        incx = rand.nextInt(2);
        incy = rand.nextInt(3)-1;
        return true;
    }

    private synchronized void move() {
        update();
        for(Creature roleCrt: battle.roles) {
            if(roleCrt.isAlive() && willHit(getXCenter(),getYCenter(), roleCrt.getXCenter(),roleCrt.getYCenter()) ) {

                if (roleCrt.isRightRole()) {
                    if (roleCrt.role == Role.YEYE) {
                        roleCrt.setDie();
                    } else {
                        roleCrt.HP--;
                        setX(getX() + 1600);
                        if (battle.mainEvilDeadFlag) {
                            setDie();
                            return;
                        }
                    }

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
        int addx = getX()-incx;
        int addy = getY()+incy;

        if(addy<120)
            addy = 120;
        else if(addy>800)
            addy=800;
        if(addx<-100)
            addx = -100;
        else if(addx>1600)
            addx=1600;
        setX(addx);
        setY(addy);
        update();
    }

    public synchronized void run() {
        while (!Thread.interrupted() && runningFlag) {
            try {
                if(preMove()) {
                    move();;
                }
                Thread.sleep(rand.nextInt(20));
                this.battle.repaint();
            } catch (Exception e) {

            }
        }
    }
}
