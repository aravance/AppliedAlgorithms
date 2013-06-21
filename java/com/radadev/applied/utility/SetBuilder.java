package com.radadev.applied.utility;

import java.util.Collections;
import java.util.Set;

public class SetBuilder<T> extends CollectionBuilder<T, Set<T>> {

    SetBuilder(Set<T> set) {
        super(set);
    }

    public static <T> SetBuilder<T> newBuilder(Set<T> set) {
        return new SetBuilder<>(set);
    }

    @Override
    public SetBuilder<T> with(T t) {
        super.with(t);
        return this;
    }

    @Override
    public Set<T> buildUnmodifiable() {
        return Collections.unmodifiableSet(build());
    }
}
