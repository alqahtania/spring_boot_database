package com.abdull.database.mappers;

import java.util.List;

public interface Mapper<A, B> {
    B mapTo(A a);

    A mapFrom(B b);

    List<B> mapTo(List<A> aList);

    List<A> mapFrom(List<B> bList);
}
