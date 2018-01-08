package formation;
import formation.ChangShe;
import org.junit.Test;

import space.Space;
import queue.QueueArray;
import space.Coordinate;

public class FormationTest {
    @Test
    public void testFormation(){
        Space spaceTMP=new Space(10,10);
        QueueArray queueTMP=new QueueArray(5);
        ChangShe formationTMP=new ChangShe();
        formationTMP.formation(queueTMP,new Coordinate(5,2),spaceTMP);
        assert(formationTMP.getQueueLength()==5);
        assert(formationTMP.getSpaceLength()==10);
        assert(formationTMP.getSpaceWidth()==10);
    }
}
