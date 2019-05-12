package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JdOrderDaily implements Serializable {

    private Long total;

    private int state;

    public JdOrderDaily(Long total, int state) {
        this.total = total;
        this.state = state;
    }
}
