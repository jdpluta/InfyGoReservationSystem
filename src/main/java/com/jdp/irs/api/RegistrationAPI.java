package com.jdp.irs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.jdp.irs.exception.UserIdAlreadyPresentException;
import com.jdp.irs.model.User;
import com.jdp.irs.service.RegistrationService;

@RestController
@RequestMapping("RegisterAPI")
public class RegistrationAPI {
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private Environment environment;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody User user) throws Exception {
		String returnMessage = null;
		HttpStatus status = null;
		try {
			registrationService.registerUser(user);
			returnMessage = environment.getProperty("RegistrationController.SUCCESSFUL_REGISTRATION");
			status = HttpStatus.OK;
		}
		catch (UserIdAlreadyPresentException e) {
			if (e.getMessage().contains("RegistrationService")) {
				returnMessage = environment.getProperty(e.getMessage());
				status = HttpStatus.BAD_REQUEST;
			}
		}
		return new ResponseEntity<String>(returnMessage, status);
	}
}
