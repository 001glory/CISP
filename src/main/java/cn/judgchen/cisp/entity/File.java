package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sw_file")
public class File implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Integer fileSize;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "group_id")
    private Integer groupId;

    private String mimetype;
}
