package com.github.kozmo.photostorage.controller;

import java.util.Objects;

public final class NextPreviousMove {
    private final long left;
    private final long right;

    NextPreviousMove(long left, long right) {
        this.left = left;
        this.right = right;
    }

    static NextPreviousMove fromSkipAndLimit(Long skip, Long limit) {
        if (Objects.requireNonNull(skip) < 0) {
            skip = Math.abs(skip);
            return new NextPreviousMove(skip - limit, skip);
        } else {
            return new NextPreviousMove(skip, skip + limit);
        }
    }

    /**
     *
     * @return long - current value to skip
     */
    public long skip(){
        return left;
    }

    long left() {
        return left;
    }

    long right() {
        return right;
    }

    @Override
    public String toString() {
        return "NextPreviousMove{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
