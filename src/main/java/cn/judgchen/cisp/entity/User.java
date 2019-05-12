package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "y_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private  Integer pkId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String pwd;

    @Column(name = "a_id")
    private Integer aId;

    private Integer dtype;

    @Column(name = "user_state")
    private String userState;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    @Column(name = "is_delete")
    private Integer isDelete;

    private Double payment;

    private String phone;

    private LocalDateTime deadline;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "open_emer")
    private  Integer openEmer;

    @Column(name = "emer_title")
    private String emerTitle;

    @Column(name = "emer_content")
    private String emerContent;



}
