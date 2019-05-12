package cn.judgchen.cisp.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminUserInfo implements Serializable {

    private String username;

    private String user_sate;

    private LocalDateTime create_time;

    private String phone;
}
