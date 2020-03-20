/*
 * Copyright (C) 2017 Brendan Rollinson-Lorimer
 *
 * This library is licensed under LGPL v2.1.
 * See LICENSE.md for details.
 */

package ripal.util;

import java.util.HashMap;
import java.util.Map;

public class PairToValueMap<K1, K2, V>
{
    private Map<K1, Map<K2, V>> map;

    public PairToValueMap()
    {
        this.map = new HashMap<K1, Map<K2, V>>();
    }

    public V get(K1 key1, K2 key2)
    {
        V value = null;
        Map<K2, V> entry = this.map.get(key1);
        if (entry != null)
        {
            value = entry.get(key2);
        }

        return value;
    }

    public void put(K1 key1, K2 key2, V value)
    {
        Map<K2, V> entry = this.map.get(key1);
        if (entry == null)
        {
            entry = new HashMap<K2, V>();
        }

        entry.put(key2, value);
        this.map.put(key1, entry);
    }

    public boolean hasValueForFirstKey(K1 key1)
    {
        return this.map.containsKey(key1);
    }

    public Map<K1, Map<K2, V>> getMap()
    {
        return this.map;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof PairToValueMap))
        {
            return false;
        }

        return this.map.equals(((PairToValueMap)other).getMap());
    }
}
