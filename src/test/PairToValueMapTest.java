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
        assertEquals(0, 1);
    }

    @Test
    public void testGetReturnsNullWhenOnlySecondKeyMatchesSomeValue()
    {
        assertEquals(0, 1);
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
