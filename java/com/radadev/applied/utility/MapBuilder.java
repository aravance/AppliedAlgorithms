package com.radadev.applied.utility;

import java.util.Collections;
import java.util.Map;

public class MapBuilder<K, V> {

    private Map<K, V> mMap;

    MapBuilder(Map<K, V> map) {
        mMap = map;
    }

    public static <K, V> MapBuilder<K, V> newBuilder(Map<K, V> map) {
        return new MapBuilder<>(map);
    }

    public MapBuilder<K, V> with(K key, V value) {
        mMap.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return mMap;
    }

    public Map<K, V> buildUnmodifiable() {
        return Collections.unmodifiableMap(mMap);
    }
}
