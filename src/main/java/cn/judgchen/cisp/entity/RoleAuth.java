package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "role_auth")
public class RoleAuth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "auth_id")
    private Integer authId;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private LocalDateTime crateTime;


}
