package hu.csani.application.model.zipato;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Data
public class Configuration {

	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("byteSize")
	@Expose
	public String byteSize;
	@SerializedName("value")
	@Expose
	public List<Integer> value = null;

}