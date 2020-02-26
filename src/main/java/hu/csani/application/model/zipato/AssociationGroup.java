package hu.csani.application.model.zipato;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class AssociationGroup {

	@SerializedName("cmdListRaw")
	@Expose
	public Object cmdListRaw;
	@SerializedName("devMap")
	@Expose
	public Object devMap;
	@SerializedName("epMap")
	@Expose
	public Object epMap;
	@SerializedName("groupId")
	@Expose
	public Integer groupId;
	@SerializedName("groupName")
	@Expose
	public String groupName;

}