package sorter;
import compare.Compare;
import queue.QueueArray;
import space.*;
import creature.*;

public class QuickSort implements Sorter {
    @Override
    public void Sort(QueueArray queue){
        int queueLength=queue.getQueueSize();
        this.QuickSort(queue,0,queueLength-1);
    }

    protected void QuickSort(QueueArray queue, int qleft, int qright){
        if (qleft<qright){
            int mid=this.Partition(queue,qleft,qright);
            this.QuickSort(queue,qleft,mid-1);
            this.QuickSort(queue,mid+1,qright);
        }
    }

    protected int Partition(QueueArray queue, int qleft, int qright){
        int splitpt=qleft;
        PositionInfo pivot;
        pivot=queue.getCreatureInfo(splitpt);

        for (int i=qleft+1;i<=qright;i++){
            if  (((Compare)(queue.getCreatureInfo(i).getHolder())).isLargerThan
                    ((Compare)(pivot.getHolder()))==true){
                splitpt++;
                Creature tmp= queue.getCreatureInfo(i).getHolder();
                queue.getCreatureInfo(i).setHolder(queue.getCreatureInfo(splitpt).getHolder());
                queue.getCreatureInfo(splitpt).setHolder(tmp);
            }
        }
        queue.getCreatureInfo(qleft).setHolder(queue.getCreatureInfo(splitpt).getHolder());
        queue.getCreatureInfo(splitpt).setHolder(pivot.getHolder());
        return splitpt;
    }
}
