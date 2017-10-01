package com.romajs.servlet;

import com.romajs.QuartzWeb;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class QuartzWebServletContextListener implements ServletContextListener {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		final String quartzAutostart = servletContextEvent.getServletContext().getInitParameter("quartzAutostart");
		logger.info("quartzAutostart = {}", quartzAutostart);

		if(quartzAutostart != null && !quartzAutostart.trim().isEmpty() && Boolean.TRUE.equals(Boolean.valueOf(quartzAutostart))) {
			try {
				QuartzWeb.getInstance().start();
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}

		logger.info("{} initialized", getClass().getSimpleName());
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

		try {
			QuartzWeb.getInstance().shutdown(false);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		logger.info("{} destroyed", getClass().getSimpleName());
	}
}
