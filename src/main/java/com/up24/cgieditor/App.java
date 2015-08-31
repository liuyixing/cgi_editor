package com.up24.cgieditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.up24.cgieditor.common.Logger;
import com.up24.cgieditor.dao.CgiDao;
import com.up24.cgieditor.view.CgiForm;
import com.up24.cgieditor.model.Cgi;

public class App {
	
	private static final Logger logger = Logger.getLogger();
	private static final Map<String, Object> objMap = new HashMap<String, Object>();
	
	private App() {
		// 注册一些对象
		objMap.put("cgiDao", new CgiDao());
		logger.info("object registered");
	}
	
	public static Object get(String objName) {
		return objMap.get(objName);
	}
	
	public static void set(String objName, Object obj) {
		objMap.put(objName, obj);
	}
	
	public void start() {
		// 构建Cgi窗体
		CgiForm cgiForm = CgiForm.getInstance();
		CgiDao cgiDao = (CgiDao)get("cgiDao");
		List<Cgi> cgiList = cgiDao.getCgis();
		cgiForm.render(cgiList);
		logger.info("app started");
	}

	public static void main(String[] args) {	
		try {
			App app = new App();
			app.start();
		} catch(Exception e) {
			logger.error("error occurred", e);
		} finally {
			logger.flush();
		}
	}
}
