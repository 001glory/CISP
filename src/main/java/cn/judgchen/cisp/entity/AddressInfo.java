package cn.judgchen.cisp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "address_info")
public class AddressInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "cate_id")
    private Integer cateId;

    @Column(name = "a_id")
    private Integer aId;

    private Integer sort;

    @Column(name = "sub_name")
    private String subName;

    @Column(name = "is_delete")
    private Integer isDelete;

}
