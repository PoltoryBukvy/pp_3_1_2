package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "admin")
public class AdminController {

	private final UserService userService;

	public AdminController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		model.addAttribute("user", new User());
		model.addAttribute("allRoles", userService.getAllRoles());
		return "list";
	}

	@GetMapping
	public String restapi() {
		return "view";
	}

	@GetMapping("/user")
	public String form(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("allRoles", userService.getAllRoles());
		return "form";
	}

	@PostMapping("/user")
	public String save(@ModelAttribute User user, Model model, @RequestParam("roless") String[] roless) {
		List<Role> roles = new ArrayList<>();
		for (String roleId : roless) {
			roles.add(new Role(Long.parseLong(roleId)));
		}
		user.setRoles(roles);
		model.addAttribute("user", userService.save(user));
		return "redirect:";
	}

	@GetMapping(value = "/user/{id}")
	public String show(@PathVariable(value = "id") long id, Model model, @RequestParam("delete") Optional<Boolean> isDelete) {
		User user = userService.find(id);
		if (user == null) {
			return "404";
		}
		model.addAttribute("user", user);
		model.addAttribute("readonly", isDelete.isPresent() && isDelete.get());
		model.addAttribute("allRoles", userService.getAllRoles());
		return "form";
	}

	@PostMapping("/user/{id}")
	public String edit(@ModelAttribute User user, Model model, @RequestParam("roless") Optional<String[]> roless) {
		List<Role> roles = new ArrayList<>();
		if (roless.isPresent()) {
			for (String roleId : roless.get()) {
				roles.add(new Role(Long.parseLong(roleId)));
			}
		}
		user.setRoles(roles);
		model.addAttribute("user", userService.update(user));
		return "redirect:/admin/";
	}

	@GetMapping(value = "/user/{id}/delete")
	public String delete(@PathVariable(value = "id") long id, Model model) {
		User user = userService.find(id);
		if (user == null) {
			return "404";
		}
		userService.delete(user);
		return "redirect:/admin/";
	}
}
