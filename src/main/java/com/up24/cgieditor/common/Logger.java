package com.up24.cgieditor.common;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Logger {
	
	private static final Logger logger = new Logger();
	private static List<Log> logList = new ArrayList<Log>();
	private enum Level {DEBUG, INFO, WARN, ERROR};
	private static Level level = Level.DEBUG;
	private static String logFilePath = "";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	private Logger() {
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public void debug(String msg) {
		log(Level.DEBUG, msg);
	}
	
	public void info(String msg) {
		log(Level.INFO, msg);
	}
	
	public void warn(String msg) {
		log(Level.WARN, msg);
	}
	
	public void error(String msg) {
		log(Level.ERROR, msg);
	}
	
	public void error(String msg, Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log(Level.ERROR, String.format("%s\n%s", msg, sw.toString()));
	}
	
	private void log(Level level, String msg) {
		// 获取当前线程的调用堆栈信息
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		boolean found = false;
		StringBuilder caller = new StringBuilder();
		for (StackTraceElement frame : stack) {
			if (found) {
				// 找到方法的调用者，记录调用者信息
				caller.append(frame.getClassName()).append(".").append(frame.getMethodName()).append("/")
				.append(frame.getFileName()).append(":").append(frame.getLineNumber());
				break;
			}
			if (frame.getClassName().equals("com.up24.cgieditor.common.Logger")
				&& (frame.getMethodName().equals("debug") || frame.getMethodName().equals("info")
					|| frame.getMethodName().equals("warn") || frame.getMethodName().equals("error"))) {
				found = true;
			}
		}
		Log log = new Log(level, msg, caller.toString(), new Date());
		logList.add(log);
	}
	
	public void flush() {
		if (logList.isEmpty()) {
			return;
		}
		List<Log> tmpLogList = logList;
		logList = new ArrayList<Log>();
		StringBuilder sb = new StringBuilder();
		for (Log log : tmpLogList) {
			if (log.getLevel().ordinal() >= level.ordinal()) {
				sb.append(log);
				sb.append("\n");
			}
		}
		//System.out.print(sb);
		FileWriter writer = null;
		try {
			File logPath = new File(System.getenv("APPDATA") + Constants.CGI_LOG_PATH);
			if (!logPath.exists()) {
				logPath.mkdir();
			}
			File logFile = new File(System.getenv("APPDATA") + Constants.CGI_LOG_PATH + Constants.CGI_LOG_NAME);
			writer = new FileWriter(logFile, true);
			writer.write(sb.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class Log {
		
		private Level level;
		private String msg;
		private String caller;
		private Date time;
		
		Log(Level level, String msg, String caller, Date time) {
			this.level = level;
			this.msg = msg;
			this.caller = caller;
			this.time = time;
		}
		
		public Level getLevel() {
			return level;
		}

		public String getMsg() {
			return msg;
		}

		public Date getTime() {
			return time;
		}

		public String toString() {
			return String.format("[%s]\t%s\t%s\t%s", level.name(), dateFormat.format(time), caller, msg);
		}
	}
}
