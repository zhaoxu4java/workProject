package com.lenovo.bda.ca.casearchkits.utilities;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.lenovo.bda.ca.casearchkits.entities.Cinema;
import com.lenovo.bda.ca.casearchkits.entities.Film;
import com.lenovo.bda.ca.casearchkits.entities.Schedule;
import com.lenovo.cic.utilities.datetime.DateTimeUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtilities {
	protected DBUtilities() {
	}

	private static final String DatabaseIP = "DatabaseIP";
	private static final String DatabasePort = "DatabasePort";
	private static final String UserName = "UserName";
	private static final String Password = "Password";
	private static final String DatabaseName = "DatabaseName";
	private static String TodayString = DateTimeUtils.GetTodayDateString();

	static ComboPooledDataSource cpds = null;
	static Configuration config = null;
	static {
		try {
			File configFile = new File("config" + File.separator + "config.xml");

			// System.out.println(configFile.getAbsolutePath());

			if (configFile.exists()) {
				config = new XMLConfiguration("config" + File.separator + "config.xml");
			} else {
				config = new XMLConfiguration(DBUtilities.class.getResource("config" + File.separator + "config.xml"));
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		if (config != null) {
			cpds = new ComboPooledDataSource();
			String databaseIp = config.getString(DBUtilities.DatabaseIP);
			String databasePort = config.getString(DBUtilities.DatabasePort);
			String userName = config.getString(DBUtilities.UserName);
			String password = config.getString(DBUtilities.Password);
			String databaseName = config.getString(DBUtilities.DatabaseName);

			// example: jdbc:mysql://localhost:3306/test
			String jdbcUrl = "jdbc:mysql://" + databaseIp + ":" + databasePort + "/" + databaseName + "?useUnicode=true&characterEncoding=utf8";

			try {
				cpds.setDriverClass("com.mysql.jdbc.Driver");

				// loads the jdbc driver
				cpds.setJdbcUrl(jdbcUrl);
				cpds.setUser(userName);
				cpds.setPassword(password);

				// the settings below are optional -- c3p0 can work with
				// defaults
				cpds.setMinPoolSize(5);
				cpds.setAcquireIncrement(5);
				cpds.setMaxPoolSize(100);

			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection GetConnection() throws SQLException {
		return cpds.getConnection();
	}

	/**
	 * 关闭工具方法
	 * 
	 * @param con
	 * @param preparedStatement
	 * @param rs
	 */
	private static void closeConn(Connection con, PreparedStatement preparedStatement, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
			if (con != null && !con.isClosed()) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer GetTableRowCount(String tableName) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = cpds.getConnection();

			String sql = "SELECT count(*) FROM "+tableName+";";

			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
	
			while (rs.next()) {
				return rs.getInt(1);
			}

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public static HashMap<Integer,Cinema> GetCinemaMap(int start,int limit) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = cpds.getConnection();

			String sql = "SELECT * FROM cinema_info limit "+start+","+limit+";";

			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			HashMap<Integer,Cinema> map = new HashMap<Integer,Cinema>();
			while (rs.next()) {
				Cinema cinema = new Cinema();
				
				cinema.setAmap_id(rs.getString("amap_id"));
				cinema.setAmap_name(rs.getString("amap_name"));
				cinema.setAddr(rs.getString("addr"));
				cinema.setArea(rs.getString("area"));
				cinema.setCinema_name(rs.getString("cinema_name"));
				cinema.setCity(rs.getString("city"));
				cinema.setDescription(rs.getString("description"));
				cinema.setID(rs.getInt("ID"));
				cinema.setProvince(rs.getString("province"));
				cinema.setScore(rs.getFloat("score"));
				cinema.setService(rs.getString("service"));
				cinema.setStore_service(rs.getString("store_service"));
				cinema.setTel(rs.getString("tel"));
				cinema.setUpdate_time(rs.getString("update_time"));
				cinema.setUrl(rs.getString("url"));
				cinema.setIndex_text(null);
				map.put(rs.getInt("ID"), cinema);
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static HashMap<Integer,Film> GetFilmMap(int start,int limit) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = cpds.getConnection();

			String sql = "SELECT * FROM film_info limit "+start+","+limit+";";

			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			HashMap<Integer,Film> map = new HashMap<Integer,Film>();
			while (rs.next()) {
				Film film = new Film();
				
				film.setActors(rs.getString("actors"));
				film.setDirector(rs.getString("director"));
				film.setDuration(rs.getString("duration"));
				film.setFilm_name(rs.getString("film_name"));
				film.setID(rs.getInt("ID"));
				film.setRegion(rs.getString("region"));
				film.setScore(rs.getFloat("score"));
				film.setScreendate(rs.getString("screendate"));
				film.setType(rs.getString("type"));
				film.setUpdate_time(rs.getString("update_time"));
				film.setUrl(rs.getString("url"));
				film.setImage_name(rs.getString("image_name"));
				film.setIndex_text(null);
				map.put(rs.getInt("ID"), film);
			
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static LinkedHashMap<Integer,Schedule> GetFilmScheduleMap(int start,int limit) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = cpds.getConnection();

			String sql = "SELECT * FROM film_schedule_info order by ID desc limit "+start+","+limit+";";

			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			LinkedHashMap<Integer,Schedule> map = new LinkedHashMap<Integer,Schedule>();
			String start_time = null;
			String end_time = null;
			while (rs.next()) {
				Schedule schedule = new Schedule();
				
				schedule.setCinema_id(rs.getInt("cinema_id"));
				schedule.setCinema_name(rs.getString("cinema_name"));
				schedule.setCrawldate(rs.getString("crawldate"));
				end_time = rs.getString("end_time");
				if(end_time!=null){
					end_time = end_time.replaceAll("[^:0-9]", "").trim();
					schedule.setEnd_time(end_time);
				}
				
				schedule.setFilm_id(rs.getInt("film_id"));
				schedule.setFilm_name(rs.getString("film_name"));
				schedule.setID(rs.getInt("ID"));
				schedule.setLang_version(rs.getString("lang_version"));
				schedule.setPrice(rs.getFloat("price"));
				schedule.setScreen_date(rs.getString("screen_date"));
				schedule.setScreen_hall(rs.getString("screen_hall"));
				start_time=rs.getString("start_time");
				if(start_time!=null){
					start_time = start_time.replaceAll("[^:0-9]", "").trim();
					schedule.setStart_time(start_time);
					schedule.setStart_time_sort(Integer.valueOf(start_time.replaceAll(":", "")));
				}
		
				schedule.setUpdate_time(rs.getString("update_time"));
				schedule.setUrl(rs.getString("url"));
			
				map.put(rs.getInt("ID"), schedule);
			
			}

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (ps != null) {
					ps.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
