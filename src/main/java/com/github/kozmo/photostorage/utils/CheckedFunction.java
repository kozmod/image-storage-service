package com.github.kozmo.photostorage.utils;

import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
@Deprecated
public interface CheckedFunction<T, R> {

    R apply(T t) throws Exception;

    static <T, R> Function<T, R> func(CheckedFunction<T, R> func) {
        return t -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static <T> Predicate<T> predicate(CheckedFunction<T, Boolean> func) {
        return t -> {
            try {
                return func.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
