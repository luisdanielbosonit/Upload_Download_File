package com.bosonit.trainig.Upload_Download_File.moldel;

import lombok.Data;

@Data
public class FileInfo {

                                 //CONTIENE INFORMACION SOBRE EL ARCHIVO
                                 private String name;
    private String url;

    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

}
