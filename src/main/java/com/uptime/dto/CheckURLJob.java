package com.uptime.dto;

import com.uptime.model.Monitor;
import lombok.*;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckURLJob {

    String url;
    Monitor monitor;
    @Builder.Default
    Boolean result = Boolean.FALSE;


    public void execute(){

        try {
            URL siteURL = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            int code = connection.getResponseCode();
            this.result = code==200;
        } catch (Exception ignored) {
            this.result = false;
        }
    }

}
