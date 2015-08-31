package com.up24.cgieditor.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;

public class AddButtonEventHandler implements ActionListener {
	
	private static final Logger logger = Logger.getLogger();
	private JTable cgiTable;
	
	public AddButtonEventHandler() {
		cgiTable = (JTable)App.get("cgiTable");
		logger.info("addButtonEventHandler created");
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
		// 添加新行
		DefaultTableModel tableModel = (DefaultTableModel)cgiTable.getModel();
		Object[] rowData = new Object[Constants.CGI_TABLE_COLUMN_COUNT];
		tableModel.addRow(rowData);
		// 滚动到新行，其实就是滚动到底部
		int lastRow = tableModel.getRowCount() - 1;
		// 选中最后一行
		cgiTable.setRowSelectionInterval(lastRow, lastRow);
		// 将scrollRectToVisible消息推送给父组件JScrollPane
		cgiTable.scrollRectToVisible(cgiTable.getCellRect(lastRow, 0, true));
		// 编辑第一个单元格
		cgiTable.editCellAt(lastRow, 0);
	}
}
