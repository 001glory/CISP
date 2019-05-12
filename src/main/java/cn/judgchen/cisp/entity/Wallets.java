package cn.judgchen.cisp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "wallets")
public class Wallets implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    private Integer type;

    @Column(name = "income_total")
    private double incomeTotal;

    private double  cash;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private Integer state;
}
