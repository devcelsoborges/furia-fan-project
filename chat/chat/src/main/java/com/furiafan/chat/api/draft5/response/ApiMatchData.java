package com.furiafan.chat.api.draft5.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMatchData {
    private List<ApiMatch> list;
    private int totalItems;


    public List<ApiMatch> getList() {
        return list;
    }

    public void setList(List<ApiMatch> list) {
        this.list = list;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}