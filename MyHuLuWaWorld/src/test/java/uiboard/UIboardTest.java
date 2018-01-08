package uiboard;

import org.junit.Test;
import uiboard.Thing2D;

public class UIboardTest {
    @Test
    public void testThing2D(){
        Thing2D thing2dTMP=new Thing2D("HuLuWa1.png" ,10, 10, 100, 100);
        assert(thing2dTMP.getX()==10);
        assert(thing2dTMP.getY()==10);
        assert(thing2dTMP.getLength()==100);
        assert(thing2dTMP.getWidth()==100);
    }
}
