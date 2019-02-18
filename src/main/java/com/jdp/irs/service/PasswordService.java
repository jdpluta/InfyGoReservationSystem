package com.jdp.irs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jdp.irs.exception.UserNotFoundException;
import com.jdp.irs.repository.UserRepository;

@Service
public class PasswordService {
	@Autowired
	private UserRepository userRepository;

	public int updatePassword(String email, String phone, String password) throws UserNotFoundException {
		int rowCount = 0;
		rowCount = userRepository.updatePassword(email, phone, password);
		if (rowCount != 1)
			throw new UserNotFoundException("PasswordService.USER_NOT_FOUND");
		return rowCount;
	}
}
