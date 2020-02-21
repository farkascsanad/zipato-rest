package hu.csani.application.model.http;

import java.util.List;

import org.apache.http.cookie.Cookie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpZipatoResponse {

	private String response;
	private List<Cookie> cookies;

}
