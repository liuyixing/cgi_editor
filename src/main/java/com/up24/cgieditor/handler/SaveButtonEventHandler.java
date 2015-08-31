package com.up24.cgieditor.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;
import com.up24.cgieditor.dao.CgiDao;
import com.up24.cgieditor.model.Cgi;
import com.up24.cgieditor.model.Cgi.Arg;

public class SaveButtonEventHandler implements ActionListener {

	private static final Logger logger = Logger.getLogger();
	private CgiDao cgiDao;
	private JTable cgiTable;
	
	public SaveButtonEventHandler() {
		cgiDao = (CgiDao)App.get("cgiDao");
		cgiTable = (JTable)App.get("cgiTable");
		logger.info("saveButtonEventHandler created");
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			unsafeActionPerformed(e);
		} catch (Exception ex) {
			logger.error("actionPerformed failed", ex);
			JOptionPane.showMessageDialog(null, Constants.SYS_ERR);
		} finally {
			logger.flush();
		}
	}
	
	/**
	 * actionPerformed方法运行在事件线程中，一旦发生未捕获的异常则当前事件线程会挂掉
	 * 这里把逻辑代码写在unsafeActionPerformed方法中，然后在actionPerformed方法中使用try..catch并友好地报告异常情况
	 */
	private void unsafeActionPerformed(ActionEvent e) {
		// 取消掉单元格的编辑状态
    	TableCellEditor activeCellEditor = cgiTable.getCellEditor();
    	if (null != activeCellEditor) {
    		activeCellEditor.stopCellEditing(); // 只是取消编辑，会保存编辑的内容
    	}
		List<Cgi> cgiList = new ArrayList<Cgi>();
		Cgi cgi = null;
		Arg arg = null;
		int rowCount = cgiTable.getRowCount();
		//@TODO： 表单验证
		// 组装Cgi信息
		for (int i = 0; i < rowCount; i++) {
			String cgiName = (String)cgiTable.getValueAt(i, 0);
			String cgiType = (String)cgiTable.getValueAt(i, 1);
			String cgiComment = (String)cgiTable.getValueAt(i, 2);
			String cgiAuthor = (String)cgiTable.getValueAt(i, 3);
			String cgiNeedLogin = (String)cgiTable.getValueAt(i, 4);
			String cgiArgName = (String)cgiTable.getValueAt(i, 5);
			String cgiArgFrom = (String)cgiTable.getValueAt(i, 6);
			String cgiArgIsRequire = (String)cgiTable.getValueAt(i, 7);
			String cgiArgType = (String)cgiTable.getValueAt(i, 8);
			String cgiArgAllowHtml = (String)cgiTable.getValueAt(i, 9);
			String cgiArgTypeDesc = (String)cgiTable.getValueAt(i, 10);
			String cgiArgComment = (String)cgiTable.getValueAt(i, 11);
			if (null != cgiName && !cgiName.trim().isEmpty()) {
				cgi = new Cgi();
				cgi.setName(cgiName);
				cgi.setType(cgiType);
				cgi.setComment(cgiComment);
				cgi.setAuthor(cgiAuthor);
				cgi.setNeedLogin(cgiNeedLogin);
				cgi.setArgList(new ArrayList<Arg>());
				cgiList.add(cgi);
			}
			if (null != cgiArgName && !cgiArgName.trim().isEmpty()) {
				arg = new Arg();
				arg.setName(cgiArgName);
				arg.setFrom(cgiArgFrom);
				arg.setIsRequire(cgiArgIsRequire);
				arg.setType(cgiArgType);
				arg.setAllowHtml(cgiArgAllowHtml);
				arg.setTypeDesc(cgiArgTypeDesc);
				arg.setComment(cgiArgComment);
				cgi.getArgList().add(arg);
			}
		}
		// 保存Cgi信息
		boolean isSucc = false;
		if (cgiList.size() != 0) {
			isSucc = cgiDao.setCgis(cgiList);
		}
		if (isSucc) {
			JOptionPane.showMessageDialog(null, Constants.OP_SUCC);
		} else {
			JOptionPane.showMessageDialog(null, Constants.OP_FAIL);
		}
	}
}
