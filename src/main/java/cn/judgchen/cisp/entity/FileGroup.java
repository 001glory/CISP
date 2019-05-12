package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "sw_file_group")
public class FileGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
