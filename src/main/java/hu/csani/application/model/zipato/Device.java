
package hu.csani.application.model.zipato;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Device {

	@SerializedName("link")
	@Expose
	private String link;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("uuid")
	@Expose
	private String uuid;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("room")
	@Expose
	private Integer room;
	@SerializedName("tags")
	@Expose
	private List<String> tags = null;

	@SerializedName("device_attributes")
	@Expose
	private List<Attribute> deviceAttributes = new ArrayList<>();

}