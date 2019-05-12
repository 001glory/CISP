package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    private String remarks;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    private Integer state;

    private Integer sort;


}
