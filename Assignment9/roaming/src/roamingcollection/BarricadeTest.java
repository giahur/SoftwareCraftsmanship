package roaming.src.roamingcollection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.logging.Logger;

// Test class for Barricade
public class BarricadeTest {

    private final Logger logger = Logger.getLogger(Barricade.class.getName());
    private final LoggerTestingHandler handler = new LoggerTestingHandler();

    @Before
    public void setUp() {
        logger.addHandler(handler);
    }

    public RoamingMap<Integer, Integer> roamingMap() {
        RoamingMap<Integer, Integer> roamingMap = new RoamingMap<>();
        roamingMap.put(1, 2);
        roamingMap.put(2, 4);
        roamingMap.put(3, 6);
        roamingMap.put(4, 8);
        roamingMap.put(2, 500);
        return roamingMap;
    }

    public RoamingMap<Integer, Integer> roamingMap2() {
        RoamingMap<Integer, Integer> roamingMap = new RoamingMap<>();
        roamingMap.put(1, 1);
        roamingMap.put(2, 3);
        roamingMap.put(3, 5);
        roamingMap.put(4, 1);
        roamingMap.put(5, 3);
        roamingMap.put(6, 1);
        roamingMap.put(7, 2);
        return roamingMap;
    }

    public RoamingMap<Integer, Integer> roamingMap3() {
        RoamingMap<Integer, Integer> roamingMap = new RoamingMap<>();
        roamingMap.put(1, 1);
        roamingMap.put(2, 1);
        roamingMap.put(3, 1);
        roamingMap.put(4, 1);
        roamingMap.put(5, 1);
        roamingMap.put(6, 1);
        roamingMap.put(7, 1);
        roamingMap.put(8, 2);
        return roamingMap;
    }

    public RoamingMap<Integer, Integer> roamingMap4() {
        RoamingMap<Integer, Integer> roamingMap = new RoamingMap<>();
        roamingMap.put(1, 1);
        roamingMap.put(2, 1);
        roamingMap.put(3, 1);
        roamingMap.put(4, 1);
        roamingMap.put(5, 1);
        roamingMap.put(6, 1);
        roamingMap.put(7, 2);
        roamingMap.put(8, 1);
        roamingMap.put(9, 2);
        return roamingMap;
    }

    // Testing methods starting here
    @Test
    public void testGetWithStateVar() {
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        RoamingMap<Integer, Integer> roamingMap2 = roamingMap2();
        RoamingMap<Integer, Integer> roamingMap3 = roamingMap3();
        RoamingMap<Integer, Integer> roamingMap4 = roamingMap4();
        //Test 1 (CC1, CC4, GD1, GD2): nonnull roaming map, nonnull key
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(2, null), Barricade.getWithStateVar(roamingMap, 1));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(6, null), Barricade.getWithStateVar(roamingMap, 3));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(8, null), Barricade.getWithStateVar(roamingMap, 4));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(null, null), Barricade.getWithStateVar(roamingMap, 5));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(3, null), Barricade.getWithStateVar(roamingMap2, 5));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(1, null), Barricade.getWithStateVar(roamingMap4, 5));
        //Test 2 (CC2): entrySetBefore != entrySetAfter
        assertThrows(RuntimeException.class, () -> Barricade.getWithStateVar(roamingMap3, 2));
        //Test 3 (CC3): prevValue != value
        handler.clearLogRecords();
        Barricade.getWithStateVar(roamingMap, 2);
        assertTrue(handler.getLastLog().get().contains("get method of RoamingMap returned incorrect value; correct value was used instead"));
        //Test 4 (BD1): null roaming map
        assertThrows(NullPointerException.class, () -> Barricade.getWithStateVar(null, 2));
        //Test 5 (BD2): null key
        assertThrows(NullPointerException.class, () -> Barricade.getWithStateVar(roamingMap, null));
    }

    @Test
    public void testCorrectSize() {
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        RoamingMap<Integer, Integer> roamingMap2 = roamingMap2();
        RoamingMap<Integer, Integer> roamingMap3 = roamingMap3();
        RoamingMap<Integer, Integer> roamingMap4 = roamingMap4();
        //Test 1 (CC1, CC4): nonnull roaming map
        assertEquals(4, Barricade.correctSize(roamingMap));
        assertEquals(7, Barricade.correctSize(roamingMap2));
        //Test 2 (CC2): entrySetBefore != entrySetAfter
        assertThrows(RuntimeException.class, () -> Barricade.correctSize(roamingMap3));
        //Test 3 (CC3): prevSize != size
        handler.clearLogRecords();
        Barricade.correctSize(roamingMap4);
        assertTrue(handler.getLastLog().get().contains("size method of RoamingMap returned incorrect value; correct value was used instead"));
        //Test 4 (BD1): null roaming map
        assertThrows(NullPointerException.class, () -> Barricade.correctSize(null));
    }

    @Test
    public void testPutWithStateVar() {
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        RoamingMap<Integer, Integer> roamingMap3 = roamingMap3();
                RoamingMap<Integer, Integer> roamingMap2 = roamingMap2();

        RoamingMap<Integer, Integer> roamingMap4 = roamingMap4();

        //Test 1 (CC1, GD1): nonnull roamingmap, key, value, updatedvalue=value, prevRoamingSet=newRoamingSet
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(2, null), Barricade.putWithStateVar(roamingMap, 1, 2));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(6, null), Barricade.putWithStateVar(roamingMap, 3, 6));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(5, null), Barricade.putWithStateVar(roamingMap2, 3, 6));
        assertEquals(new Barricade.StateRecoveryOptional<Integer>(1, null), Barricade.putWithStateVar(roamingMap4, 3, 6));
        //Test 2 (CC2, B1): updatedvalue != value, prevRoamingSet != newRoamingSet
        assertThrows(RuntimeException.class, () -> Barricade.putWithStateVar(roamingMap3, 1, 3));
        //Test 3 (BD1): null roamingmap
        assertThrows(NullPointerException.class, () -> Barricade.putWithStateVar(null, 1, 2));
        //Test 4 (BD2): null key
        assertThrows(NullPointerException.class, () -> Barricade.putWithStateVar(roamingMap, null, 2));
        //Test 5 (BD3): null value
        assertThrows(NullPointerException.class, () -> Barricade.putWithStateVar(roamingMap, 1, null));
    }

    @Test
    public void testCorrectKeySet() {
        //Test 1 (CC1): nonnull roaming map
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        assertEquals(roamingMap.keySet(), Barricade.correctKeySet(roamingMap));
        //Test 2 (BD1): null roaming map
        assertThrows(NullPointerException.class, () -> Barricade.correctKeySet(null));
    }

    @Test
    public void testCorrectEntrySet() {
        //Test 1 (CC1): nonnull roaming map
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        assertEquals(roamingMap.entrySet(), Barricade.correctEntrySet(roamingMap));
        //Test 2 (BD1): null roaming map
        assertThrows(NullPointerException.class, () -> Barricade.correctEntrySet(null));
    }

    @Test
    public void testCorrectStringRepresentation() {
        RoamingMap<Integer, Integer> roamingMap = roamingMap();
        RoamingMap<Integer, Integer> roamingMap2 = roamingMap2();
        RoamingMap<Integer, Integer> roamingMap3 = roamingMap3();
        //Test 1 (CC1): nonnull roaming map
        assertEquals("{1=2, 2=500, 3=6, 4=8}", Barricade.correctStringRepresentation(roamingMap));
        //Test 2 (CC2): roamingSetBefore != roamingSetAfter
        assertThrows(RuntimeException.class, () -> Barricade.correctStringRepresentation(roamingMap3));
        //Test 3 (CC3): prevRepresentation != representation
        handler.clearLogRecords();
        Barricade.correctStringRepresentation(roamingMap2);
        assertTrue(handler.getLastLog().get().contains("toString method of RoamingMap returned incorrect value; correct value was used instead"));
        //Test 4 (BD1): null roaming map
        assertThrows(NullPointerException.class, () -> Barricade.correctStringRepresentation(null));
    }
}