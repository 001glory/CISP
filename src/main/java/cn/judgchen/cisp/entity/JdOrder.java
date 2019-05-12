package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JdOrder implements Serializable {

    private Long total;

    private int state;

    public JdOrder(Long total, int state) {
        this.total = total;
        this.state = state;
    }
}
