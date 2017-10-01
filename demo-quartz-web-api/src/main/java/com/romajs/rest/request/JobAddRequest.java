package com.romajs.rest.request;

import lombok.Data;
import org.quartz.Job;

import javax.validation.constraints.NotNull;

@Data
public class JobAddRequest {
	@NotNull
	private boolean replace;
	@NotNull
	private Class<? extends Job> jobClass;
	private String jobDescription;
}
