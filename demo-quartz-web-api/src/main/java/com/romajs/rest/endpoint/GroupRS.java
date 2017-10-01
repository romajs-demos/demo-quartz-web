package com.romajs.rest.endpoint;

import com.romajs.QuartzWeb;
import org.quartz.SchedulerException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Set;

@Path("/group")
public class GroupRS extends AbstractRS {

	@GET
	@Path("/names")
	public List<String> names() throws SchedulerException {
		return QuartzWeb.getInstance().getJobGroupNames();
	}

	@GET
	@Path("/paused")
	public Set<String> paused() throws SchedulerException {
		return QuartzWeb.getInstance().getPausedTriggerGroups();
	}

	@GET
	@Path("/trigger")
	public List<String> trigger() throws SchedulerException {
		return QuartzWeb.getInstance().getTriggerGroupNames();
	}

}
