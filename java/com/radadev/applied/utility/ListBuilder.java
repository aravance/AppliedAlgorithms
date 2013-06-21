package com.radadev.applied.utility;

import java.util.Collections;
import java.util.List;

public class ListBuilder<T> extends CollectionBuilder<T, List<T>> {

    ListBuilder(List<T> list) {
        super(list);
    }

    public static <T> ListBuilder<T> newBuilder(List<T> list) {
        return new ListBuilder<>(list);
    }

    @Override
    public ListBuilder<T> with(T t) {
        super.with(t);
        return this;
    }

    @Override
    public List<T> buildUnmodifiable() {
        return Collections.unmodifiableList(build());
    }
}
