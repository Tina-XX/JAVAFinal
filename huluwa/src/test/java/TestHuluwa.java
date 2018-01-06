import org.junit.*;
import nju.huluwa.Field;
import nju.huluwa.Grandpa;
import nju.huluwa.Map;
import static org.junit.Assert.fail;


public class TestHuluwa {
    Field field;
    Grandpa yeye;
    Map map;
    @BeforeClass
    public static void classBefore()
    {
        System.out.println("Test Begin:\n");
    }
    @AfterClass
    public static void classAfter()
    {
        System.out.println("Test End:\n");
    }
    @Test
    public void TestEnd() throws Exception {
        field = new Field();
        field.startGame();
        assert !field.isDeadAll();
    }
    public void OutOfMap(Grandpa yeye){
        if(yeye.getX()>14||yeye.getY()>14)
            fail("Position out of map!");
    }
    @Test
    public void TestPosition() throws Exception {
        map=new Map(15,15);
        yeye=new Grandpa(map,field);
        yeye.setXY(0,0);
        for(int i=0;i<15;i++)
            OutOfMap(yeye);
    }


}
