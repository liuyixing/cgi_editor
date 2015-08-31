package com.up24.cgieditor.handler;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableCellEditor;

import com.up24.cgieditor.App;

/**
 * 实现当表格单元格右键菜单显示的时候，选中当前行
 * 除此之外，还可以通过监听表格的鼠标事件来实现
 */
public class PopupMenuEventHandler implements PopupMenuListener {
	
	private JTable cgiTable;
	
	public PopupMenuEventHandler() {
		cgiTable = (JTable)App.get("cgiTable");
	}

	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		// 此处另起一个线程，是因为要等到popupMenu显示出来再执行，以获取当前行号
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// 获取表格的鼠标右键菜单
				JPopupMenu popupMenu = cgiTable.getComponentPopupMenu();
				// 计算右键菜单对应的表格行号
				int rowAtPoint = cgiTable.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), cgiTable));
				int rowCount = cgiTable.getRowCount();
				// 选中该行
		        if (rowAtPoint >= 0 && rowAtPoint < rowCount) {
		        	// 取消掉单元格的编辑状态
		        	TableCellEditor activeCellEditor = cgiTable.getCellEditor();
		        	if (null != activeCellEditor) {
		        		activeCellEditor.stopCellEditing(); // 只是取消编辑，会保存编辑的内容
		        		//activeCellEditor.cancelCellEditing(); // 取消编辑，并删除编辑的内容
		        	}
		        	cgiTable.setRowSelectionInterval(rowAtPoint, rowAtPoint);
		        }
			}
		});
	}

	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	public void popupMenuCanceled(PopupMenuEvent e) {
	}

}
