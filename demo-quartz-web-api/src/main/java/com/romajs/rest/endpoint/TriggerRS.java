package com.romajs.rest.endpoint;

import com.romajs.QuartzWeb;
import org.quartz.*;

import javax.ws.rs.*;

@Path("/trigger/{name}{group:(/group/[^/]+?)?}")
public class TriggerRS extends AbstractRS {

	@GET
	public Trigger detail(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().getTrigger(TriggerKey.triggerKey(name, group));
	}

	@DELETE
	public boolean delete(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().unscheduleJob(TriggerKey.triggerKey(name, group));
	}

	@POST
	@Path("/pause")
	public void pause(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().pauseTrigger(TriggerKey.triggerKey(name, group));
	}

	@POST
	@Path("/resume")
	public void resume(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		QuartzWeb.getInstance().resumeTrigger(TriggerKey.triggerKey(name, group));
	}

	@GET
	@Path("/state")
	public Trigger.TriggerState x(@PathParam("name") String name, @PathParam("group") String group) throws SchedulerException {
		group = fixOptionalPathParam(group);
		return QuartzWeb.getInstance().getTriggerState(TriggerKey.triggerKey(name, group));
	}

}
