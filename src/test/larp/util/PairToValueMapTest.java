/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package larp.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", null, 1);

        assertTrue(pairToValueMap.hasValueForFirstKey("a"));
    }

    @Test
    public void testHasValueForFirstKeyReturnsFalseWhenValueNotPresent()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", null, 1);

        assertFalse(pairToValueMap.hasValueForFirstKey("b"));
    }

    @Test
    public void testEqualsReturnsTrue()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);
        PairToValueMap<String, String, Integer> otherPairToValueMap = new PairToValueMap<String, String, Integer>();
        otherPairToValueMap.put("a", "b", 1);

        assertTrue(pairToValueMap.equals(otherPairToValueMap));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentFirstKey()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);
        PairToValueMap<String, String, Integer> otherPairToValueMap = new PairToValueMap<String, String, Integer>();
        otherPairToValueMap.put("c", "b", 1);

        assertFalse(pairToValueMap.equals(otherPairToValueMap));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentSecondKey()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);
        PairToValueMap<String, String, Integer> otherPairToValueMap = new PairToValueMap<String, String, Integer>();
        otherPairToValueMap.put("a", "c", 1);

        assertFalse(pairToValueMap.equals(otherPairToValueMap));
    }

    @Test
    public void testEqualsReturnsFalseForObjectWithDifferentClass()
    {
        PairToValueMap<String, String, Integer> pairToValueMap = new PairToValueMap<String, String, Integer>();
        pairToValueMap.put("a", "b", 1);

        assertFalse(pairToValueMap.equals(new Object()));
    }
}
