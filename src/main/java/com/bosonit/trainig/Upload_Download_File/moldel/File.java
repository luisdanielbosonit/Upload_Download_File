package com.bosonit.trainig.Upload_Download_File.moldel;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
public class File {

                                 //CONTIENE INFORMACION SOBRE EL ARCHIVO

    @Id
    @GeneratedValue(generator = "file-seq")
    @GenericGenerator(name = "file-seq", strategy = "com.bosonit.trainig.Upload_Download_File.MiGenerator")
    @Column(name = "id")
    public String id;
    private String name;
    private String url;
    private String type;

    @Lob
    private byte[] data;

    LocalDateTime dateTime;



    public File(String name, String type, byte[] data,String url,LocalDateTime dateTime) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.url= url;
        this.dateTime= dateTime;
    }
}
