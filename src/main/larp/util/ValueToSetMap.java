package larp.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValueToSetMap<K, V>
{
    private Map<K, Set<V>> map;

    public ValueToSetMap()
    {
        this.map = new HashMap<K, Set<V>>();
    }

    public Set<V> get(K key)
    {
        if (!this.map.containsKey(key))
        {
            return new HashSet<V>();
        }

        return this.map.get(key);
    }

    public void put(K key, V value)
    {
        Set<V> existingSet = this.map.get(key);
        if (existingSet == null)
        {
            existingSet = new HashSet<V>();
        }
        existingSet.add(value);
        this.map.put(key, existingSet);
    }

    public Set<Map.Entry<K, Set<V>>> entrySet()
    {
        return this.map.entrySet();
    }
}
