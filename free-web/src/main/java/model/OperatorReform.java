package model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author WIN10 .
 * @create 2020-08-03-11:12 .
 * @description .
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ExcelTarget("operatorReform")
public class OperatorReform implements Serializable {

    private static final long serialVersionUID = -4118537235820669616L;

    @Excel(name = "序号")
    private Integer siteId;

    private String operator;

    private Long userId;

    @Excel(name = "基站名称")
    private String siteName;

    @Excel(name = "基站编号")
    private String siteNumber;

    @Excel(name = "RRU经度")
    private String rruLon;

    @Excel(name = "RRU纬度")
    private String rruLat;

    @Excel(name = "所属市")
    private String baseCity;

    @Excel(name = "所属区县")
    private String baseCounty;

    @Excel(name = "所属场景")
    private String baseScene;

    @Excel(name = "站址类型")
    private String siteType;

    @Excel(name = "网络类型")
    private String netType;

    @Excel(name = "设备型号")
    private String deviceModel;

    @Excel(name = "小区配置")
    private String communityConfig;

    @Excel(name = "频率（MHz）")
    private String rate;

    @Excel(name = "带宽（M）")
    private String bandwidth;

    @Excel(name = "小区最大发射功率（dBm）")
    private String communityMaxpower;

    @Excel(name = "第1小区PCI")
    private String pciOne;

    @Excel(name = "第2小区PCI")
    private String pciTwo;

    @Excel(name = "第3小区PCI")
    private String pciThree;

    @Excel(name = "第4小区PCI")
    private String pciFour;

    @Excel(name = "第5小区PCI")
    private String pciFive;

    @Excel(name = "第6小区PCI")
    private String pciSix;

    @Excel(name = "第7小区PCI")
    private String pciSeven;

    @Excel(name = "第8小区PCI")
    private String pciEight;

    @Excel(name = "天线方位角")
    private String antennaAzimuth;

    @Excel(name = "天线型号")
    private String antennaModel;

    @Excel(name = "天线挂高")
    private String antennaHeight;

}
