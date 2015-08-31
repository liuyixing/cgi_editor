package com.up24.cgieditor.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;

public class QueryButtonEventHandler implements ActionListener {
	
	private static final Logger logger = Logger.getLogger();
	private JTable cgiTable;
	private JTextField queryText;
	
	public QueryButtonEventHandler() {
		cgiTable = (JTable)App.get("cgiTable");
		queryText = (JTextField)App.get("queryText");
		logger.info("queryButtonEventHandler created");
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
		DefaultTableModel tableModel = (DefaultTableModel)cgiTable.getModel();
		int rowCount = cgiTable.getRowCount();
		// 获取查询关键字
		String keyword = queryText.getText();
		if (null == keyword || keyword.trim().isEmpty() || Constants.CGI_QUERY_TIPS.equals(keyword)) {
			JOptionPane.showMessageDialog(null, Constants.CGI_QUERY_TIPS);
			return;
		}
		// 调整开始查询的行号
		int beginRow = 0;
		String lastKeyword = (String)App.get("lastKeyword");
		Integer lastFoundRow = (Integer)App.get("lastFoundRow");
		if (keyword.equals(lastKeyword) && null != lastFoundRow) {
			beginRow = lastFoundRow + 1;
		} else {
			App.set("lastKeyword", null);
			App.set("lastFoundRow", null);
		}
		String cgiName = null;
		for (int i = beginRow; i < rowCount; i++) {
			cgiName = (String)cgiTable.getValueAt(i, 0);
			if (null != cgiName && cgiName.contains(keyword)) {
				// 选中该行
				cgiTable.setRowSelectionInterval(i, i);
				// 将scrollRectToVisible消息推送给父组件JScrollPane
				cgiTable.scrollRectToVisible(cgiTable.getCellRect(i, 0, true));
				App.set("lastKeyword", keyword);
				App.set("lastFoundRow", i);
				return;
			}
		}
		// 清空缓存的内容
		App.set("lastKeyword", null);
		App.set("lastFoundRow", null);
		JOptionPane.showMessageDialog(null, Constants.CGI_QUERY_NOT_FOUND);
	}
}
