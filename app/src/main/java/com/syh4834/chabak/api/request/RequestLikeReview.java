package com.syh4834.chabak.api.request;

public class RequestLikeReview {
    /**
     * {
     *    "reviewIdx" : 1,
     * }
     */

    private int reviewIdx;

    public RequestLikeReview(int reviewIdx) {
        this.reviewIdx = reviewIdx;
    }
}
