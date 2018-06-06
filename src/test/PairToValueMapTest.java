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
        assertEquals(0, 1);
    }

    @Test
    public void testGetReturnsValueForNullFirstKey()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testGetReturnsValueForNullSecondKey()
    {
        assertEquals(0, 1);
    }
}
