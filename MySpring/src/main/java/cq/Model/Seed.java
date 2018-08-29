package cq.Model;

public class Seed {
	private int seed_id;
	private String media_cd;
	private String task_type;
	private String task_content;
	private int interval;
	private String remark;
	private long last_runTime;
	private int items;
	private int priority;

	public int getSeed_id() {
		return seed_id;
	}

	public void setSeed_id(int seed_id) {
		this.seed_id = seed_id;
	}

	public String getMedia_cd() {
		return media_cd;
	}

	public void setMedia_cd(String media_cd) {
		this.media_cd = media_cd;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public String getTask_content() {
		return task_content;
	}

	public void setTask_content(String task_content) {
		this.task_content = task_content;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getLast_runTime() {
		return last_runTime;
	}

	public void setLast_runTime(long last_runTime) {
		this.last_runTime = last_runTime;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
