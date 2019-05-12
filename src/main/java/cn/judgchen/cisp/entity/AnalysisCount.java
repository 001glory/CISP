package cn.judgchen.cisp.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AnalysisCount implements Serializable {

    private String time;

    private Integer total;

    private String state;
}
