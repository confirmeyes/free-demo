package entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Region)实体类
 *
 * @author makejava
 * @since 2020-12-16 15:16:53
 */

@Data
public class Region implements Serializable {
    private static final long serialVersionUID = 446410755632007002L;
    /**
    * 区域主键
    */
    @ExcelProperty(value = "区域主键")
    private Integer id;
    /**
    * 区域名称
    */
    @ExcelProperty(value = "区域名称")
    private String name;
    /**
    * 区域上级标识
    */
    @ExcelProperty(value = "区域上级标识")
    private Integer pid;
    /**
    * 地名简称
    */
    @ExcelProperty(value = "地名简称")
    private String sname;
    /**
    * 区域等级
    */
    @ExcelProperty(value = "区域等级")
    private Integer level;
    /**
    * 区域编码
    */
    @ExcelProperty(value = "区域编码")
    private String citycode;
    /**
    * 邮政编码
    */
    @ExcelProperty(value = "邮政编码")
    private String yzcode;
    /**
    * 组合名称
    */
    @ExcelProperty(value = "组合名称")
    private String mername;

    @ExcelProperty(value = "lng")
    private Float lng;

    @ExcelProperty(value = "lat")
    private Float lat;

    @ExcelProperty(value = "拼音")
    private String pinyin;

}