
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
public class DeviceDetails {

	@SerializedName("link")
	@Expose
	private String link;
	@SerializedName("icon")
	@Expose
	private Icon icon;
	@SerializedName("room")
	@Expose
	private Room room;
	@SerializedName("showIcon")
	@Expose
	private Boolean showIcon;
	@SerializedName("templateId")
	@Expose
	private String templateId;
	@SerializedName("userIcon")
	@Expose
	private Object userIcon;
	@SerializedName("uuid")
	@Expose
	private String uuid;
	
	

}
