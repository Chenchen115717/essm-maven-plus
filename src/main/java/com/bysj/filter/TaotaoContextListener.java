package com.bysj.filter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.bysj.tools.Constant;

public class TaotaoContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

			ServletContext context = event.getServletContext();
			Constant.DICTIONARIES = Constant.getDictionary();
			context.setAttribute("SYS_NAME", Constant.SYS_NAME);
			context.setAttribute("dictionary", Constant.DICTIONARIES);
			System.out.println(context.getContextPath() + " starting ....");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
