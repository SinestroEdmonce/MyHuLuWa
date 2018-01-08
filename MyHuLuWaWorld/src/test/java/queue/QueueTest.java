package queue;

import org.junit.Test;

import space.Space;
import queue.QueueArray;
import space.Coordinate;

public class QueueTest {
    @Test
    public void testQueue(){
        QueueArray queueTMP=new QueueArray(7);
        assert(queueTMP.getQueueSize()==7);
    }
}
