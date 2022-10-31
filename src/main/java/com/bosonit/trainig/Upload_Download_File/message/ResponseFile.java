package com.bosonit.trainig.Upload_Download_File.message;

import lombok.Data;

@Data
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;

    public ResponseFile(String name, String url, String type, long size) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }
}
