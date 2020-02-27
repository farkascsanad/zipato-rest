
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
	public String link;
	@SerializedName("config")
	@Expose
	public Config config;
	@SerializedName("endpoints")
	@Expose
	public List<Endpoint> endpoints = null;
	@SerializedName("icon")
	@Expose
	public Icon icon;
	@SerializedName("room")
	@Expose
	public Object room;
//	@SerializedName("showIcon")
//	@Expose
//	public Boolean showIcon;
	@SerializedName("templateId")
	@Expose
	public String templateId;
	@SerializedName("userIcon")
	@Expose
	public Icon userIcon;
	@SerializedName("uuid")
	@Expose
	public String uuid;

//	@SerializedName("link")
//	@Expose
//	private String link;
//	@SerializedName("name")
//	@Expose
//	private String name;
//	@SerializedName("uuid")
//	@Expose
//	private String uuid;
//	@SerializedName("description")
//	@Expose
//	private String description;
//	@SerializedName("room")
//	@Expose
//	private Room room;
//	@SerializedName("tags")
//	@Expose
//	private List<String> tags = null;
//	@SerializedName("icon")
//	@Expose
//	private Icon icon;
//	@SerializedName("showIcon")
//	@Expose
//	private Boolean showIcon;
//	@SerializedName("templateId")
//	@Expose
//	private String templateId;
//	@SerializedName("userIcon")
//	@Expose
//	private Object userIcon;
//
	@SerializedName("device_attributes")
	@Expose
	private Attribute deviceAttribute;
//
//	@SerializedName("config")
//	@Expose
//	public Config config;
//	@SerializedName("endpoints")
//	@Expose
//	public List<Endpoint> endpoints = null;

}