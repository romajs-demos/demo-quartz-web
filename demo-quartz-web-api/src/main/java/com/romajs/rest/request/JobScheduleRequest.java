package com.romajs.rest.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class JobScheduleRequest {

	private Date endAt;
	private Date startAt;
//	private int priority;
//	private String jobDescription;
//	private String identity;
//	private String jobDataKey;
//	private String jobDataValue;
	private Boolean startNow;
//	private String modifiedByCalendar;
	@NotNull
	private String cronExpression;

}
