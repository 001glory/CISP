package cn.judgchen.cisp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "y_logs")
public class Log implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uid;

    @Column(name = "fi_table")
    private String fiTable;

    @Column(name = "table_id")
    private String tableId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private String des;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "op_code")
    private String opCode;

    @Column(name = "is_delete")
    private Integer isDelete;
}
