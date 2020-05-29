import entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author lpx .
 * @create 2020-04-15-15:55 .
 * @description .
 */

public class InsertTest {

    private static Logger logger = LoggerFactory.getLogger(InsertTest.class);


    @Test
    public void testRasterBatchAdd() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        List<String> arrayList = new ArrayList<String>();

        arrayList = JDBCUtils.cordinatecMercatorGrid
                (11980484.1242664, 12224755.8225355, 3987978.80093863, 4129146.90663547, 50);
        //logger.info("生成列表数量 {" + arrayList.size() + "}");

        System.err.println("{----- arrayList.size():::: " + arrayList.size() + "----------------}");

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


    @Test
    public void testBatchAdd() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        List<User> userList = getUserList(10000000);



        //logger.info("生成列表数量 {" + userList.size() + "}");

        //1.获取连接
        conn = JDBCUtils.getConnection();
        //2.编写sql语句
        String sql = "insert into gci_test (name,age,remark) values(?,?,?)";
        //3.获取执行sql语句对象
        pstmt = (PreparedStatement) conn.prepareStatement(sql);

        for (User user : userList) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getId());
            pstmt.setString(3, user.getRemark());
            pstmt.addBatch();
            //pstmt.executeUpdate();
        }

        long begin = System.currentTimeMillis();
        pstmt.executeBatch();
        conn.commit();
        //pstmt.executeUpdate();
        long end = System.currentTimeMillis();


    }

    private List<User> getUserList(int count) {

        List<User> userList = new ArrayList<User>(10000000);
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(1);
            user.setName("123");
            user.setRemark("remark");
            userList.add(user);
        }
        return userList;
    }


    @Test
    public void testAdd() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.编写sql语句
            String sql = "insert into product values(null,?,?,?,?)";
            //3.获取执行sql语句对象
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            //4.设置参数
            pstmt.setString(1, "hehe");
            pstmt.setDouble(2, 2);
            pstmt.setDate(3, null);
            pstmt.setString(4, "2");
            //5.执行插入操作
            int row = pstmt.executeUpdate();
            if (row > 0) {
                System.out.println("添加成功！");
            } else {
                System.out.println("添加失败！");

            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            JDBCUtils.closeResource(conn, pstmt, null);

        }

    }


    /*测试工具类
     * 根据id查询用户信息
     * */
    @Test
    public void testFindUserById() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        //获取链接
        try {
            conn = JDBCUtils.getConnection();
            //写sql语句
            String sql = "select * from product where pid =?";
            //获取执行sql语句对象

            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            //设置参数
            pstmt.setInt(1, 2);
            //执行查询操作
            rs = pstmt.executeQuery();
            //处理结果集
            while (rs.next()) {
                System.out.println(rs.getString(1) + "----" + rs.getString("price"));


            }
            //释放资源不能放在此处
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            //释放资源
            JDBCUtils.closeResource(conn, pstmt, rs);
        }
    }
}
