package formation;

import queue.QueueArray;
import space.*;


public class ChongE implements Formation {
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
        int ptyCur=this.beginPt.getPtY();

        for(int i=0;i<this.queueLength;i++){
            int ptxCur=this.beginPt.getPtX()-i*10;
            ptyCur=ptyCur-(int)(Math.pow(-1.0f,(double)i))*10;

            if (ptxCur>=0 && ptxCur<this.spaceLength
                    && ptyCur>=0 && ptyCur<this.spaceWidth){
                if (space.getPositionInfo(ptxCur,ptyCur).isHolderEmpty()==false){
                    System.out.println("Error:Overlapping position.");
                }
            }
            else{
                System.out.println("Error:Out of boundary.");
                return;
            }
        }

        ptyCur=this.beginPt.getPtY();
        for(int i=0;i<this.queueLength;i++){
            int ptxCur=this.beginPt.getPtX()-i*10;
            ptyCur=ptyCur-10*(int)(Math.pow(-1.0f,(double)i));

            space.getPositionInfo(ptxCur,ptyCur)
                    .setHolder(queue.getCreatureInfo(i).getHolder());
        }

    }
}
