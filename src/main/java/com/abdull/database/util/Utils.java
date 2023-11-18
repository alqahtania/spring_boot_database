package com.abdull.database.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Utils {

    private Utils() {
    }

    public static <S> List<S> convertIterableToList(Iterable<S> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
