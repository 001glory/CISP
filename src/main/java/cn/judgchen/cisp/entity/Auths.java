package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auths")
public class Auths implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cate_id")
    private Integer cateId;

    @Column(name = "auth_name")
    private String authName;

    @Column(name = "auth_url")
    private String authUrl;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    private String remarks;

}
