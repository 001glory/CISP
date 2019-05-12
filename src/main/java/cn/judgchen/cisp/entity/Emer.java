package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Emer implements Serializable {

    private Integer openEmer;

    private String emerTitle;

    private String emerContent;
}
