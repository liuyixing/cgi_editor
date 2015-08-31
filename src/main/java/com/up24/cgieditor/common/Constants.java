package com.up24.cgieditor.common;

public class Constants {
	
	/** Cgi表格列名 */
	public static final Object[] CGI_TABLE_COLUMN_NAMES = {"Cgi", "入口", "注释说明", "维护人", "要求登陆", "参数名", "限定来源", "是否必须", "类型", "允许html", "类型附加说明", "注释说明"};
	
	/** Cgi表格列数 */
	public static final int CGI_TABLE_COLUMN_COUNT = 12;
	
	/** 操作成功 */
	public static final String OP_SUCC = "操作成功";
	
	/** 操作失败 */
	public static final String OP_FAIL = "操作失败";
	
	/** 系统错误 */
	public static final String SYS_ERR = "系统错误，请联系技术人员解决";
	
	/** Cgi窗体名称 */
	public static final String CGI_FORM_NAME = "Cgi编辑器";
	
	/** Cgi窗体宽度 */
	public static final int CGI_FORM_WIDTH = 1800;
	
	/** Cgi窗体高度 */
	public static final int CGI_FORM_HEIGHT = 900;
	
	/** Cgi表格宽度 */
	public static final int CGI_TABLE_WIDTH = 1795;
	
	/** Cgi表格高度 */
	public static final int CGI_TABLE_HEIGHT = 830;
	
	/** Cgi入口类型 */
	public static final String[] CGI_TYPES = {"index", "json", "sys", "erp", "index&sys"};
	
	/** Cgi维护人 */
    public static final String[] CGI_AUTHORS = {"郑富山", "胡华泉", "刘毅星", "陈伟锋", "周稳稳", "黄燕达", "曾雷"};
    
    /** Cgi通用选择项 */
    public static final String[] CGI_COMMON_OPTIONS = {"Y", "N"};
    
    /** Cgi参数来源 */
    public static final String[] CGI_ARG_FROMS = {"get", "post", "cookie", "all"};
    
    /** Cgi参数类型 */
    public static final String[] CGI_ARG_TYPES = {"int", "int_list", "string", "enum", "float", "array"};
    
    /** 保存按钮 */
    public static final String CGI_BUTTON_SAVE = "保存";
    
    /** 查找按钮 */
    public static final String CGI_BUTTON_QUERY = "查找";
    
    /** 查找输入框提示 */
    public static final String CGI_QUERY_TIPS = "请输入Cgi名称";
    
    /** 查询失败提示 */
    public static final String CGI_QUERY_NOT_FOUND = "找不到匹配的Cgi";
    
    /** 查找输入框宽度 */
    public static final int CGI_BUTTON_QUERY_WIDTH = 200;
    
    /** 查找输入框高度 */
    public static final int CGI_BUTTON_QUERY_HEIGHT = 27;
    
    /** 空字符串 */
    public static final String EMPTY_STRING = "";
    
    /** 刷新按钮 */
    public static final String CGI_BUTTON_REFRESH = "刷新";
    
    /** Cgi窗体图标路径 */
    public static final String CGI_FORM_ICON_PATH = "/resources/images/up24.png";
    
    /** 新增按钮 */
    public static final String CGI_BUTTON_ADD = "新增";
    
    /** 插入新行 */
    public static final String CGI_TABLE_INSERT_ROW = "插入新行";
    
    /** 追加新行 */
    public static final String CGI_TABLE_APPEND_ROW = "追加新行";
    
    /** 删除此行 */
    public static final String CGI_TABLE_DELETE_ROW = "删除此行";
    
    /** Cgi XML文件路径 */
    public static final String CGI_XML_PATH = "cgi.xml";
    
    /** Cgi日志文件路径 */
    public static final String CGI_LOG_PATH = "/CgiEditor/";
    
    /** Cgi日志文件名称 */
    public static final String CGI_LOG_NAME = "log.log";
}
