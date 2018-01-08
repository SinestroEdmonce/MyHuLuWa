package formation;

import queue.QueueArray;
import space.*;

public class YanXing implements Formation {
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
            int ptxCur=beginPt.getPtX()-i*10;
            int ptyCur=beginPt.getPtY()+i*10;

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

        for (int i=0;i<this.queueLength;i++){
            int ptxCur=beginPt.getPtX()-i*10;
            int ptyCur=beginPt.getPtY()+i*10;

            space.getPositionInfo(ptxCur,ptyCur)
                    .setHolder(queue.getCreatureInfo(i).getHolder());
        }
    }
}
