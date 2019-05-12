package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "userinfo")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wx_id")
    private Integer wxId;

    private String name;

    @Column(name = "card_num")
    private String cardNum;

    private String cert;

    @Column(name = "a_id")
    private Integer aId;

    private Integer state;

    private String msg;

    @Column(name = "stu_card")
    private String stuCard;

    @Column(name = "form_id")
    private String formId;
}