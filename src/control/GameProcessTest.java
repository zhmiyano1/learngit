package control;

import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.Method;
import static org.junit.Assert.*;


public class GameProcessTest {

    public static GameProcess gameControl;

    @BeforeClass
    public static void beforeClass() {
        gameControl = new GameProcess(10, 10);
    }

    @Test
    public void testGameProcess() {
        GameProcess newGame = new GameProcess(10, 10);
        assertNotSame(gameControl, newGame);
    }

    @Test
    public void testChangeCellStatus() {
        boolean status;
        int row = 5, col = 5;
        status = gameControl.changeCellStatus(row, col);

        assertNotSame(status, gameControl.world.getCellStatus(row, col));
    }

    @Test
    public void testCellSavePrinciple() throws Exception {
        Method cellSavePrinciple;

        Class rc = Class.forName("control.GameProcess");
        Object obj = rc.newInstance();
        System.out.println(rc.toString());
        cellSavePrinciple = rc.getDeclaredMethod("cellSavePrinciple", boolean.class, int.class);
        System.out.println(cellSavePrinciple.toString());
        cellSavePrinciple.setAccessible(true);
        assertTrue((boolean) cellSavePrinciple.invoke(gameControl, false, 3));
        assertEquals(true, (boolean) cellSavePrinciple.invoke(gameControl, true, 2));
        assertFalse((boolean) cellSavePrinciple.invoke(gameControl, true, 1));
    }

}
