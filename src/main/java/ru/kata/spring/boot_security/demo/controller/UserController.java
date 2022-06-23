package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@org.springframework.stereotype.Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping(value = "/user")
	public String user(Model model) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.getByEmail(name);
		model.addAttribute("user", user);
		return "user";
	}

}
