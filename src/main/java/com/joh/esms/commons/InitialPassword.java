package com.joh.esms.commons;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class InitialPassword {
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("nihan"));
	}
}
