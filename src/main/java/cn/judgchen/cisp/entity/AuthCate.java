package cn.judgchen.cisp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auth_cate")
public class AuthCate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cate_name")
    private String  cateName;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "is_show")
    private Integer isShow;

    private String remarks;

    private Integer sort;
}
