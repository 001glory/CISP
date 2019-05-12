package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "address_cate")
public class AddressCate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer sort;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "is_show")
    private Integer isShow;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "a_id")
    private Integer aId;
}
