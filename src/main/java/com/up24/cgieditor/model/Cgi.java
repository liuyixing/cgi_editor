package com.up24.cgieditor.model;

import java.util.List;

public class Cgi {
	
	public static class Arg {
		
		private String name;
		private String type;
		private String from;
		private String isRequire;
		private String typeDesc;
		private String comment;
		private String allowHtml;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getIsRequire() {
			return isRequire;
		}
		public void setIsRequire(String isRequire) {
			this.isRequire = isRequire;
		}
		public String getTypeDesc() {
			return typeDesc;
		}
		public void setTypeDesc(String typeDesc) {
			this.typeDesc = typeDesc;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		public String getAllowHtml() {
			return allowHtml;
		}
		public void setAllowHtml(String allowHtml) {
			this.allowHtml = allowHtml;
		}
	}
	
	private String name;
	private String type;
	private String comment;
	private String author;
	private String needLogin;
	private List<Arg> argList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getNeedLogin() {
		return needLogin;
	}
	public void setNeedLogin(String needLogin) {
		this.needLogin = needLogin;
	}
	public List<Arg> getArgList() {
		return argList;
	}
	public void setArgList(List<Arg> argList) {
		this.argList = argList;
	}
}
