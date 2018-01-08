package creature;

import creature.Creature;
import uiboard.*;

import java.util.Random;

public class LaoYeYe extends Creature {

    public LaoYeYe(Battle battle){
        super("LaoYeYe.png", 200,300,400,400, Role.YEYE, battle,0);
        rightRole = true;
        rand = new Random();
    }

    public boolean preMove(){
        int flag=0;
        int aimx=0, aimy=0;
        int minD=9999;
        int i=0;
        for(; i< battle.roles.size(); i++) {
            if (battle.roles.get(i).isRightRole()){
                flag=1;;
                int d = Math.abs(battle.roles.get(i).getXCenter()-getXCenter()) + Math.abs(battle.roles.get(i).getYCenter()-getYCenter());
                if(d<minD){
                    minD = d;
                    aimx = battle.roles.get(i).getXCenter();
                    aimy = battle.roles.get(i).getYCenter();
                }
            }
        }
        if(flag==0)
            return false;

        incx = rand.nextInt(3)-1;
        incy = rand.nextInt(3)-1;
        return true;
    }
    public synchronized void move() {
        update();
        for(Creature roleCrt: battle.roles) {
            if(willHit(getXCenter(), getYCenter(), roleCrt.getXCenter(),roleCrt.getYCenter())){

                if(!roleCrt.isRightRole()){
                    setDie();
                    return;
                } else if(roleCrt != this) {
                    incx *= 4;
                    incy *= 3;;
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
        else if(addy>820)
            addy=820;
        if(addx<0)
            addx = 0;
        else if(addx>1510)
            addx=1510;
        setX(addx);
        setY(addy);
        update();
    }
    public synchronized void run() {
        while (!Thread.interrupted() && isAlive()  && runningFlag) {
            try {
                if(preMove()){
                    move();
                }
                Thread.sleep(rand.nextInt(40));
                this.battle.repaint();
            } catch (Exception e) {

            }
        }
    }

}
