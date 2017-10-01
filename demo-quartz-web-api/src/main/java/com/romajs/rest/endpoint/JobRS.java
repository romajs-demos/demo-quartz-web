package com.romajs.rest.endpoint;

import com.romajs.QuartzWeb;
import com.romajs.rest.request.JobAddRequest;
import com.romajs.rest.request.JobScheduleRequest;
import org.quartz.*;

import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/job/{name}{group:(/group/[^/]+?)?}")
public class JobRS extends AbstractRS {

	@GET
	public JobDetail detail(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().getJobDetail(JobKey.jobKey(name, group));
	}

	@DELETE
	public boolean delete(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().deleteJob(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/add/durably")
	public JobDetail addDurably(@PathParam("name") String name, @PathParam("group") String group, JobAddRequest request) throws SchedulerException {
		group = fixOptionalPathParam(group);
		final JobBuilder jobBuilder = JobBuilder.newJob(request.getJobClass())
				.withDescription(request.getJobDescription())
				.withIdentity(name, group)
				.storeDurably();
		final JobDetail jobDetail = jobBuilder.build();
		QuartzWeb.getInstance().addJob(jobDetail, request.isReplace());
		return jobDetail;
	}

	@GET
	@Path("/exists")
	public boolean checkExists(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().checkExists(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/interrupt")
	public void interrupt(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().interrupt(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/pause")
	public void pause(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().pauseJob(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/resume")
	public void resume(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().resumeJob(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/schedule")
	public Trigger schedule(@PathParam("name") String name, @PathParam("group") String group, JobScheduleRequest request) throws SchedulerException {
		group = fixOptionalPathParam(group);
		final TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
				.forJob(name, group);
		if(Boolean.TRUE.equals(request.getStartNow())) {
			triggerBuilder.startNow();
		}
		if(request.getStartAt() != null) {
			triggerBuilder.startAt(request.getStartAt());
		}
		if(request.getEndAt() != null) {
			triggerBuilder.endAt(request.getEndAt());
		}
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(request.getCronExpression()));
		final Trigger trigger = triggerBuilder.build();
		QuartzWeb.getInstance().scheduleJob(trigger);
		return trigger;
	}

	@POST
	@Path("/unschedule")
	public boolean unschedule(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		final List<? extends Trigger> triggers = QuartzWeb.getInstance().getTriggersOfJob(JobKey.jobKey(name, group));
		return QuartzWeb.getInstance().unscheduleJobs(triggers.stream().map(Trigger::getKey).collect(Collectors.toList()));
	}

	@GET
	@Path("/trigger")
	public List<? extends Trigger> listTriggers(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().getTriggersOfJob(JobKey.jobKey(name, group));
	}

	@POST
	@Path("/trigger")
	public void trigger(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().triggerJob(JobKey.jobKey(name, group));
	}
}
