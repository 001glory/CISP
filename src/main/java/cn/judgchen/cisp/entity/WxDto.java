package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxDto implements Serializable {

    private Integer count;

    private double sum;
}
