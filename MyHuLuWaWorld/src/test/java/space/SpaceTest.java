package space;

import org.junit.Test;

public class SpaceTest {
    @Test
    public void testSpace(){
        Space spaceTMP=new Space(10,10);
        assert(spaceTMP.getSpaceLength()==10);
        assert(spaceTMP.getSpaceWidth()==10);
    }
}
