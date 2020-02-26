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
public class ZipatoResponse {

	@SerializedName("success")
	@Expose
	private Boolean success;

	@SerializedName("jsessionid")
	@Expose
	private String jsessionid;

	@SerializedName("nonce")
	@Expose
	private String nonce;

}