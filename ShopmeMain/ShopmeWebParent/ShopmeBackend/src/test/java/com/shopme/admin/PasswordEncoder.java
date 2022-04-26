package com.shopme.admin;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
	
	@Test
	public void passwordEndcode() {
		BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
		String raw = "Ritvik";
		String encode = pass.encode(raw);
		System.out.println(encode);
		
		System.out.println(pass.matches(raw, encode));
	}

}
