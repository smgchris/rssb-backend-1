package com.rssb.backend1.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "id"), name = "uploads")
public class FileUpload {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id")
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private String imageFileName;

    @Column(name = "uploadtime")
    private Long  uploadTime=new Date().getTime();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uploader_id", referencedColumnName = "id")
    private User uploader;


}
