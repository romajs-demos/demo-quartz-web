package com.romajs.rest.endpoint;

import com.romajs.QuartzWeb;
import org.quartz.*;

import javax.ws.rs.*;
import java.util.List;

@Path("/scheduler")
public class SchedulerRS extends AbstractRS {

	@GET
	@Path("/executing")
	public List<JobExecutionContext> executing() throws SchedulerException {
		return QuartzWeb.getInstance().getCurrentlyExecutingJobs();
	}

	@GET
	@Path("/context")
	public SchedulerContext context() throws SchedulerException {
		return QuartzWeb.getInstance().getContext();
	}

	@GET
	@Path("/id")
	public String id() throws SchedulerException {
		return QuartzWeb.getInstance().getSchedulerInstanceId();
	}

	@GET
	@Path("/listener")
	public ListenerManager listener() throws SchedulerException {
		return QuartzWeb.getInstance().getListenerManager();
	}

	@GET
	@Path("/metadata")
	public SchedulerMetaData metadata() throws SchedulerException {
		return QuartzWeb.getInstance().getMetaData();
	}

	@GET
	@Path("/name")
	public String name() throws SchedulerException {
		return QuartzWeb.getInstance().getSchedulerName();
	}

	@POST
	@Path("/pause")
	public void pause() throws SchedulerException {
		QuartzWeb.getInstance().pauseAll();
	}

	@POST
	@Path("/resume")
	public void resume() throws SchedulerException {
		QuartzWeb.getInstance().resumeAll();
	}

	@GET
	@Path("/standby")
	public boolean isStandby() throws SchedulerException {
		return QuartzWeb.getInstance().isInStandbyMode();
	}

	@POST
	@Path("/standby")
	public void standby() throws SchedulerException {
		QuartzWeb.getInstance().standby();
	}

	@GET
	@Path("/start")
	public boolean isStarted() throws SchedulerException {
		return QuartzWeb.getInstance().isStarted();
	}

	@POST
	@Path("/start")
	public void start() throws SchedulerException {
		QuartzWeb.getInstance().start();
	}

	@POST
	@Path("/start/{seconds}")
	public void startDelayed(@PathParam("seconds") int seconds) throws SchedulerException {
		QuartzWeb.getInstance().startDelayed(seconds);
	}

	@GET
	@Path("/shutdown")
	public boolean isShutdown() throws SchedulerException {
		return QuartzWeb.getInstance().isShutdown();
	}

	@POST
	@Path("/shutdown")
	public void stop(@QueryParam("waitForJobsToComplete") Boolean waitForJobsToComplete) throws SchedulerException {
		if(waitForJobsToComplete == null) {
			QuartzWeb.getInstance().shutdown();
		} else {
			QuartzWeb.getInstance().shutdown(waitForJobsToComplete);
		}
	}

}
