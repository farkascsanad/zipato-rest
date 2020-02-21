package hu.csani.application.controller;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.csani.application.service.ZipatoService;

@RestController
//@RequestMapping("/")
public class BasicController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private ZipatoService zipato;

	@RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String setAccount(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) throws IOException {

		zipato.setUSERNAME(username);
		zipato.setPASSWORD(password);

		return "Done";
	}

	@RequestMapping(value = "/force-auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String setAccount() throws IOException {

		return zipato.generateAuthHeader();

	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String fullRefresh() throws IOException, LoginException {
		zipato.refreshAllDevice();
		zipato.refreshAllAttribute();
		return "Done";

	}

	@RequestMapping("/test")
	public String test(@RequestParam(value = "name", defaultValue = "World") String name) throws IOException {

		return zipato.test();
//		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
