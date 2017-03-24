package com.example.entityUtil;

/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SystemConfig
/*     */ {
/*  20 */   private static Properties props = new Properties();
/*     */   
/*     */   private static String configPath;
/*     */   
/*     */   private static String classPath;
/*     */   private static String appRoot;
/*     */   private static String appAbsolutePath;
/*     */   private static boolean isWindows;
/*     */   private static String resPath;
/*     */   private static boolean isDevMode;
/*     */   
/*     */   static
/*     */   {
/*  33 */     initialize();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isLinux()
/*     */   {
/*  41 */     return !isWindows;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isWindows()
/*     */   {
/*  49 */     return isWindows;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isDevMode()
/*     */   {
/*  57 */     return isDevMode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void initialize()
/*     */   {
/*  67 */     String osType = System.getProperty("os.name");
/*  68 */     isWindows = (osType == null) || (osType.toUpperCase().indexOf("WINDOWS") > -1);
/*  69 */     if (isWindows) {
/*  70 */       classPath = SystemConfig.class.getResource("/").getPath();
/*  71 */       classPath = classPath.replace("file:", "");
/*  72 */       int pos = classPath.indexOf("/classes/");
/*  73 */       if (pos > -1)
/*  74 */         classPath = classPath.substring(1, pos + 9);
/*     */     } else {
/*  76 */       classPath = SystemConfig.class.getResource("").getPath();
/*  77 */       classPath = classPath.replace("file:", "");
/*  78 */       int pos = classPath.indexOf("/classes/");
/*  79 */       if (pos == -1) {
/*  80 */         pos = classPath.indexOf("/lib/");
/*  81 */         if (pos > -1)
/*  82 */           classPath = classPath.substring(0, pos + 5);
/*     */       } else {
/*  84 */         classPath = classPath.substring(0, pos + 9);
/*     */       }
/*     */     }
/*  87 */     classPath = classPath.replace("/lib/", "/classes/");
/*     */     
/*  89 */     configPath = classPath;
/*  90 */     appAbsolutePath = classPath.replace("/WEB-INF/classes/", "");
/*     */     try
/*     */     {
/*  93 */       classPath = URLDecoder.decode(classPath, "UTF-8");
/*     */     }
/*     */     catch (Exception e) {}
/*     */     
/*  97 */     loadProperties(configPath + "application.properties");
/*  98 */     loadProperties(configPath + "config.properties");
/*     */     
/* 100 */     isDevMode = "true".equals(getProperty("dev.mode", "false"));
/* 101 */     appRoot = props.getProperty("app.root", "/");
/* 102 */     resPath = props.getProperty("static.resource.root", "/");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void reloadProperties()
/*     */   {
/* 109 */     props.clear();
/* 110 */     loadProperties(configPath + "jdbc.properties");
/* 111 */     loadProperties(configPath + "config.properties");
/*     */   }
/*     */   
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Properties getProperties()
/*     */   {
/* 128 */     return props;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getResPath()
/*     */   {
/* 136 */     return resPath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getProperty(String key, String defaultProperty)
/*     */   {
/* 146 */     return props.getProperty(key, defaultProperty);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getProperty(String key)
/*     */   {
/* 159 */     return props.getProperty(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getConfigPath()
/*     */   {
/* 168 */     return configPath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getClassPath()
/*     */   {
/* 177 */     return classPath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getAppRoot()
/*     */   {
/* 185 */     return appRoot;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getAppAbsolutePath()
/*     */   {
/* 195 */     return appAbsolutePath;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void loadProperties(String propertyFile)
/*     */   {
/* 203 */     InputStream ins = null;
/*     */     try {
/* 205 */       ins = new FileInputStream(propertyFile);
/* 206 */       props.load(ins);
/* 207 */       ins.close(); return;
/*     */     } catch (Exception e) {
/* 209 */       System.out.println("Not found config file: " + configPath + ".");
/*     */     } finally {
/*     */       try {
/* 212 */         if (ins != null) {
/* 213 */           ins.close();
/*     */         }
/*     */       }
/*     */       catch (IOException e) {}
/*     */     }
/*     */   }
/*     */   
/*     */ }

