package com.example.entityUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Hibernate配置文件(.hbm.xml)和Entity类的生成工具, <BR>
 * 这个工具必须配合config/SystemConfig.properties文件才能工作正常，<BR>
 * 在这个配置文件里指定要转换的表：hibernate.database.tables和<BR>
 * 文件的输出路径：hibernate.entity.output.dir
 * @author Lin Yufa
 * @version 1.0, 2010-04-12
 */

@Resource
@ComponentScan("com.example.entityUtil")
public class EntityHelper {
	Connection conn = null;
	PreparedStatement autoIncrPstmt = null;
	DatabaseMetaData dbMeta = null;
	
	String outputDir = SystemConfig.getClassPath().replace("/target/classes/", "/src/main/java/");
	
	String entityPackageName = "com.example.entity";
	String table = ",users,";
	//String table = "ALL"; //生成实体的表
	String entityPackagePath = entityPackageName.replaceAll("\\.", "/") + "/";
	String dtoDir = outputDir + entityPackagePath;
	@Resource
	DBUtil dBUtil;
	
	Random uidGenerator = new Random(System.currentTimeMillis());
	
/*	{
		System.out.println("========================="+dtoDir);
		try {
			//DBUtil dUtil = new DBUtil();
			conn = dUtil.getConnection();
			System.out.println("heheh"+dUtil.getDrivers());
			String queryAutoIncr = "select count(1) from information_schema.columns " +
								   "where table_name=? and EXTRA = 'auto_increment'";
			autoIncrPstmt = conn.prepareStatement(queryAutoIncr);
			dbMeta = conn.getMetaData();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	public void init(){
		try {
			conn = dBUtil.getConnection();
			String queryAutoIncr = "select count(1) from information_schema.columns " +
								   "where table_name=? and EXTRA = 'auto_increment'";
			autoIncrPstmt = conn.prepareStatement(queryAutoIncr);
			dbMeta = conn.getMetaData();
			generateCode();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取config/SystemConfig.properties配置文件里的
	 * <li>数据库连接参数</li> 
	 * <li>要转换的表(也可以是所有表)</li>
	 * <li>实体类文件和.hbm.xml文件的输出路径</li><BR>
	 * 然后根据数据表定义成生配置文件与Java实体类到输出路径上
	 * @throws IOException
	 */
	public void generateCode() throws IOException {
		try {
			File dir = new File(dtoDir);
			if (!dir.exists() && !dir.mkdirs()) return;
            
			String[] types = {"TABLE"};
            ResultSet rs = dbMeta.getTables(null, null, null, types);
            while(rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if(!table.equals("ALL") && table.indexOf(","+tableName+",")==-1)
                	continue;
                if(tableName.startsWith("BIN") || tableName.indexOf("$") > -1)
                	continue;
                
                generateEntity(tableName);	//生成实体类
                System.out.println(tableName+";");
            }
            System.out.println("finished successful!");
		} catch(Exception e) {
			System.out.println("Exception occur when generating hbm.xml and entity class");
			e.printStackTrace();
		}
	}
	
	private void generateEntity(String tableName) throws Exception {
		String className = toClassName(tableName);
		String instanceName = toVariableName(tableName);
		StringBuilder b = new StringBuilder();
		String serialVersionUID = null;
		File javaSrcCode = new File(dtoDir + className + ".java");
        //if the source file exists, found are there codes that user maual added
        //if yes, set the codes to a variable, and then append source code which generated automatically
        if (javaSrcCode.exists()) {
        	BufferedReader reader = new BufferedReader(new FileReader(javaSrcCode));
        	String line;
        	boolean isFound = false;
        	while((line = reader.readLine()) != null) {
        		if (serialVersionUID == null && line.trim().startsWith("private static final long serialVersionUID"))
        			serialVersionUID = line.substring(line.indexOf(" = ")+3, line.lastIndexOf(';'));
        		if (isFound || line.trim().startsWith("///")) {
        			isFound = true;
        			b.append(line).append("\n");
        		}
        	}
        	reader.close();
        }
        
        
		//generate java entity class
		StringBuilder buffer = new StringBuilder();
        buffer.append("package ").append(entityPackageName).append(";\n\n");
        buffer.append("import java.io.Serializable;\n");
        buffer.append("import java.sql.*;\n\n");
        buffer.append("import org.springframework.jdbc.core.RowMapper;\n\n");
        buffer.append("public class ").append(className)
        	  .append(" implements Serializable {\n\n");
        buffer.append("\tprivate static final long serialVersionUID = ")
        	  .append(serialVersionUID == null ? (uidGenerator.nextLong()+"L") : serialVersionUID).append(";\n\n");
        
        ResultSet pkrs = dbMeta.getPrimaryKeys(null, null, tableName);
        String sql = "select * from " + tableName + " limit 0, 1";
        ResultSetMetaData rsmd = conn.createStatement().executeQuery(sql).getMetaData();
        int numberOfColumns = rsmd.getColumnCount();
        ArrayList<String> columnNames = new ArrayList<String>();
        HashMap<String, Integer> columnTypes = new HashMap<String, Integer>(numberOfColumns);
        HashMap<String, String> columnJavaTypes = new HashMap<String, String>(numberOfColumns);
        Set<String> nullableFields = new HashSet<String>();
        Set<String> primKeys = new  LinkedHashSet<String>();
        String primaryKeys = "";
        while(pkrs.next())  {
        	primKeys.add(pkrs.getString("COLUMN_NAME"));
        	primaryKeys += pkrs.getString("COLUMN_NAME") + ",";
        }
        if (primKeys.size() == 0) {
        	System.out.println("【"+tableName+"】没有主键");
        	return;
        }
        primaryKeys = primaryKeys.substring(0, primaryKeys.length()-1);
        StringBuilder columns = new StringBuilder();
        
        buffer.append("\tpublic static final String TABLE = \"").append(tableName).append("\";\n\n");
        buffer.append("\tpublic static final String PRIMARY_KEY = \"").append(primaryKeys).append("\";\n\n");
        
        for (int i = 1; i <= numberOfColumns; i++) {
        	String columnName = rsmd.getColumnName(i);
        	columns.append('"').append(columnName).append("\", ").append(i==4 || (i>4 && (i-4)%6== 0) ? "\n\t\t\t" : "");
        	if (rsmd.isNullable(i) != ResultSetMetaData.columnNoNulls)
        		nullableFields.add(columnName);
        	int type = rsmd.getColumnType(i);
        	int precision = (type==2005 || type==2004) ? 0 : rsmd.getPrecision(i);
        	columnNames.add(columnName);
        	columnTypes.put(columnName, type);
        	columnJavaTypes.put(columnName, convertToJavaType(type, precision));
        }
        columns.delete(columns.length()-2, columns.length());
        buffer.append("\tpublic static final String[] columns = {").append(columns).append("};\n\n");
        
        
      //INSERT INTO SQL
        buffer.append("\tpublic static final String INSERT_SQL = \"insert into "+tableName+" \"\n\t\t\t+ \"(");
        autoIncrPstmt.setString(1, tableName);
        ResultSet autoIncRS = autoIncrPstmt.executeQuery();
		autoIncRS.next();
		boolean isAutoInc = false;
		if (autoIncRS.getInt(1) >= 1) {
			isAutoInc = true;
		}
		autoIncRS.close();
		int j = 0;
        for (int i = 0; i < numberOfColumns; i++) {
        	String colName = columnNames.get(i);
        	if (primKeys.contains(colName) && isAutoInc) 
        		continue;
        	buffer.append("`"+colName+"`").append(", ");
        	if (++j % 5 == 0 && i < numberOfColumns-1)
        		buffer.append("\"\n\t\t\t+ \"");
        }
        if (buffer.charAt(buffer.length()-1) == '(') {
        	return;
        } else {
        	buffer.deleteCharAt(buffer.length()-1);
        	buffer.deleteCharAt(buffer.length()-1);
        }
        buffer.append(") values(");
        for (int i = 0; i < j; i++) 
        	buffer.append("?, ");
        buffer.deleteCharAt(buffer.length()-1);
    	buffer.deleteCharAt(buffer.length()-1);
        buffer.append(")\";\n\n");
        
        //all fields
        buffer.append("\t//fields\n");
        for (int i = 0; i < numberOfColumns; i++) {
        	String colName = columnNames.get(i); 
        	buffer.append("\tprivate ")
        		   .append(columnJavaTypes.get(colName))
        		   .append(" ").append(toVariableName(colName))
        		   .append(";\n");
        }
        
        //default constructor
        buffer.append("\n\t//default constructor\n");
        buffer.append("\tpublic ").append(className).append("() {\n");
        buffer.append("\t}\n");
        
        //constructor with arguments
        if (primKeys.size() > 1) {
        	buffer.append("\n\t//constructor with arguments");
        	buffer.append("\n\tpublic ").append(className).append("(");
        	for (String colName : primKeys) {
        		String colType = columnJavaTypes.get(colName);
        		String fieldName = toVariableName(colName);
        		buffer.append(colType).append(" ").append(fieldName).append(", ");
        	}
        	buffer.delete(buffer.length()-2, buffer.length());
        	buffer.append(") {\n");
        	for (String colName : primKeys) {
        		String fieldName = toVariableName(colName);
        		buffer.append("\t\tthis.").append(fieldName).append(" = ").append(fieldName).append(";\n");
        	}
        	buffer.append("\t}\n");
        }
        
        //all getter method
        buffer.append("\n\t//getter\n");
        for (int i = 0; i < numberOfColumns; i++) {
        	String colName = columnNames.get(i); 
        	buffer.append("\tpublic ")
        		  .append(columnJavaTypes.get(colName))
        		  .append(columnJavaTypes.get(colName).equals("Boolean") ? 
        				  " is" : " get")
        		  .append(toClassName(colName))
        		  .append("() {\n");
        	buffer.append("\t\treturn ")
        		  .append(toVariableName(colName))
        		  .append(";\n\t}\n");
        }
        
        //all setter method
        buffer.append("\n\t//setter\n");
        for (int i = 0; i < numberOfColumns; i++) {
        	String colName = columnNames.get(i); 
        	String fieldName = toVariableName(colName);
        	buffer.append("\tpublic void set")
        		   .append(toClassName(colName))
        		   .append("(")
        		   .append(columnJavaTypes.get(colName))
        		   .append(" ").append(fieldName).append(") {\n")
        		   .append("\t\tthis.").append(fieldName)
        		   .append(" = " ).append(fieldName).append(";\n\t}\n");
        }
        
        //equals method
        buffer.append("\n\t//equals method\n");
        buffer.append("\tpublic boolean equals(Object other) {\n");
        buffer.append("\t\tif (this == other)\n\t\t\treturn true;\n");
        buffer.append("\t\tif (!(other instanceof ").append(className)
        	  .append("))\n\t\t\treturn false;\n\n");
        buffer.append("\t\tfinal ").append(className).append(" ")
        	  .append(instanceName).append(" = ").append("(")
        	  .append(className).append(")").append("other;\n");
        
        buffer.append("\t\tif (");
        for (String colName : primKeys) {
        	buffer.append("!this.").append(toVariableName(colName))
  			  		  .append(".equals(").append(instanceName)
  			  		  .append(".get").append(toClassName(colName))
  			  		  .append("()) || \n\t\t\t");
        }
        buffer.delete(buffer.length() - 8, buffer.length()).append(")\n");
        buffer.append("\t\t\treturn false;\n");
        buffer.append("\n\t\treturn true;\n");
    	buffer.append("\t}\n\n");
        
        //hashCode method
        buffer.append("\t//hashCode method\n");
        buffer.append("\tpublic int hashCode() {\n");
        buffer.append("\t\tStringBuffer keys = new StringBuffer();\n");
        for (String colName : primKeys) {
        	buffer.append("\t\tkeys.append(")
        		  .append(toVariableName(colName))
        		  .append(").append(").append("\",\");\n");
        }
        buffer.append("\t\tif (keys.length() > 0)\n");
        buffer.append("\t\t\tkeys.deleteCharAt(keys.length() - 1);\n");
        buffer.append("\t\treturn keys.toString().hashCode();\n");
        buffer.append("\t}\n\n");
        
        //toString method
        buffer.append("\t//toString method\n");
        buffer.append("\tpublic String toString() {\n");
        buffer.append("\t\treturn new StringBuilder(\"").append(className).append("[\")\n");
        for (int i = 0; i < numberOfColumns - 1; i++) {
        	String colName = columnNames.get(i); 
        	colName = toVariableName(colName);
        	buffer.append("\t\t\t.append(\"").append(colName).append("=\").append(")
        		.append(colName).append(").append(\", \")\n");
        }
        String col = columnNames.get(numberOfColumns - 1); 
        col = toVariableName(col);
        buffer.append("\t\t\t.append(\"").append(col).append("=\").append(")
        	.append(col).append(").append(\"]\").toString();\n");
        buffer.append("\t}\n\n");
        
      //RowMapper class
        buffer.append("\t//RowMapper\n");
        buffer.append("\tpublic static class Mapper implements RowMapper<"+className+"> {\n");
        buffer.append("\t\tString s;\n");
        buffer.append("\t\tpublic "+className+" mapRow(ResultSet rs, int i) throws SQLException {\n");
        buffer.append("\t\t\t"+className+" "+instanceName+" = new "+className+"();\n");
        for (int i = 0; i < numberOfColumns; i++) {
        	String colName = columnNames.get(i); 
        	String type = toInitUpperCase(columnJavaTypes.get(colName));
        	String clsname = toClassName(colName);
        	
        	if ((type.equals("Integer") || type.equals("Double") || type.equals("Long")) && nullableFields.contains(colName)) {
        		buffer.append("\t\t\t"+instanceName+".set"+clsname+"((s=rs.getString(\""+colName+"\")) == null ? null : new "+type+"(s));\n");
        	} else {
        		if (type.equals("Integer")) 
        			type = "Int";
        		buffer.append("\t\t\t"+instanceName+".set"+clsname+"(rs.get"+type+"(\""+colName+"\"));\n");
        	}
        }
        buffer.append("\t\t\treturn "+instanceName+";\n");
        buffer.append("\t\t}\n");
        buffer.append("\t}\n\n");
      
        if (b.length() > 0) {
        	buffer.append("\n").append(b.toString());
        } else {
        	buffer.append("}");
        }
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(dtoDir + className + ".java"));
        writer2.write(buffer.toString());
        writer2.flush();
        writer2.close();
	}

	private static String convertToJavaType(int type, int precision) {
		switch(type) {
		case Types.LONGVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.CLOB:
			return "String";
			
		case Types.NUMERIC:
			return (precision > 9) ? "Long" : "Integer";
		
		case Types.INTEGER:
		case Types.TINYINT:
		case Types.SMALLINT:
			return "Integer";
		
		case Types.BIT:
			return "Boolean";
		
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.REAL:
		case Types.DECIMAL:
			return "Double";
		
		case Types.BIGINT:
			return "Long";
			
		case Types.TIMESTAMP:
			return "Timestamp";
			
		case Types.DATE:
			return "Date";
		
		case Types.TIME:
			return "Time";
			
		case Types.BLOB:
		case Types.LONGVARBINARY:
			return "Blob";
			
		default: 
			return "Object";
		}
	}
	
	private static String toClassName(String input) {
		String[] arr = input.toLowerCase().split("_");
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if(arr[i].equals(""))
				continue;
			buffer.append(toInitUpperCase(arr[i]));
		}
		return buffer.toString();
	}
	
	private static String toVariableName(String input) {
		String[] arr = input.toLowerCase().split("_");
		StringBuffer buffer = new StringBuffer(arr[0]);
		for(int i = 1; i < arr.length; i++) {
			if(arr[i].equals(""))
				continue;
			buffer.append(toInitUpperCase(arr[i]));
		}
		return buffer.toString();
	}
	
	private static String toInitUpperCase(String input) {
		if(input.length() < 2)
			return input.toUpperCase();
		else return input.substring(0,1).toUpperCase() + input.substring(1);
	}
	
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EntityHelper.class);
		EntityHelper bean = context.getBean(EntityHelper.class);
		bean.init();
		context.close();
	}
}
