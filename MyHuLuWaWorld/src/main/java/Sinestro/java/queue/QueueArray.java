package queue;
import space.*;
import creature.*;

public class QueueArray {
    private PositionInfo creatureInfo[];
    private int queueSize;
    private int queueIter;
    private static final int maxSize=10;

    public QueueArray(int queuelength){
        this.queueSize=queuelength;
        this.queueIter=0;
        creatureInfo=new PositionInfo[this.queueSize];

        for (int i=0;i<this.queueSize;i++){
            creatureInfo[i]=new PositionInfo();
        }
    }

    private void expandQueueSize(){
        PositionInfo tempInfo[];
        tempInfo=new PositionInfo[this.queueSize];
        for (int i=0;i<this.queueSize;i++){
            tempInfo[i]=this.creatureInfo[i];
        }

        this.creatureInfo=null;
        this.creatureInfo=new PositionInfo[this.queueSize+maxSize];
        for (int i=0;i<this.queueSize;i++) {
            this.creatureInfo[i] = tempInfo[i];
        }
        this.queueSize+=maxSize;
    }

    public int getQueueSize(){
        return this.queueSize;
    }

    public PositionInfo getCreatureInfo(int index){
        return this.creatureInfo[index];
    }

    public void creatureEnqueue(Creature creaturetmp){
        if (this.queueIter>this.queueSize-1){
            this.expandQueueSize();
        }

        this.creatureInfo[queueIter].setHolder(creaturetmp);
        queueIter++;
    }
}
