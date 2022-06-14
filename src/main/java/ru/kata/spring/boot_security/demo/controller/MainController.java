package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

	private final UserService userService;

	public MainController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping(value = "/user")
	public String user(Model model) {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByEmail(name).get();
		model.addAttribute("user", user);
		return "user";
	}

}
