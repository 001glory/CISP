package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "dl_server")
public class DLServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true,name = "dl_id")
    private Integer dlId;

    @Column(nullable = true,name = "server_name")
    private String serverName;

    @Column(nullable = true,name = "dl_sy")
    private double dlSy;

    @Column(nullable = true,name = "user_sy")
    private double userSy;

    @Column(nullable = true,name = "p_sy")
    private double pSy;

    @Column(nullable = true,name = "create_time")
    private LocalDateTime createTime;

    @Column(nullable = true,name = "is_show")
    private Integer isShow;

    @Column(nullable = true,name = "price_gui")
    private String priceGui;

    @Column(nullable = true)
    private String des;

    @Column(nullable = true)
    private String icon;

    @Column(nullable = true)
    private String jdr;

    @Column(nullable = true,name = "rate_id")
    private Integer rateId;

    @Column(nullable = true)
    private String tags;
}
