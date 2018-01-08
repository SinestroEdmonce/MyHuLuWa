package formation;

import queue.*;
import space.*;


public class FangZhen implements Formation{
    private int queueLength;
    private int spaceLength;
    private int spaceWidth;
    private int sideLength;
    private Coordinate beginPt;

    @Override
    public void formation(QueueArray queue, Coordinate startpt, Space space){
        this.spaceLength=space.getSpaceLength();
        this.spaceWidth=space.getSpaceWidth();
        this.queueLength=queue.getQueueSize();
        this.sideLength=(this.queueLength+2)/3;
        this.beginPt=startpt;

        for(int i=0;i<this.queueLength;i++){
            if (i<this.sideLength){
                int ptxCur=this.beginPt.getPtX();
                int ptyCur=this.beginPt.getPtY()+i*10;
                if (ptxCur>=0 && ptxCur<this.spaceLength
                        && ptyCur>=0 && ptyCur<this.spaceWidth) {
                    if (space.getPositionInfo(ptxCur, ptyCur).isHolderEmpty() == false) {
                        System.out.println("Error:Overlapping position.");
                        return;
                    }
                }
                else{
                    System.out.println("Error:Out of boundary.");
                    return;
                }
            }
            else{
                int ptxCur=this.beginPt.getPtX()-(int)(((i-this.sideLength)+2)/2)*10;
                int ptyCur=this.beginPt.getPtY()+(int)((this.sideLength-1)*((i-this.sideLength)%2))*10;
                if (ptxCur>=0 && ptxCur<this.spaceLength
                        && ptyCur>=0 && ptyCur<this.spaceWidth) {
                    if (space.getPositionInfo(ptxCur, ptyCur).isHolderEmpty() == false) {
                        System.out.println("Error:Overlapping position.");
                        return;
                    }
                }
                else{
                    System.out.println("Error:Out of boundary.");
                    return;
                }
            }
        }

        for(int i=0;i<this.queueLength;i++){
            if (i<this.sideLength){
                int ptxCur=this.beginPt.getPtX();
                int ptyCur=this.beginPt.getPtY()+i*10;
                space.getPositionInfo(ptxCur,ptyCur)
                        .setHolder(queue.getCreatureInfo(i).getHolder());
            }
            else{
                int ptxCur=this.beginPt.getPtX()-(int)(((i-this.sideLength)+2)/2)*10;
                int ptyCur=this.beginPt.getPtY()+(int)((this.sideLength-1)*((i-this.sideLength)%2))*10;
                space.getPositionInfo(ptxCur,ptyCur)
                        .setHolder(queue.getCreatureInfo(i).getHolder());
            }
        }

    }
}
