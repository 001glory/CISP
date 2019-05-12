package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "area")
public class Area implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Integer pkId;

    private Integer atype;

    private String name;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    private Integer sort;

}
