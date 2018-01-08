package Sinestro.java;

import uiboard.Ground;

import java.io.IOException;
import java.lang.*;
import uiboard.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FormationID formationID=FormationID.NULL;
        FormChooser fmChooser=new FormChooser();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        formationID=fmChooser.getFormationID();

        if(formationID!=FormationID.NULL) {
            Ground ground = new Ground(formationID);
            ground.setVisible(true);
        }
        else{
            formationID=FormationID.FENGSHI;
            Ground ground = new Ground(formationID);
            ground.setVisible(true);
        }
    }

    /* Original version:
     * Including Formation
     * /
    /*
    private void setJusticeQueue(){
        for (int i=6;i>=0;i--)
            this.justiceQueue.creatureEnqueue(this.huLuWa[i]);
    }

    private void setEvilQueue(){
        this.evilQueue.creatureEnqueue(this.xieZiJing);

        for (int i=0;i<6;i++)
            this.evilQueue.creatureEnqueue(this.xiaoLouLuo[i]);
    }

    public static void main(String[] args){
        HuLuWaWorld huLuWaWorld=new HuLuWaWorld();

        //葫芦娃入队排序
        huLuWaWorld.setJusticeQueue();
        new QuickSort().Sort(huLuWaWorld.justiceQueue);

        //妖精入队
        huLuWaWorld.setEvilQueue();

        //排兵布阵
        huLuWaWorld.yeYe.setPositionInfo(huLuWaWorld.space.getPositionInfo(18,13));
        new ChangShe().formation(huLuWaWorld.justiceQueue,new Coordinate(20,9),huLuWaWorld.space);
        huLuWaWorld.sheJing.setPositionInfo(huLuWaWorld.space.getPositionInfo(10,12));
        new YanXing().formation(huLuWaWorld.evilQueue,new Coordinate(8,7),huLuWaWorld.space);
        huLuWaWorld.space.displaySpace();

        huLuWaWorld.space.clearSpace();

        huLuWaWorld.yeYe.setPositionInfo(huLuWaWorld.space.getPositionInfo(18,13));
        new ChangShe().formation(huLuWaWorld.justiceQueue,new Coordinate(20,9),huLuWaWorld.space);
        huLuWaWorld.sheJing.setPositionInfo(huLuWaWorld.space.getPositionInfo(10,12));
        new HeYi().formation(huLuWaWorld.evilQueue,new Coordinate(8,12),huLuWaWorld.space);
        huLuWaWorld.space.displaySpace();

        huLuWaWorld.space.clearSpace();

        huLuWaWorld.yeYe.setPositionInfo(huLuWaWorld.space.getPositionInfo(18,13));
        new ChangShe().formation(huLuWaWorld.justiceQueue,new Coordinate(20,9),huLuWaWorld.space);
        huLuWaWorld.sheJing.setPositionInfo(huLuWaWorld.space.getPositionInfo(10,12));
        new FangZhen().formation(huLuWaWorld.evilQueue,new Coordinate(8,12),huLuWaWorld.space);
        huLuWaWorld.space.displaySpace();

        huLuWaWorld.space.clearSpace();

        huLuWaWorld.yeYe.setPositionInfo(huLuWaWorld.space.getPositionInfo(18,13));
        new ChangShe().formation(huLuWaWorld.justiceQueue,new Coordinate(20,9),huLuWaWorld.space);
        huLuWaWorld.sheJing.setPositionInfo(huLuWaWorld.space.getPositionInfo(10,12));
        new FengShi().formation(huLuWaWorld.evilQueue,new Coordinate(8,12),huLuWaWorld.space);
        huLuWaWorld.space.displaySpace();

        huLuWaWorld.space.clearSpace();

        huLuWaWorld.yeYe.setPositionInfo(huLuWaWorld.space.getPositionInfo(18,13));
        new ChangShe().formation(huLuWaWorld.justiceQueue,new Coordinate(20,9),huLuWaWorld.space);
        huLuWaWorld.sheJing.setPositionInfo(huLuWaWorld.space.getPositionInfo(10,12));
        new ChongE().formation(huLuWaWorld.evilQueue,new Coordinate(8,12),huLuWaWorld.space);
        huLuWaWorld.space.displaySpace();

    }*/
}
