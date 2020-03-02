package hu.csani.application.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Data;

@Data
public class DayTime {

	private DayOfWeek dayOfWeek;
	private LocalTime time;
	
	

	public DayTime(DayOfWeek dayOfWeek, LocalTime time) {
		this.dayOfWeek = dayOfWeek;
		this.time = time;
		//Test
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayTime other = (DayTime) obj;
		if (dayOfWeek != other.dayOfWeek)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	
	

}
