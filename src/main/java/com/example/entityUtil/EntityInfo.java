package com.example.entityUtil;

import org.springframework.jdbc.core.RowMapper;

class EntityInfo<T>
{
  public String table;
  public String primaryKey;
  public RowMapper<T> mapper;
  
  EntityInfo() {}
}