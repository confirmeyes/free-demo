package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lpx .
 * @create 2020-04-15-15:48 .
 * @description .
 */
public class JDBCUtils {


    private static String drivername;
    private static String url;
    private static String user;
    private static String password;

    /**
     * 通过静态代码块，初始化数据库连接配置数据，并且注册数据库驱动
     */
    static {
        try {
            Properties pr = new Properties();
            //通过读取Properties文件给属性赋值，即每次使用该工具类都会读取最新配置进行连接
            //pr.load(new FileInputStream(new File("jdbc_config.properties")));
            //drivername = "com.mysql.jdbc.Driver";
            drivername = "com.mysql.cj.jdbc.Driver";
            url = "jdbc:mysql://192.168.1.100:3306/61db?useUnicode=true&characterEncoding=utf8&useSSL=false&rewriteBatchedStatements=true";
            user = "root";
            password = "Root@123";
            Class.forName(drivername);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
    }

    /**
     * 获取数据库连接对象
     *
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接异常，请检查配置数据");
        }
        return con;
    }

    /**
     * 关闭JDBC相关资源
     *
     * @param con
     * @param sta
     * @param rs
     */
    public static void closeResource(Connection con, Statement sta, ResultSet rs) {
        try {
            if (con != null) {
                con.close();
            }
            if (sta != null) {
                sta.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成栅格方法
     *
     * @param x1   原点X坐标
     * @param x2   斜角点X坐标
     * @param y1   原点Y坐标
     * @param y2   斜角点坐标
     * @param stup 步长 （栅格大小 单位：米）
     * @return
     */
    public static List<String> cordinatecMercatorGrid(double x1, double x2, double y1, double y2, int stup) {
        int total_x = (int) (Math.round(x2 - x1) / stup);
        int total_y = (int) (Math.round(y2 - y1) / stup);
        String origin_xy, up_xy, bevel_xy, down_xy;
        double x_origin, y_origin, x_up, y_up, x_bevel, y_bevel, x_down, y_down;
        List<String> resultList = new ArrayList<String>();
        for (int j = 0; j <= total_y; j++) {
            for (int i = 0; i <= total_x; i++) {
                //原点
                x_origin = x1 + stup * i;
                y_origin = y1 + stup * j;
                origin_xy = Mercator2lonLat(x_origin, y_origin);
                //原点向上
                x_up = x1 + stup * i;
                y_up = y1 + stup * (j + 1);
                up_xy = Mercator2lonLat(x_up, y_up);
                //斜角
                x_bevel = x1 + stup * (i + 1);
                y_bevel = y1 + stup * (j + 1);
                bevel_xy = Mercator2lonLat(x_bevel, y_bevel);
                //下斜角
                x_down = x1 + stup * (i + 1);
                y_down = y1 + stup * j;
                down_xy = Mercator2lonLat(x_down, y_down);
                resultList.add("MULTIPOLYGON(((" + origin_xy + "," + up_xy + "," + bevel_xy + "," + down_xy + "," + origin_xy + ")))");
            }
        }
        return resultList;
    }


    /**
     * 墨卡托转经纬度
     *
     * @param mercatorX 经度
     * @param mercatorY 纬度
     * @return
     */
    public static String Mercator2lonLat(double mercatorX, double mercatorY) {
        double[] xy = new double[2];
        double x = mercatorX / 20037508.34 * 180;
        double y = mercatorY / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        xy[0] = x;
        xy[1] = y;
        return String.valueOf(x + " " + y);
    }

}
