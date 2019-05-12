package cn.judgchen.cisp.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "capital_trend")
public class CapitalTrend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "a_id")
    private Integer aId;

    @Column(name = "u_id")
    private Integer uId;

    @Column(name = "h_id")
    private Integer hId;

    @Column(name = "p_get")
    private double pGet;

    @Column(name = "u_get")
    private double uGet;

    @Column(name = "a_get")
    private double aGet;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private String rate;
}
