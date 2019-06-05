package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnalysisData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer wxuserTotal;

    private Integer areaTotal;

    private Integer userPass;

    private Integer orderTotal;

    private double turnover;

    private double turnoverDaily;

    private double turnoverMonth;

    private double turnoverYear;

    private Integer dailyUser;

    private Integer wxuserTotalDaily;

    private Integer userWating;

    private Integer userBack;
}
