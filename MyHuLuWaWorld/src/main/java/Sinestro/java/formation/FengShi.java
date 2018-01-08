package formation;

import queue.QueueArray;
import space.*;


public class FengShi implements Formation {
    private int queueLength;
    private int spaceLength;
    private int spaceWidth;
    private int arrowLength;
    private Coordinate beginPt;

    @Override
    public void formation(QueueArray queue, Coordinate startpt, Space space){
        this.spaceLength=space.getSpaceLength();
        this.spaceWidth=space.getSpaceWidth();
        this.queueLength=queue.getQueueSize();
        this.arrowLength=(this.queueLength-1)/3;
        this.beginPt=startpt;

        for(int i=0;i<this.queueLength;i++){
            if(i<this.arrowLength*2+1){
                int ptxCur=this.beginPt.getPtX()-(int)((i+1)/2)*10;
                int ptyCur=this.beginPt.getPtY()+(int)((i+1)/2*Math.pow(-1.0f,(double)i))*10;

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
            else{
                int ptxCur=this.beginPt.getPtX()-(this.arrowLength+(i-this.arrowLength*2-1))*10;
                int ptyCur=this.beginPt.getPtY();

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
        }

        for(int i=0;i<this.queueLength;i++){
            if(i<this.arrowLength*2+1){
                int ptxCur=this.beginPt.getPtX()-(int)((i+1)/2)*10;
                int ptyCur=this.beginPt.getPtY()+(int)((i+1)/2*Math.pow(-1.0f,(double)i))*10;

                space.getPositionInfo(ptxCur,ptyCur)
                        .setHolder(queue.getCreatureInfo(i).getHolder());
            }
            else{
                int ptxCur=this.beginPt.getPtX()-(this.arrowLength+(i-this.arrowLength*2-1))*10;
                int ptyCur=this.beginPt.getPtY();

                space.getPositionInfo(ptxCur,ptyCur)
                        .setHolder(queue.getCreatureInfo(i).getHolder());
            }
        }

    }
}
