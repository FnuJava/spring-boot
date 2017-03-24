package com.example.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
public class BaseDAO {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	protected TransactionTemplate transactionTemplate;

	@Autowired
	protected DataSource dataSource;


	private static String toInitUpperCase(String input) {
		if (input.length() < 2) {
			return input.toUpperCase();
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	private static String toClassName(String input) {
		String[] arr = input.toLowerCase().split("_");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].equals("")) {
				buffer.append(toInitUpperCase(arr[i]));
			}
		}
		return buffer.toString();
	}

	public <T> T insert(T entity) {
		String[] columns = null;
		String table = null;
		try {
			try {
				columns = (String[]) entity.getClass().getField("columns").get(entity);
			} catch (Exception ex) {
				logger.error("该实体类：" + entity.getClass() + "没有定义columns字段，请检查！");
				return null;
			}
			try {
				table = (String) entity.getClass().getField("TABLE").get(entity);
			} catch (Exception ex) {
				logger.error("该实体类：" + entity.getClass() + "没有定义TABLE字段，请检查！");
				return null;
			}
			List<String> colList = new ArrayList<String>();
			final List<Object> valList = new ArrayList<Object>();
			for (String column : columns) {
				if ((!column.equalsIgnoreCase("id"))) {
					String className = toClassName(column);
					Method method = null;
					try {
						method = entity.getClass().getMethod("get" + className, new Class[0]);
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
						try {
							method = entity.getClass().getMethod("is" + className, new Class[0]);
						} catch (NoSuchMethodException ex) {
							ex.printStackTrace();
							return null;
						}
					}
					if (method != null) {
						Object value = method.invoke(entity, new Object[0]);
						if (value != null) {
							colList.add(column);
							valList.add(value);
						}
					}
				}
			}
			if (colList.size() > 0) {
				StringBuilder fields = new StringBuilder();
				StringBuilder values = new StringBuilder();
				for (int i = 0; i < colList.size(); i++) {
					fields.append(colList.get(i)).append(",");
					values.append("?,");
				}

				fields.deleteCharAt(fields.length() - 1);
				values.deleteCharAt(values.length() - 1);
				String tableName = table;
				final String insertSQL = "insert into " + tableName + "(" + fields + ") values(" + values
						+ ")";
				logger.info(insertSQL + " value " + valList);

				try {
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(insertSQL, 1);
						for (int i = 1; i <= valList.size(); i++) {
							ps.setObject(i, valList.get(i - 1));
						}
							return ps;
						}
					}, keyHolder);

					Object primaryKey = entity.getClass().getField("PRIMARY_KEY").get(entity);
					if ("id".equalsIgnoreCase(primaryKey.toString())) {
						Class<?> pkClass = entity.getClass().getDeclaredField("id").getType();
						Object idValue = null;
						if (Integer.class == pkClass) {
							idValue = Integer.valueOf(keyHolder.getKey().intValue());
						}
						else if (Long.class == pkClass) {
							idValue = Long.valueOf(keyHolder.getKey().longValue());
						}
						else if (Double.class == pkClass) {
							idValue = Double.valueOf(keyHolder.getKey().doubleValue());
						}

						Method method = entity.getClass().getMethod("setId", new Class[] { pkClass });
						method.invoke(entity, new Object[] { idValue });
					}
				} catch (Exception ex) {
					logger.error("该实体类：" + entity.getClass() + "插入到数据时发生异常！" + entity, ex);
					ex.printStackTrace();
				}
				return entity;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入数据库时出现错误！" + entity + " " + e);
		}
		return null;
	}

	public <T> int update(T entity) {
		String[] columns = null;
		String table = null;
		String primaryKey = null;
		try {
			try {
				columns = (String[]) entity.getClass().getField("columns").get(entity);
			} catch (Exception ex) {
				logger.error("该实体类：" + entity.getClass() + "没有定义columns字段，请检查！");
				return 0;
			}
			try {
				table = (String) entity.getClass().getField("TABLE").get(entity);
			} catch (Exception ex) {
				logger.error("该实体类：" + entity.getClass() + "没有定义TABLE字段，请检查！");
				return 0;
			}
			try {
				primaryKey = (String) entity.getClass().getField("PRIMARY_KEY").get(entity);
			} catch (Exception ex) {
				logger.error("该实体类：" + entity.getClass() + "没有定义PRIMARY_KEY字段，请检查！");
				return 0;
			}
			primaryKey = "," + primaryKey + ",";
			List<String> colList = new ArrayList<String>();
			List<Object> valList = new ArrayList<Object>();
			List<String> pkList = new ArrayList<String>(1);
			List<Object> pkValues = new ArrayList<Object>(1);
			for (String column : columns) {
				String className = toClassName(column);
				Method method = null;
				try {
					method = entity.getClass().getMethod("get" + className, new Class[0]);
				} catch (NoSuchMethodException e) {
					try {
						method = entity.getClass().getMethod("is" + className, new Class[0]);
					} catch (NoSuchMethodException ex) {
						return 0;
					}
				}
				if (method != null) {
					Object value = method.invoke(entity, new Object[0]);
					if (value != null) {
						if (primaryKey.indexOf("," + column + ",") > -1) {
							pkList.add(column);
							pkValues.add(value);
						} else {
							colList.add(column);
							valList.add(value);
						}
					}
				}
			}
			valList.addAll(pkValues);
			if ((colList.size() > 0) && (pkList.size() > 0)) {
				StringBuilder setClause = new StringBuilder();
				StringBuilder whereClause = new StringBuilder();
				for (int i = 0; i < colList.size(); i++)
					setClause.append(colList.get(i)).append("=?,");
					for (int i = 0; i < pkList.size(); i++)
						whereClause.append(pkList.get(i)).append("=? and ");
						setClause.deleteCharAt(setClause.length() - 1);
						whereClause.delete(whereClause.length() - 5, whereClause.length());
						String tableName = table;
						String updateSQL = "update " + tableName + " set " + setClause + " where " + whereClause;
						logger.info("updateSQL====================" + updateSQL + " " + valList);
				return jdbcTemplate.update(updateSQL, valList.toArray());
			}

			logger.error("请设置要更新的字段与主键字段！" + entity);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update数据库时出现错误！" + entity + " " + e);
		}
		return 0;
	}

	public int getRecordCount(String sql) {
		return jdbcTemplate.queryForObject(sql, Integer.class).intValue();
	}

	public int getRecordCount(String sql, Object... args) {
		return jdbcTemplate.queryForObject(sql, Integer.class, args).intValue();
	}

	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}

	public int update(String sql) {
		return jdbcTemplate.update(sql);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
		List<T> list = jdbcTemplate.query(sql, rowMapper, args);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
		List<T> list = jdbcTemplate.query(sql, rowMapper);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
		List<T> list = jdbcTemplate.query(sql, args, rowMapper);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public Map<String, Object> queryForMap(String sql, Object... args) {
		try {
			return jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e) {
			return jdbcTemplate.queryForMap(sql + " limit 1", args);
		}
	}


	public Map<String, Object> queryForMap(String sql) {
		try {
			return jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (IncorrectResultSizeDataAccessException e) {
			return jdbcTemplate.queryForMap(sql + " limit 1");
		}
	}

	public long queryForLong(String sql, Object... args) {
		List<Long> list = jdbcTemplate.queryForList(sql, Long.class, args);
		return (list.size() == 0) || (list.get(0) == null) ? 0L : list.get(0).longValue();
	}

	public long queryForLong(String sql) {
		List<Long> list = jdbcTemplate.queryForList(sql, Long.class);
		return (list.size() == 0) || (list.get(0) == null) ? 0L : list.get(0).longValue();
	}

	public int queryForInt(String sql, Object... args) {
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class, args);
		return (list.size() == 0) || (list.get(0) == null) ? 0 : list.get(0).intValue();
	}

	public int queryForInt(String sql) {
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
		return list.get(0) == null ? 0 : list.size() == 0 ? 0 : list.get(0).intValue();
	}

	public double queryForDouble(String sql, Object... args) {
		List<Double> list = jdbcTemplate.queryForList(sql, Double.class, args);
		return (list.size() == 0) || (list.get(0) == null) ? 0.0D : list.get(0).doubleValue();
	}

	public double queryForDouble(String sql) {
		List<Double> list = jdbcTemplate.queryForList(sql, Double.class);
		return (list.size() == 0) || (list.get(0) == null) ? 0.0D : list.get(0).doubleValue();
	}

	public String queryForString(String sql, Object... args) {
		List<String> list = jdbcTemplate.queryForList(sql, String.class, args);
		return list.size() > 0 ? list.get(0) : null;
	}

	public String queryForString(String sql) {
		List<String> list = jdbcTemplate.queryForList(sql, String.class);
		return list.size() > 0 ? list.get(0) : null;
	}

	public <T> T queryForObject(String sql, Class<T> returnType) {
		List<T> list = jdbcTemplate.queryForList(sql, returnType);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public <T> T queryForObject(String sql, Class<T> returnType, Object... args) {
		List<T> list = jdbcTemplate.queryForList(sql, returnType, args);
		return (T) (list.size() > 0 ? list.get(0) : null);
	}

	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);

	}

	List<Map<String, Object>> queryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) {
		return jdbcTemplate.queryForList(sql, elementType, args);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		return jdbcTemplate.queryForList(sql, elementType);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
		return jdbcTemplate.query(sql, rowMapper, args);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(sql, rowMapper);
	}

	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(sql, args, rowMapper);
	}

	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, int start, int pageSize) {
		sql = sql + " LIMIT " + start + ", " + pageSize;
		logger.info(sql);
		return jdbcTemplate.query(sql, rowMapper);
	}

	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, int start, int pageSize, Object... args) {
		sql = sql + " LIMIT " + start + ", " + pageSize;
		logger.info(sql);
		return jdbcTemplate.query(sql, rowMapper, args);
	}

	public void execute(String sql) {
		jdbcTemplate.execute(sql);
	}

	public <T> T getLastInsertId(Class<T> returnType) {
		return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", returnType);
	}
	
	public <T> boolean delete(Class<T> entityType, Serializable primaryKey)	{
		EntityInfo<T> ei = getEntityInfo(entityType);
		if (ei != null) {
			String sql;
			sql = "delete from " + ei.table + " where " + primaryKey + " = ?";
			return update(sql, new Object[] { primaryKey }) > 0;
	   }
	   return false;
	}
	
	public <T> T get(Class<T> entityType, Serializable primaryKey){
		EntityInfo<T> ei = getEntityInfo(entityType);
		if (ei != null) {
				String sql;
				sql = "select * from " + ei.table + " where " + primaryKey + " = ?";
				return (T)queryForObject(sql, ei.mapper, new Object[] { primaryKey });
			}
		return null;
	}
	
	private static final Map<Class, EntityInfo> entityMap = new HashMap<Class, EntityInfo>();

	private <T> EntityInfo<T> getEntityInfo(Class<T> cls){
		EntityInfo<T> ei = entityMap.get(cls);
		if (ei == null)
		{
			try
			{
				ei = new EntityInfo<T>();
				ei.table = (cls.getField("TABLE").get(null) + "");
				ei.primaryKey = (cls.getField("PRIMARY_KEY").get(null) + "");
				Class<?> cls2 = Class.forName(cls.getCanonicalName() + "$Mapper");
				RowMapper<T> rowMapper = (RowMapper<T>)cls2.newInstance();
				ei.mapper = rowMapper;
				entityMap.put(cls, ei);
				return ei;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return ei;
	}
}