package hu.csani.application.model.zipato;

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
public class Attribute {

	@SerializedName("uuid")
	@Expose
	public String uuid;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("value")
	@Expose
	public Value value;
	@SerializedName("device")
	@Expose
	public Device device;
	@SerializedName("attributeId")
	@Expose
	public Integer attributeId;

}