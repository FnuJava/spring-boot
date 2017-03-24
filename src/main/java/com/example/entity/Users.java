package com.example.entity;

import java.io.Serializable;
import java.sql.*;

import org.springframework.jdbc.core.RowMapper;

public class Users implements Serializable {

	private static final long serialVersionUID = 1574247383765084554L;

	public static final String TABLE = "users";

	public static final String PRIMARY_KEY = "id";

	public static final String[] columns = {"id", "name", "date"};

	public static final String INSERT_SQL = "insert into users "
			+ "(`name`, `date`) values(?, ?)";

	//fields
	private Integer id;
	private String name;
	private Timestamp date;

	//default constructor
	public Users() {
	}

	//getter
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Timestamp getDate() {
		return date;
	}

	//setter
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	//equals method
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Users))
			return false;

		final Users users = (Users)other;
		if (!this.id.equals(users.getId()))
			return false;

		return true;
	}

	//hashCode method
	public int hashCode() {
		StringBuffer keys = new StringBuffer();
		keys.append(id).append(",");
		if (keys.length() > 0)
			keys.deleteCharAt(keys.length() - 1);
		return keys.toString().hashCode();
	}

	//toString method
	public String toString() {
		return new StringBuilder("Users[")
			.append("id=").append(id).append(", ")
			.append("name=").append(name).append(", ")
			.append("date=").append(date).append("]").toString();
	}

	//RowMapper
	public static class Mapper implements RowMapper<Users> {
		String s;
		public Users mapRow(ResultSet rs, int i) throws SQLException {
			Users users = new Users();
			users.setId(rs.getInt("id"));
			users.setName(rs.getString("name"));
			users.setDate(rs.getTimestamp("date"));
			return users;
		}
	}

}