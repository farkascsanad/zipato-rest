package hu.csani.application.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.csani.application.model.dao.DeviceEntity;
import hu.csani.application.service.ZipatoService;

@RestController
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	private ZipatoService zipato;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String refreshAllDevice() throws IOException, LoginException {

		return zipato.refreshAllDevice();
	}
	
	@RequestMapping(value = "/shutters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DeviceEntity> getShutters() throws IOException, LoginException {

		return zipato.getDevicesByType("actuator.shutters");
	}
}
