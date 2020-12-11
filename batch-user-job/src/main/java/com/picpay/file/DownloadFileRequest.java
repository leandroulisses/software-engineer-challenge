package com.picpay.file;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloadFileRequest {

    private String fileName;
    private URL url;

    public DownloadFileRequest(String fileName, String url) {
        this.fileName = fileName;
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("The download url of file is invalid", e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public URL getUrl() {
        return url;
    }

}
