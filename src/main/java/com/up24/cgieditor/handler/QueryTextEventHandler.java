package com.up24.cgieditor.handler;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.up24.cgieditor.common.Constants;

public class QueryTextEventHandler implements FocusListener{

	public void focusGained(FocusEvent e) {
		JTextField queryText = (JTextField)e.getComponent();
		String keyword = queryText.getText();
		if (Constants.CGI_QUERY_TIPS.equals(keyword)) {
			queryText.setText(Constants.EMPTY_STRING);
		}
	}

	public void focusLost(FocusEvent e) {
		JTextField queryText = (JTextField)e.getComponent();
		String keyword = queryText.getText();
		if (Constants.EMPTY_STRING.equals(keyword)) {
			queryText.setText(Constants.CGI_QUERY_TIPS);
		}
	}
}
