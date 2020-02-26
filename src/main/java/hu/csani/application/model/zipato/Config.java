package hu.csani.application.model.zipato;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Data
public class Config {

	@SerializedName("className")
	@Expose
	public String className;
	@SerializedName("appVersion")
	@Expose
	public Integer appVersion;
	@SerializedName("assocGrpBlacklist")
	@Expose
	public List<Object> assocGrpBlacklist = null;
	@SerializedName("zwManufacturerId")
	@Expose
	public Integer zwManufacturerId;
	@SerializedName("securelyIncluded")
	@Expose
	public Boolean securelyIncluded;
	@SerializedName("type")
	@Expose
	public String type;
	@SerializedName("uuid")
	@Expose
	public String uuid;
	@SerializedName("specificDevClass")
	@Expose
	public String specificDevClass;
	@SerializedName("appSubVersion")
	@Expose
	public Integer appSubVersion;
	@SerializedName("model")
	@Expose
	public Object model;
	@SerializedName("basicDevClass")
	@Expose
	public String basicDevClass;
	@SerializedName("order")
	@Expose
	public Object order;
	@SerializedName("wakeUpInterval")
	@Expose
	public Integer wakeUpInterval;
	@SerializedName("productId")
	@Expose
	public Integer productId;
	@SerializedName("periodicallyWakeUp")
	@Expose
	public Boolean periodicallyWakeUp;
	@SerializedName("genericDevClass")
	@Expose
	public String genericDevClass;
	@SerializedName("minWakeUpInterval")
	@Expose
	public Integer minWakeUpInterval;
	@SerializedName("sensor1000ms")
	@Expose
	public Boolean sensor1000ms;
	@SerializedName("tags")
	@Expose
	public Object tags;
	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("crc16Encap")
	@Expose
	public Boolean crc16Encap;
	@SerializedName("alwaysListening")
	@Expose
	public Boolean alwaysListening;
	@SerializedName("nodeId")
	@Expose
	public Integer nodeId;
	@SerializedName("dsk")
	@Expose
	public Object dsk;
	@SerializedName("status")
	@Expose
	public String status;
	@SerializedName("discovered")
	@Expose
	public Object discovered;
	@SerializedName("sensor250ms")
	@Expose
	public Boolean sensor250ms;
	@SerializedName("hidden")
	@Expose
	public Boolean hidden;
//	@SerializedName("configuration")
//	@Expose
//	public List<Configuration> configuration = null;
	@SerializedName("descriptorFlags")
	@Expose
	public Object descriptorFlags;
	@SerializedName("neighborNodes")
	@Expose
	public List<String> neighborNodes = null;
	@SerializedName("description")
	@Expose
	public String description;
	@SerializedName("roleType")
	@Expose
	public String roleType;
	@SerializedName("sleeping")
	@Expose
	public Boolean sleeping;
	@SerializedName("noBatteryCheck")
	@Expose
	public Boolean noBatteryCheck;
	@SerializedName("usesStateChangeNotification")
	@Expose
	public Boolean usesStateChangeNotification;
	@SerializedName("wakeUpIntervalStep")
	@Expose
	public Integer wakeUpIntervalStep;
	@SerializedName("locationId")
	@Expose
	public Object locationId;
	@SerializedName("maxWakeUpInterval")
	@Expose
	public Integer maxWakeUpInterval;
	@SerializedName("iconType")
	@Expose
	public String iconType;
	@SerializedName("firmware")
	@Expose
	public Object firmware;
	@SerializedName("alternateVnodeAssoc")
	@Expose
	public Boolean alternateVnodeAssoc;
	@SerializedName("listening")
	@Expose
	public Boolean listening;
	@SerializedName("ignoreMulticasts")
	@Expose
	public Object ignoreMulticasts;
	@SerializedName("manufacturerId")
	@Expose
	public Object manufacturerId;
	@SerializedName("defaultWakeUpInterval")
	@Expose
	public Integer defaultWakeUpInterval;
	@SerializedName("productTypeId")
	@Expose
	public Integer productTypeId;
	@SerializedName("room")
	@Expose
	public Integer room;
	@SerializedName("associationGroups")
	@Expose
	public List<AssociationGroup> associationGroups = null;
	@SerializedName("eventMap")
	@Expose
	public Object eventMap;
	@SerializedName("serial")
	@Expose
	public Object serial;
	@SerializedName("user")
	@Expose
	public Object user;

}