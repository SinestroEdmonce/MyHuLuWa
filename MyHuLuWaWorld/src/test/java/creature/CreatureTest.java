package creature;


import org.junit.Test;

public class CreatureTest {
    @Test
    public void testCreature(){
        HuLuWa huLuWaTMP= new HuLuWa("HuLuWa1.png",0, 0, Role.YI,0, 0, null,1);
        assert(huLuWaTMP.getX()==0);
        assert(huLuWaTMP.getY()==0);
    }
}
