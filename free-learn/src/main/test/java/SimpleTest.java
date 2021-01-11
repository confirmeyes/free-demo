import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author WIN10 .
 * @create 2020-07-31-16:52 .
 * @description .
 */
public class SimpleTest {

    @Test
    void simple() {
        int value = HttpStatus.OK.value();
        System.out.println(value);
        Assertions.assertEquals(value, 200, "断言200");
    }

    @Test
    public static void main(String[] args) {
        //POINT(108.751865125076 34.2716292617811)
        //POINT(108.755529650173 34.0388408599178)

        /*DecimalFormat df = new DecimalFormat("0.00");
        //108.953709,34.31123
        //108.95211,34.310052
        double rad = Math.PI / 180,
                lat1 = 34.31123 * rad,
                lat2 = 34.310052 * rad,
                lon1 = 108.953709 * rad,
                lon2 = 108.95211 * rad;
        double a = Math.sin(lon2 - lon1) * Math.cos(lat2);
        double b = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        double degrees = radiansToDegrees(Math.atan2(a, b));
        BigDecimal bigDecimal = new BigDecimal(degrees);
        double result = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        System.out.println(result);*/


        String azimuth = "30";
        List<Map<String, Double>> mapList = compuerAuuRange(azimuth);
        if (mapList == null) {
            System.out.println("Auu无增益");
        }
        for (Map doubleMap : mapList) {
            if (doubleMap.get("across") != null) {
                System.out.println("跨0度");
                double start = Double.valueOf(doubleMap.get("start").toString());
                double end = Double.valueOf(doubleMap.get("end").toString());
                double aStart = Double.valueOf(doubleMap.get("aStart").toString());
                double aEnd = Double.valueOf(doubleMap.get("aEnd").toString());
                System.out.println(doubleMap.get("start"));
                System.out.println(doubleMap.get("end"));
                System.out.println(doubleMap.get("aStart"));
                System.out.println(doubleMap.get("aEnd"));
                int bool = ((start <= 50.0) && (50.0 <= end) ? 1 : 0) +
                        ((aStart <= 50.0) && (50.0 <= aEnd) ? 2 : 0);
                System.out.println(bool);
                if (bool != 0) {
                    System.out.println("范围内");
                }
            }
            if (doubleMap.get("across") == null) {
                System.out.println(doubleMap.get("start"));
                System.out.println(doubleMap.get("end"));
            }
        }




       /* Map<String, Double> doubleMap = middleRange(10);
        double start = doubleMap.get("start");
        double end = doubleMap.get("end");
        if (doubleMap.get("across") != null) {
            System.out.println("跨0度");
            System.out.println(doubleMap.get("start"));
            System.out.println(doubleMap.get("end"));
            System.out.println(doubleMap.get("aStart"));
            System.out.println(doubleMap.get("aEnd"));
        }
        if (doubleMap.get("across") == null) {
            System.out.println(doubleMap.get("start"));
            System.out.println(doubleMap.get("end"));
        }*/


       /* System.out.println(computerDegrees(10, 15, 20));
        System.out.println(Math.toDegrees(Math.tan(1)));*/

       /* String sitePoint = "POINT(108.92473632737 34.2256612358024)";
        List<String> site = Arrays.asList(sitePoint.substring(6, sitePoint.length() - 1).split(" "));

        System.out.println(site.get(0));
        System.out.println(site.get(1));*/
        //POINT(108.94018308789174 34.2433242261072)


    }


    /**
     * 计算方向角
     *
     * @param sitePoint  基站点
     * @param floorPoint 楼宇点
     * @return
     */
    private double getDirectionDegrees(String sitePoint, String floorPoint) {

        List<String> site = Arrays.asList(sitePoint.substring(6, sitePoint.length() - 1).split(" "));

        System.out.println(site.get(0));
        //POINT(108.94018308789174 34.2433242261072)
        double rad = Math.PI / 180,
                lat1 = Double.valueOf(sitePoint.substring(25, 37)) * rad,
                lat2 = Double.valueOf(floorPoint.substring(25, 37)) * rad,
                lon1 = Double.valueOf(sitePoint.substring(6, 18)) * rad,
                lon2 = Double.valueOf(floorPoint.substring(6, 18)) * rad;
        double a = Math.sin(lon2 - lon1) * Math.cos(lat2);
        double b = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1);
        double degrees = radiansToDegrees(Math.atan2(a, b));
        BigDecimal bigDecimal = new BigDecimal(degrees);
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 弧度转换为角度
     */
    private static double radiansToDegrees(double radians) {
        double degrees = radians % (2 * Math.PI);
        double result = degrees * 180 / Math.PI;
        if (result < 0) {
            return 360 - Math.abs(result);
        }
        return result;
    }


    /**
     * 方位角计算AAU增益范围
     *
     * @param azimuth
     * @return
     */
    private static List<Map<String, Double>> compuerAuuRange(String azimuth) {
        List<Map<String, Double>> mapList = new ArrayList<>();
        if (azimuth.isEmpty()) {
            return null;
        }
        boolean isContains = azimuth.contains("/");
        if (!isContains) {
            double middle = Double.valueOf(azimuth);
            mapList.add(middleRange(middle));
            return mapList;
        }

        String[] splitStr = azimuth.split("/");
        for (String str : splitStr) {
            double middle = Double.valueOf(str);
            mapList.add(middleRange(middle));
        }
        return mapList;
    }

    private static Map<String, Double> middleRange(double middle) {
        Map<String, Double> doubleMap = new HashMap<>();
        double start = middle - 60;
        double end = middle + 60;
        if (end > 360 || start < 0) {
            if (end > 360) {
                end = end - 360;
            }
            if (start < 0) {
                start = 360 - Math.abs(start);
            }
            doubleMap.put("start", start);
            doubleMap.put("end", 360d);
            doubleMap.put("aStart", 0d);
            doubleMap.put("aEnd", end);
            doubleMap.put("across", 1d);
            return doubleMap;
        }
        doubleMap.put("start", start);
        doubleMap.put("end", end);
        return doubleMap;
    }


    private static double computerDegrees(double siteHeight, double floorHeight, double distance) {

        if (siteHeight == floorHeight) {
            return 90;
        }
        if (siteHeight > floorHeight) {
            double degrees = Math.toDegrees(Math.atan(distance / (siteHeight - floorHeight)));
            BigDecimal bigDecimal = new BigDecimal(degrees);
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        }
        if (siteHeight < floorHeight) {
            BigDecimal bigDecimal = new BigDecimal(Math.toDegrees(Math.atan((floorHeight - siteHeight) / distance)) + 90);
            return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        }
        return 0;
    }

}
