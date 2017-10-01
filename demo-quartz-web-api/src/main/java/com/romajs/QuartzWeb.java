package com.romajs;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzWeb {

	static final Logger logger = LoggerFactory.getLogger(QuartzWeb.class);

	private static Scheduler scheduler;

	public static Scheduler getInstance() {
		if(scheduler == null) {
			try {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
			} catch (SchedulerException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		return scheduler;
	}

}
