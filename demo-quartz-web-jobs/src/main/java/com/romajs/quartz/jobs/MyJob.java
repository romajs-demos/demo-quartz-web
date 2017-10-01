package com.romajs.quartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJob implements org.quartz.Job {

	final Logger logger = LoggerFactory.getLogger(getClass());

	public MyJob() {
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Hello World!  MyJob is executing.");
	}
}