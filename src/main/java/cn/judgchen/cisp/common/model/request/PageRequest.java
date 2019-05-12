package cn.judgchen.cisp.common.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

@Data
public class PageRequest implements BaseRequest {

    @Min(value = 1, message = "页码不能小于1")
    private Integer page = 1;

    @Range(min = 1, max = 100, message = "分页大小限制在1-100条")
    private Integer pageSize = 10;
}
