package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "address_user")
public class AddressUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    private String detail;

    @Column(name = "wx_id")
    private Integer wxId;

    @Column(name = "is_delete")
    private Integer idDelete;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
