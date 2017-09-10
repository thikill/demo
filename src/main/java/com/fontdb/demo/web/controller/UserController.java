package com.fontdb.demo.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fontdb.demo.model.User;
import com.fontdb.demo.service.RoleService;
import com.fontdb.demo.service.UserService;
import com.fontdb.demo.service.mapper.UserMapper;
import com.fontdb.demo.web.dto.JqGridDto;
import com.fontdb.demo.web.dto.UserDto;

@Controller
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Value("${user.default.password}")
	private String defaultPassword;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UserMapper userMapper;

	@RequestMapping(method = RequestMethod.GET, path = "/crud")
	public @ResponseBody JqGridDto<UserDto> getAll(@ModelAttribute User user, Pageable pageable) {
		logger.debug("Received request to get all users");
		Pageable pageRequest = new PageRequest(pageable.getPageNumber() - 1, pageable.getPageSize());
		Page<User> pageUser = userService.page(pageRequest);
		return userMapper.pageToJqGrid(pageUser);
	}

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping("/users")
	public String index(Model model) {
		logger.info("This is an info message to test logging");
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.findAll());
		return "list";
	}

	@GetMapping("/users/create")
	public String create(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.findAll());
		return "form";
	}

	@PostMapping("/users/search")
	public String search(@ModelAttribute User user, Model model) {
		model.addAttribute("users", userService.searchUser(user));
		model.addAttribute("roles", roleService.findAll());
		return "list";
	}

	@GetMapping("/users/cancel")
	public String cancel(RedirectAttributes redirect) {
		return "redirect:/users";
	}

	@PostMapping("/users/save")
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (result.hasErrors() == true) {
			model.addAttribute("roles", roleService.findAll());
			return "form";
		}
		user.setPassword(defaultPassword);
		user.setPassword(encoder.encode(user.getPassword()));
		userService.save(user);
		redirect.addFlashAttribute("success", "Saved successfully!");
		return "redirect:/users";
	}

	@GetMapping("/users/{id}/edit")
	public String edit(@PathVariable Integer id, ModelMap model) {
		model.addAttribute("user", userService.findOne(id));
		model.addAttribute("roles", roleService.findAll());
		return "form";
	}

	@GetMapping("/users/{id}/delete")
	public String delete(@PathVariable Integer id, RedirectAttributes redirect) {
		userService.delete(id);
		redirect.addFlashAttribute("success", "Deleted successfully!");
		return "redirect:/users";
	}

}
