package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthDto implements Serializable {

    private Integer id;

    private String roleName;

    private String roleUrl;
}
