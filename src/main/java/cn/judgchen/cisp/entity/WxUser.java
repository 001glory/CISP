package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "wxuser")
public class WxUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    private String openid;

    private String province;

    private String city;

    private String phone;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    private double payment;

    private Integer gender;

    private String dphone;

    private Integer auth;

    @Column(name = "default_address")
    private Integer defaultAddress;

    @Column(name = "role_id")
    private Integer roleId;
}
