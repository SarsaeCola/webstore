package Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/** 
* @Author 黄子良
* @Time 2020年5月14日 下午11:04:39 
* Description:
*/
public class c3p0Test {
    public static void main(String argv[]) throws PropertyVetoException, SQLException {

        test2();


    }


    public static void test1() throws PropertyVetoException, SQLException{

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver            
        cpds.setJdbcUrl( "jdbc:mysql://localhost/webstore?useSSL=false" );
        cpds.setUser("sarsae");                                  
        cpds.setPassword("xysxxldm647252");    

        //设置池 初始化大小
        cpds.setInitialPoolSize(5);
        Connection con =cpds.getConnection();


        System.out.println(con);

    }

    //c3p0可以使用配置文件加载配置 但是推荐使用xml加载配置
    public static void test2() throws SQLException {
        //在src下建立c3p0-config.xml 文件 文件名必须这样写
        //有了xml配置文件 new 的使用写 在  <named-config name="myconfig">  填写的name即可
        ComboPooledDataSource dataSources= new ComboPooledDataSource();
        Connection con =dataSources.getConnection();
        System.out.println(con);
    }

}
