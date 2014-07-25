package ca.utoronto.ece1778.services;

public class Result {
	
	private Integer event_id;
	private Integer event_result;
	private Integer time_int;
	private String which_leg;

	public Integer getEvent_id() {
		return event_id;
	}


	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}
	
	public Integer getEvent_result() {
		return event_result;
	}


	public void setEvent_result(Integer event_result) {
		this.event_result = event_result;
	}

	public Integer getTime_int() {
		return time_int;
	}


	public void setTime_int(Integer time_int) {
		this.time_int = time_int;
	}

	public String getWhich_leg()
	{
		return which_leg;
	}


	public void setWhich_leg(String which_leg)
	{
		this.which_leg = which_leg;
	}

	public Result(Integer event_result, Integer time_int, String which_leg) {
		super();
		this.event_result = event_result;
		this.time_int = time_int;
		this.which_leg = which_leg;
	}
	
	public Result(Integer event_id, Integer event_result, Integer time_int, String which_leg) {
		super();
		this.event_id = event_id;
		this.event_result = event_result;
		this.time_int = time_int;
		this.which_leg = which_leg;
	}

	@Override
	public String toString()
	{
		return "Result [event_id=" + event_id + ", event_result=" + event_result + ", time_int=" + time_int + ", which_leg=" + which_leg + "]";
	}
	
}
