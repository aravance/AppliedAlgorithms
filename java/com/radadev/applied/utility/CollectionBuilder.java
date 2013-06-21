package com.radadev.applied.utility;

import java.util.Collection;
import java.util.Collections;

public class CollectionBuilder<T, C extends Collection<T>> {

    private C mCollection;

    CollectionBuilder(C collection) {
        mCollection = collection;
    }

    public static <T, C extends Collection<T>> CollectionBuilder<T, C> newBuilder(C collection) {
        return new CollectionBuilder<>(collection);
    }

    public CollectionBuilder<T, C> with(T t) {
        mCollection.add(t);
        return this;
    }

    public C build() {
        return mCollection;
    }

    public Collection<T> buildUnmodifiable() {
        return Collections.unmodifiableCollection(build());
    }
}
