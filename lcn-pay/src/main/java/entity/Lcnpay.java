package entity;

import java.io.Serializable;
import lombok.Data;

/**
 * lcn-pay
 * @author 
 */
@Data
public class Lcnpay implements Serializable {
    private Integer id;

    /**
     * 支付状态
     */
    private Long status;

    private static final long serialVersionUID = 1L;
}