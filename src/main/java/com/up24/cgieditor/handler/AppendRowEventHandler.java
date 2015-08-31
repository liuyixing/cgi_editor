package com.up24.cgieditor.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;

public class AppendRowEventHandler implements ActionListener {

	private static final Logger logger = Logger.getLogger();
	private JTable cgiTable;
	
	public AppendRowEventHandler() {
		cgiTable = (JTable)App.get("cgiTable");
		logger.info("appendRowEventHandler created");
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
		// 获取当前选中的行号
		int selectedRow = cgiTable.getSelectedRow() + 1;
		int rowCount = cgiTable.getRowCount();
		Object[] rowData = new Object[Constants.CGI_TABLE_COLUMN_COUNT];
		// 追加新行
		if (selectedRow >= rowCount) {
			tableModel.addRow(rowData);
			// 将scrollRectToVisible消息推送给父组件JScrollPane
			cgiTable.scrollRectToVisible(cgiTable.getCellRect(selectedRow, 0, true));
		} else {
			tableModel.insertRow(selectedRow, rowData);
		}
		// 选中新行
		cgiTable.setRowSelectionInterval(selectedRow, selectedRow);
		cgiTable.editCellAt(selectedRow, 5);
	}
}
