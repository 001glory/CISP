package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import javax.print.attribute.standard.MediaSize;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cash_recode")
public class Cash implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    @Column(name = "cash_free")
    private double cashFree;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private Integer state;

    private Integer type;

    private String msg;

    @Column(name = "trade_no")
    private String tradeNo;
}
