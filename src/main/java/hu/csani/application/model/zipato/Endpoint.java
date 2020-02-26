package hu.csani.application.model.zipato;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
@Data
public class Endpoint {

@SerializedName("link")
@Expose
public String link;
@SerializedName("name")
@Expose
public String name;
@SerializedName("room")
@Expose
public Integer room;
@SerializedName("uuid")
@Expose
public String uuid;

}