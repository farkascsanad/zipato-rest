package hu.csani.application.model.zipato;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import hu.csani.application.util.Hidden;
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
@Hidden
public class Room {

	@SerializedName("link")
	@Expose
	private String link;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("id")
	@Expose
	private Integer id;

	

}