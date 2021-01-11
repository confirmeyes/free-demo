import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lpx .
 * @create 2020-04-22-17:59 .
 * @description .
 */
public class BatchTest {

    private static Logger logger = LoggerFactory.getLogger(InsertTest.class);


    private static ExecutorService creatThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("redisTest-pool-%d").build();
        return new ThreadPoolExecutor(6, 6,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }


    public static void cordinatecMercatorGrid(double x1, double x2, double y1, double y2, int stup) throws Exception {
        int total_x = (int) (Math.round(x2 - x1) / stup);
        int total_y = (int) (Math.round(y2 - y1) / stup);
        String origin_xy, up_xy, bevel_xy, down_xy;
        double x_origin, y_origin, x_up, y_up, x_bevel, y_bevel, x_down, y_down;
        int steps = 10;
        for (int m = 0; m < steps; m++) {
            List<String> resultList = new ArrayList<String>();
            for (int j = Math.round(total_y / steps * m); j <= Math.round(total_y / steps * (m + 1)); j++) {
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
            System.err.println("{----- arrayList.size():::: " + resultList.size() + "----------------}");


        }

    }


    @Test
    private void testRasterBatchAdd() throws Exception {
        cordinatecMercatorGrid(11980484.1242664, 12224755.8225355, 3987978.80093863, 4129146.90663547, 50);
    }

    public static void main(String[] args) throws Exception {
        cordinatecMercatorGrid(11980484.1242664, 12224755.8225355, 3987978.80093863, 4129146.90663547, 50);
    }


    public static void insertBatch(List<String> arrayList) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        System.out.println("------------" + Thread.currentThread().getName() + "-------------");

        //1.获取连接
        conn = JDBCUtils.getConnection();
        //2.编写sql语句
        String sql = "insert into gci_test (shape) values({call ST_GeomFromText(?)})";
        //3.获取执行sql语句对象
        pstmt = (PreparedStatement) conn.prepareStatement(sql);

        for (String string : arrayList) {
            pstmt.setString(1, string);
            pstmt.addBatch();
            //pstmt.executeUpdate();
        }

        long begin = System.currentTimeMillis();
        pstmt.executeBatch();
        conn.commit();
        long end = System.currentTimeMillis();



    }


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
