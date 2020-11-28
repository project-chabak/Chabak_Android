package com.syh4834.chabak.api.request;

public class RequestReport {
    /**
     * {
     *    "name" : "여행지 이름",
     *    "address" : "여행지 주소",
     *    "content" : "여행지 설명",
     *    "toilet" : "0" or "1",
     *    "store" : "0" or "1",
     *    "cooking" : "0" or "1",
     * }
     */

    private String name;
    private String address;
    private String content;
    private String toilet;
    private String store;
    private String cooking;

    public RequestReport(String name, String address, String content, String toilet, String store, String cooking) {
        this.name = name;
        this.address = address;
        this.content = content;
        this.toilet = toilet;
        this.store = store;
        this.cooking = cooking;
    }
}
