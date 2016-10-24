package com.my.home.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Cache
 */
public class ObjectsCache<V>
{
    private Map<String, Object> m_cache = new HashMap<>();
    public <V> V get(String key, ObjectBuilder objectBuilder)
    {
        if (m_cache.containsKey(key))
        {
            return (V) m_cache.get(key);
        }
        else
        {
            V value = (V) objectBuilder.build();
            m_cache.put(key, (V) value);
            return value;
        }
    }

    public static interface ObjectBuilder<V>
    {
        <V> V build();
    }
}
