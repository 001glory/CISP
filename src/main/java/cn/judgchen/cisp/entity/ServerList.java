package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "helplist")
public class ServerList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wx_id")
    private Integer wxId;

    private String title;

    private Integer state;

    private String des;

    @Column(name = "is_delete")
    private Integer isDelete;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "a_id")
    private Integer aId;

    @Column(name = "total_fee")
    private double totalFee;

    @Column(name = "form_id")
    private String formId;

    private String mu;

    private String qi;

    private String file;

    @Column(name = "order_num")
    private String orderNum;

    @Column(name = "is_pay")
    private Integer isPay;

    @Column(name = "jd_id")
    private Integer jdId;

    private String openid;

    private Integer page;

    private String cai;

    @Column(name = "out_refund_no")
    private String outRefundNo;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "jd_time")
    private LocalDateTime jdTime;

    @Column(name = "com_time")
    private LocalDateTime comTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;
}
