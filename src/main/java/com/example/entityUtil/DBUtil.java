package com.example.entityUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@ComponentScan("com.example.entityUtil")
@PropertySource("classpath:application.properties")
@Component
public class DBUtil {
	
	
	private static  String url;
	private static String username;
	private static String password;
	private static String driver;
	
//采用代码的方式获取属性
/*	static {
		driver = SystemConfig.getProperty("spring.datasource.driver-class-name");
		url = SystemConfig.getProperty("spring.datasource.url");
		username = SystemConfig.getProperty("spring.datasource.username");
		password = SystemConfig.getProperty("spring.datasource.password");
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't found jdbc driver, please check!\n" + e);
		}
	}*/

	public  Connection getConnection() throws SQLException {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't found jdbc driver, please check!\n" + e);
		}
		return DriverManager.getConnection(url, username, password);
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error occur when close Connection object");
			}
		}
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("Error occur when close PreparedStatement object");
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.out.println("Error occur when close Statement object");
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("Error occur when close ResultSet object");
			}
		}
	}

	public static void rollback(Connection conn) {
		if (conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				System.out.println("Error occur when invoke rollback() " + e);
			}
		}
	}

	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		close(rs);
		close(pstmt);
		close(conn);
	}

	public static void close(Connection conn, PreparedStatement pstmt) {
		close(pstmt);
		close(conn);
	}

	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
		close(conn);
	}

	public static void close(Connection conn, Statement stmt) {
		close(stmt);
		close(conn);
	}

	/**
	 * 在web容器初始化才会调用到 单纯调用main方法没效果
	 */
	@PostConstruct
	public void run(){
		
    }

	/**
	 * @param driver the driver to set
	 */
	@Value("${spring.datasource.driver-class-name}")
	public  void setDriver(String driver) {
		DBUtil.driver = driver;
	}
	

	@Value("${spring.datasource.url}")
	public void setUrl(String url) {
		DBUtil.url = url;
	}

	@Value("${spring.datasource.username}")
	public  void setUsername(String username) {
		DBUtil.username = username;
	}


	@Value("${spring.datasource.password}")
	public void setPassword(String password) {
		DBUtil.password = password;
	}
}
