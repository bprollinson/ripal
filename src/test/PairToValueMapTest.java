import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.util.PairToValueMap;

public class PairToValueMapTest
{
    @Test
    public void testGetReturnsNullForNonExistentKeyPair()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();

        assertEquals(null, pairToValueMap.get("a", "b"));
    }

    @Test
    public void testGetReturnsNullWhenOnlyFirstKeyMatchesSomeValue()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);

        assertEquals(null, pairToValueMap.get("a", "c"));
    }

    @Test
    public void testGetReturnsNullWhenOnlySecondKeyMatchesSomeValue()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);

        assertEquals(null, pairToValueMap.get("c", "b"));
    }

    @Test
    public void testGetReturnsValueForValidKeyPair()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);

        assertEquals(new Integer(1), pairToValueMap.get("a", "b"));
    }

    @Test
    public void testGetReturnsValueForValidKeyPairWhenFirstKeyUsedMultipleTimes()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);
        pairToValueMap.put("a", "c", 2);

        assertEquals(new Integer(2), pairToValueMap.get("a", "c"));
    }

    @Test
    public void testGetReturnsValueForValidKeyPairWhenSecondKeyUsedMultipleTimes()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);
        pairToValueMap.put("c", "b", 2);

        assertEquals(new Integer(2), pairToValueMap.get("c", "b"));
    }

    @Test
    public void testGetReturnsValueForNullFirstKey()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put(null, "b", 1);

        assertEquals(new Integer(1), pairToValueMap.get(null, "b"));
    }

    @Test
    public void testGetReturnsValueForNullSecondKey()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", null, 1);

        assertEquals(new Integer(1), pairToValueMap.get("a", null));
    }

    @Test
    public void testHasValueForFirstKeyReturnsTrueWhenValuePresent()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testHasValueForFirstKeyReturnsFalseWhenValueNotPresent()
    {
        assertEquals(0, 1);
    }
}
