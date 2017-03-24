package com.example.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class BaseService {
	@Autowired
	private BaseDAO baseDAO;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected TransactionTemplate transactionTemplate;
	
	
	/*-------------------------------------------------------- 增加相关开始--------------------------------------------------------------------------------*/
	
	/**
	 * 注意实体要符合规范（工具生成）
	 * @param entity
	 * @return
	 */
	public <T> T insert(T entity) {
		return (T) baseDAO.insert(entity);
	}
	
	/*-------------------------------------------------------- 增加相关结束--------------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------- 删除相关开始--------------------------------------------------------------------------------*/
	
	
	
	/*-------------------------------------------------------- 删除相关结束--------------------------------------------------------------------------------*/


	public <T> boolean delete(Class<T> entityType, Serializable primaryKey) {
		return baseDAO.delete(entityType, primaryKey);
	}

	/*-------------------------------------------------------- 更新相关开始--------------------------------------------------------------------------------*/
	
	/**
	 * 更新
	 * @param sql
	 * @return
	 */
	public int update(String sql) {
		return baseDAO.update(sql);
	}
	
	/**
	 * 更新带参数
	 * @param sql
	 * @return
	 */
	public int update(String sql, Object... args) {
		return baseDAO.update(sql, args);
	}
	
	/**
	 * 整个实体更新 注意实体要符合规范（工具生成）
	 * @param entity
	 * @return
	 */
	public <T> int update(T entity) {
		return baseDAO.update(entity);
	}
	
	/*-------------------------------------------------------- 更新相关结束--------------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------- 查询相关开始--------------------------------------------------------------------------------*/

	
	public long queryForLong(String sql, Object... args) {
		return baseDAO.queryForLong(sql, args);
	}

	public long queryForLong(String sql) {
		return baseDAO.queryForLong(sql);
	}

	public int queryForInt(String sql, Object... args) {
		return baseDAO.queryForInt(sql, args);
	}

	public int queryForInt(String sql) {
		return baseDAO.queryForInt(sql);
	}

	public double queryForDouble(String sql, Object... args) {
		return baseDAO.queryForDouble(sql, args);
	}

	public double queryForDouble(String sql) {
		return baseDAO.queryForDouble(sql);
	}

	public String queryForString(String sql, Object... args) {
		return baseDAO.queryForString(sql, args);
	}

	public String queryForString(String sql) {
		return baseDAO.queryForString(sql);
	}
	
	/**
	 * 查询条数
	 * @param sql
	 * @return
	 */
	public int getRecordCount(String sql) {
		return baseDAO.getRecordCount(sql);
	}
	
	/**
	 * 查询条数带参数
	 * @param sql
	 * @param args
	 * @return
	 */
	public int getRecordCount(String sql, Object... args) {
		return baseDAO.getRecordCount(sql, args);
	}
	
	
	public <T> T get(Class<T> entityType, Serializable primaryKey) {
		return (T) baseDAO.get(entityType, primaryKey);
	}
	
	/**
	 * 获取实体
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) {
		return (T) baseDAO.queryForObject(sql, rowMapper);
	}
	
	/**
	 * 获取实体带不定参数
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
		return (T) baseDAO.queryForObject(sql, rowMapper, args);
	}
	
	/**
	 * 获取实体带数组参数
	 * @param sql
	 * @param args
	 * @param rowMapper
	 * @return
	 */
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
		return (T) baseDAO.queryForObject(sql, args, rowMapper);
	}
	
	/**
	 * 获取实体通过Class
	 * @param sql
	 * @param returnType
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> returnType) {
		return (T) baseDAO.queryForObject(sql, returnType);
	}
	
	/**
	 * 获取实体通过Class带定参
	 * @param sql
	 * @param returnType
	 * @return
	 */
	public <T> T queryForObject(String sql, Class<T> returnType, Object... args) {
		return (T) baseDAO.queryForObject(sql, returnType, args);
	}
	
	public Map<String, Object> queryForMap(String sql) {
		return baseDAO.queryForMap(sql);
	}
	
	public Map<String, Object> queryForMap(String sql, Object... args) {
		return baseDAO.queryForMap(sql, args);
	}

	
	/*-------------------------------------------------------- 查询相关结束--------------------------------------------------------------------------------*/



	/*-------------------------------------------------------- List和Page查询相关开始--------------------------------------------------------------------------------*/
	
	
	public List<Map<String, Object>> queryForList(String sql) {
		return baseDAO.queryForList(sql);
	}
	
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		return baseDAO.queryForList(sql, args);
	}

	public <T> List<T> queryForList(String sql, Class<T> elementType) {
		return baseDAO.queryForList(sql, elementType);
	}
	
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) {
		return baseDAO.queryForList(sql, elementType, args);
	}


	public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
		return baseDAO.query(sql, rowMapper);
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
		return baseDAO.query(sql, rowMapper, args);
	}

	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
		return baseDAO.query(sql, args, rowMapper);
	}

	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, int start, int pageSize) {
		return baseDAO.queryByPage(sql, rowMapper, start, pageSize);
	}

	public <T> List<T> queryByPage(String sql, RowMapper<T> rowMapper, int start, int pageSize, Object... args) {
		return baseDAO.queryByPage(sql, rowMapper, start, pageSize, args);
	}
	
	
	/*-------------------------------------------------------- List和Page查询相关结束--------------------------------------------------------------------------------*/
	
	/*-------------------------------------------------------- 特殊相关开始--------------------------------------------------------------------------------*/
	
	/**
	 * 获取最新插入的ID
	 * @param returnType
	 * @return
	 */
	public <T> T getLastInsertId(Class<T> returnType) {
		return (T) baseDAO.getLastInsertId(returnType);
	}

	/**
	 * 万能执行sql
	 * @param sql
	 */
	public void execute(String sql) {
		baseDAO.execute(sql);
	}
	/*-------------------------------------------------------- 特殊相关结束--------------------------------------------------------------------------------*/


	
}
