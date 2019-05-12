package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name ="calousels")
public class Calouels implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cover;

    @Column(name = "admin_id")
    private Integer adminId;


    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "is_show")
    private Integer isShow;

    @Column(name = "create_time")
    private LocalDateTime createTime;


    private Integer sort;

    private String path;

    private String company;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private String remark;

    @Column(name = "a_id")
    private Integer aId;
}
