package sorter;
import queue.QueueArray;
import compare.*;
import creature.*;

public class BubbleSort implements Sorter {
    @Override
    public void Sort(QueueArray queue){
        int queueLength=queue.getQueueSize();
        for (int i=0;i<queueLength-1;i++){
            for (int j=queueLength-1;j>i;j--){
                if (((Compare)(queue.getCreatureInfo(j).getHolder())).isLargerThan
                        ((Compare)(queue.getCreatureInfo(j-1).getHolder()))==true){
                    Creature tmp=queue.getCreatureInfo(j).getHolder();
                    queue.getCreatureInfo(j).setHolder(queue.getCreatureInfo(j-1).getHolder());
                    queue.getCreatureInfo(j-1).setHolder(tmp);
                }
            }
        }
    }
}
