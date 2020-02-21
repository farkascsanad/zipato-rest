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
import hu.csani.application.model.zipato.Attribute;
import hu.csani.application.service.ZipatoService;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

	@Autowired
	private ZipatoService zipato;

	@RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Attribute> refreshAllDevice() throws IOException, LoginException {

		return zipato.refreshAllAttribute();
	}

}
