package formation;

import queue.QueueArray;
import space.*;

public class ChangShe implements Formation {
    private int queueLength;
    private int spaceLength;
    private int spaceWidth;
    private Coordinate beginPt;

    @Override
    public void formation(QueueArray queue, Coordinate startpt, Space space){
        this.spaceLength=space.getSpaceLength();
        this.spaceWidth=space.getSpaceWidth();
        this.queueLength=queue.getQueueSize();
        this.beginPt=startpt;

        for(int i=0;i<this.queueLength;i++){
            if (this.beginPt.getPtX()>=0 && this.beginPt.getPtX()<this.spaceLength
                    && this.beginPt.getPtY()+i*10>=0 && this.beginPt.getPtY()+i*10<this.spaceWidth){
                if (space.getPositionInfo(this.beginPt.getPtX(),this.beginPt.getPtY()+i).isHolderEmpty()==false) {
                    System.out.println("Error:Overlapping position.");
                    return;
                }
            }
            else{
                System.out.println("Error:Out of boundary.");
                return;
            }
        }
        for (int i=0;i<this.queueLength;i++){
            space.getPositionInfo(this.beginPt.getPtX(),this.beginPt.getPtY()+i*10)
                    .setHolder(queue.getCreatureInfo(i).getHolder());
        }
    }

    public int getQueueLength(){
        return this.queueLength;
    }

    public int getSpaceLength(){
        return this.spaceLength;
    }

    public int getSpaceWidth(){
        return this.spaceWidth;
    }
}
