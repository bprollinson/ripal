package larp.util;

import java.util.HashMap;
import java.util.HashSet;

public class SetMap<K, V>
{
    private HashMap<K, HashSet<V>> map;

    public SetMap()
    {
        this.map = new HashMap<K, HashSet<V>>();
    }

    public HashSet<V> get(K key)
    {
        return this.map.get(key);
    }

    public void put(K key, V value)
    {
        HashSet<V> existingSet = this.map.get(key);
        if (existingSet == null)
        {
            existingSet = new HashSet<V>();
        }
        existingSet.add(value);
        map.put(key, existingSet);
    }
}
