
package com.furiafan.chat.api.draft5.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiMatchResponse {
    private String status;
    private ApiMatchData data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApiMatchData getData() {
        return data;
    }

    public void setData(ApiMatchData data) {
        this.data = data;
    }
}









