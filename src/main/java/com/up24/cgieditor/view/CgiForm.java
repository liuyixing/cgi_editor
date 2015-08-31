package com.up24.cgieditor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;

import com.up24.cgieditor.App;
import com.up24.cgieditor.common.Constants;
import com.up24.cgieditor.common.Logger;
import com.up24.cgieditor.handler.AddButtonEventHandler;
import com.up24.cgieditor.handler.AppendRowEventHandler;
import com.up24.cgieditor.handler.DeleteRowEventHandler;
import com.up24.cgieditor.handler.InsertRowEventHandler;
import com.up24.cgieditor.handler.PopupMenuEventHandler;
import com.up24.cgieditor.handler.QueryButtonEventHandler;
import com.up24.cgieditor.handler.QueryTextEventHandler;
import com.up24.cgieditor.handler.RefreshButtonEventHandler;
import com.up24.cgieditor.handler.SaveButtonEventHandler;
import com.up24.cgieditor.model.Cgi;
import com.up24.cgieditor.model.Cgi.Arg;

public class CgiForm {
	
	/** 注意：若logger放在cgiForm下面而且cgiForm的构造函数有调用logger，就会抛出空指针错误 */
	private static final Logger logger = Logger.getLogger(); 
	private static final CgiForm cgiForm = new CgiForm();
	/** 窗口 */
	private JFrame frame;
	/** 面板 */
	private JPanel panel;
	/** 表格 */
	private JTable table;
	
	private CgiForm() {
		// 窗口
		frame = new JFrame(Constants.CGI_FORM_NAME);
		frame.setSize(Constants.CGI_FORM_WIDTH, Constants.CGI_FORM_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// 面板
		panel = new JPanel();
		frame.setContentPane(panel);
		// 窗口左右居中
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = screenSize.width;
		int x = (width - Constants.CGI_FORM_WIDTH) / 2;
		frame.setLocation(x, 10);
		// 设置窗口图标 
		InputStream imageInputStream = this.getClass().getResourceAsStream(Constants.CGI_FORM_ICON_PATH);
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(imageInputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setIconImage(icon.getImage()); 
	    // 表格
        DefaultTableModel tableModel = new DefaultTableModel(Constants.CGI_TABLE_COLUMN_NAMES, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(Constants.CGI_TABLE_WIDTH, Constants.CGI_TABLE_HEIGHT));
        panel.add(scrollPane, BorderLayout.NORTH);
        
		// 将组件对象注册到App里面
		App.set("cgiFrame", frame);
		App.set("cgiPanel", panel);
		App.set("cgiTable", table);
        
        // 单元格鼠标右键菜单
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem insertItem = new JMenuItem(Constants.CGI_TABLE_INSERT_ROW);
        JMenuItem appendItem = new JMenuItem(Constants.CGI_TABLE_APPEND_ROW);
        JMenuItem deleteItem = new JMenuItem(Constants.CGI_TABLE_DELETE_ROW);
        insertItem.addActionListener(new InsertRowEventHandler());
        appendItem.addActionListener(new AppendRowEventHandler());
        deleteItem.addActionListener(new DeleteRowEventHandler());
        popupMenu.add(insertItem);
        popupMenu.add(appendItem);
        popupMenu.add(deleteItem);
        popupMenu.addPopupMenuListener(new PopupMenuEventHandler());
        table.setComponentPopupMenu(popupMenu);
        
        // 设置列编辑器
		// 设置鼠标点击一次就能编辑单元格
		((DefaultCellEditor)table.getDefaultEditor(Object.class)).setClickCountToStart(1);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(1).setCellEditor(createTableCellEditor(Constants.CGI_TYPES));
        columnModel.getColumn(3).setCellEditor(createTableCellEditor(Constants.CGI_AUTHORS));
        columnModel.getColumn(4).setCellEditor(createTableCellEditor(Constants.CGI_COMMON_OPTIONS));
        columnModel.getColumn(6).setCellEditor(createTableCellEditor(Constants.CGI_ARG_FROMS));
        columnModel.getColumn(7).setCellEditor(createTableCellEditor(Constants.CGI_COMMON_OPTIONS));
        columnModel.getColumn(8).setCellEditor(createTableCellEditor(Constants.CGI_ARG_TYPES));
        columnModel.getColumn(9).setCellEditor(createTableCellEditor(Constants.CGI_COMMON_OPTIONS));

        // 查找按钮
        JTextField queryText = new JTextField(Constants.CGI_QUERY_TIPS);
        App.set("queryText", queryText);
        queryText.setPreferredSize(new Dimension(Constants.CGI_BUTTON_QUERY_WIDTH, Constants.CGI_BUTTON_QUERY_HEIGHT));
        queryText.addFocusListener(new QueryTextEventHandler());
        JButton queryButton = new JButton(Constants.CGI_BUTTON_QUERY);
        queryButton.addActionListener(new QueryButtonEventHandler());
        // 保存按钮
        JButton saveButton = new JButton(Constants.CGI_BUTTON_SAVE);
        saveButton.addActionListener(new SaveButtonEventHandler());
        // 刷新按钮
        JButton refreshButton = new JButton(Constants.CGI_BUTTON_REFRESH);
        refreshButton.addActionListener(new RefreshButtonEventHandler());
        // 新增按钮
        JButton addButton = new JButton(Constants.CGI_BUTTON_ADD);
        addButton.addActionListener(new AddButtonEventHandler());
        panel.add(queryText, BorderLayout.SOUTH);
        panel.add(queryButton, BorderLayout.SOUTH);
        panel.add(addButton, BorderLayout.SOUTH);
        panel.add(saveButton, BorderLayout.SOUTH);
        panel.add(refreshButton, BorderLayout.SOUTH);
        
		// 显示窗口
		frame.setVisible(true);
		logger.info("cgiForm created");
	}
	
	public static CgiForm getInstance() {
		return cgiForm;
	}
	
	public void render(List<Cgi> cgiList) {
		if (null == cgiList || cgiList.isEmpty()) {
			logger.error("cgiList empty");
			return;
		}
		// 渲染表格数据
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        Object[] rowData = null;
        boolean firstArg = false;
        for (Cgi cgi : cgiList) {
        	firstArg = true;
            for (Arg arg : cgi.getArgList()) {
            	rowData = new Object[12];
            	// 第一个参数才需要设置Cgi信息
                if (firstArg) {
                	rowData[0] = cgi.getName();
                	rowData[1] = cgi.getType();
                	rowData[2] = cgi.getComment();
                	rowData[3] = cgi.getAuthor();
                	rowData[4] = cgi.getNeedLogin();
                }
                rowData[5] = arg.getName();
                rowData[6] = arg.getFrom();
                rowData[7] = arg.getIsRequire();
                rowData[8] = arg.getType();
                rowData[9] = arg.getAllowHtml();
                rowData[10] = arg.getTypeDesc();
                rowData[11] = arg.getComment();
                tableModel.addRow(rowData);
                firstArg = false;
            }
        }
        logger.info("render finished");
	}
	
	/**
	 * 创建列编辑器
	 */
	private TableCellEditor createTableCellEditor(String[] options) {
		JComboBox<String> comboBox = new JComboBox<String>();
		for (String option : options) {
			comboBox.addItem(option);
		}
		return new DefaultCellEditor(comboBox);
	}
}
