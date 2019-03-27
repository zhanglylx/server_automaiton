package server_automaiton_gather.server_automaiton_Utils;

import com.mfeia.book.server_automaiton.Test;
import org.apache.commons.dbcp2.BasicDataSource;
import server_automaiton_gather.ErrException;
import server_automaiton_gather.RealizePerform;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库管理
 */
public class DBUtils {
    //数据库连接池
    private static BasicDataSource ds;
    //为不同线程管理连接
    private static ThreadLocal<Connection> tl;

    static {
        try {
            Properties properties = PropertiesConfig.getPropertiesConfig(PropertiesConfig.DATA_BASE_CONFIG);

            //初始化连接池
            ds = new BasicDataSource();
            //设置驱动
            ds.setDriverClassName(properties.getProperty("driver"));
            //设置url
            ds.setUrl(properties.getProperty("url"));
            //设置用户名
            ds.setUsername(properties.getProperty("user"));
            //设置密码
            ds.setPassword(properties.getProperty("pwd"));
            //初始连接数量
            ds.setInitialSize(Integer.parseInt(properties.getProperty("initsize")));
            //连接池允许的最大连接数
            ds.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
            //最大空闲连接数
            ds.setMaxIdle(Integer.parseInt(properties.getProperty("maxidle")));
            //最小空闲连接数
            ds.setMinIdle(Integer.parseInt(properties.getProperty("minidle")));
            //获取连接的等待超时时间
            ds.setMaxWaitMillis(Integer.parseInt(properties.getProperty("maxwait")));
            //初始化本地线程
            tl = new ThreadLocal<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() throws SQLException {
        /**
         * 通过连接池获取一个空闲连接
         */
        Connection connection =
                ds.getConnection();
        tl.set(connection);
        return connection;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        try {
            Connection connection = tl.get();
            if (connection != null) {
                /**
                 * 通过连接池获取的Connection
                 * 的close()方法实际上并没有将
                 * 连接关闭，而是将该链接归还。
                 */
                connection.close();
                tl.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getDrCallBackStatus(String s, String ss, String sss) throws SQLException {
        String sql = "SELECT status From ssss WHERE 1=? AND 2=? AND 3=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        preparedStatement.setString(1, s);
        preparedStatement.setString(2, ss);
        preparedStatement.setString(3, sss);
        int status = 0;
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            status = resultSet.getInt("sss");
        }
        return status;

    }

}
