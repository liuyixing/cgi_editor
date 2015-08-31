package com.up24.cgieditor.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;
import com.up24.cgieditor.dao.CgiDao;
import com.up24.cgieditor.view.CgiForm;

public class RefreshButtonEventHandler implements ActionListener {

	private static final Logger logger = Logger.getLogger();
	private CgiDao cgiDao;
	private JTable cgiTable;
	
	public RefreshButtonEventHandler() {
		cgiDao = (CgiDao)App.get("cgiDao");
		cgiTable = (JTable)App.get("cgiTable");
		logger.info("resetButtonEventHandler created");
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
		// 先隐藏表格
		//cgiTable.setVisible(false);
		// 取消掉单元格的编辑状态
    	TableCellEditor activeCellEditor = cgiTable.getCellEditor();
    	if (null != activeCellEditor) {
    		activeCellEditor.stopCellEditing(); // 只是取消编辑，会保存编辑的内容
    	}
		// 先删除表格的全部行
		DefaultTableModel tableModel = (DefaultTableModel)cgiTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
		// 重新渲染表格行信息
		CgiForm cgiForm = CgiForm.getInstance();
		cgiForm.render(cgiDao.getCgis());
		// 显示表格
		//cgiTable.setVisible(true);
		// 将scrollRectToVisible消息推送给父组件JScrollPane
		cgiTable.scrollRectToVisible(cgiTable.getCellRect(0, 0, true));
	}

}
