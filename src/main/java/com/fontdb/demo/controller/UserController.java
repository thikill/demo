package com.fontdb.demo.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.fontdb.demo.dto.UserDto;
import com.fontdb.demo.json.CustomUserResponse;
import com.fontdb.demo.mapper.UserMapper;
import com.fontdb.demo.model.User;
import com.fontdb.demo.service.RoleService;
import com.fontdb.demo.service.UserService;
import com.google.gson.Gson;

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
	public @ResponseBody CustomUserResponse getAll(@RequestParam(defaultValue = "0") final int page,
			@RequestParam(defaultValue = "100") final int rows, @RequestParam(required = false) final String code,
			@RequestParam(required = false) final String name) {
		logger.debug("Received request to get all users");

		// Retrieve all users from the service
		List<User> users = new ArrayList<>();
		  Pageable pageRequest = new PageRequest(page-1, rows); 
		users = userService.page(pageRequest).getContent();

		// Initialize our custom user response wrapper
		CustomUserResponse response = new CustomUserResponse();

		// Assign the result from the service to this response
		List<UserDto> userDtos = new ArrayList<>();
		//users.forEach(u);
		response.setRows(userMapper.usersToUserDtos(users));

		// Assign the total number of records found. This is used for paging
		response.setRecords(users.size());

		// Since our service is just a simple service for teaching purposes
		// We didn't really do any paging. But normally your DAOs or your
		// persistence layer should support this
		// Assign a dummy page
		response.setPage(page);

		// Same. Assign a dummy total pages
		response.setTotal(userService.page(pageRequest).getTotalPages());

		// Return the response
		// Spring will automatically convert our CustomUserResponse as JSON
		// object.
		// This is triggered by the @ResponseBody annotation.
		// It knows this because the JqGrid has set the headers to accept JSON
		// format when it made a request
		// Spring by default uses Jackson to convert the object to JSON
		Gson gson = new Gson();
		List<User> userss = new ArrayList<>();
		userService.findAll().forEach(userss::add);
				
		return response;
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

	@GetMapping("/user")
	public String index(Model model) {
		logger.info("This is an info message to test logging");
		model.addAttribute("users", userService.findAll());
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.findAll());
		return "list";
	}

	@GetMapping("/user/create")
	public String create(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("roles", roleService.findAll());
		return "form";
	}

	@PostMapping("/user/search")
	public String search(@ModelAttribute User user, Model model) {
		model.addAttribute("users", userService.searchUser(user));
		model.addAttribute("roles", roleService.findAll());
		return "list";
	}

	@GetMapping("/user/cancel")
	public String cancel(RedirectAttributes redirect) {
		return "redirect:/user";
	}

	@PostMapping("/user/save")
	public String save(@Valid User user, BindingResult result, RedirectAttributes redirect, Model model) {
		if (result.hasErrors() == true) {
			model.addAttribute("roles", roleService.findAll());
			return "form";
		}
		user.setPassword(defaultPassword);
		user.setPassword(encoder.encode(user.getPassword()));
		userService.save(user);
		redirect.addFlashAttribute("success", "Saved successfully!");
		return "redirect:/user";
	}

	@GetMapping("/user/{id}/edit")
	public String edit(@PathVariable Integer id, ModelMap model) {
		model.addAttribute("user", userService.findOne(id));
		model.addAttribute("roles", roleService.findAll());
		return "form";
	}

	@GetMapping("/user/{id}/delete")
	public String delete(@PathVariable Integer id, RedirectAttributes redirect) {
		userService.delete(id);
		redirect.addFlashAttribute("success", "Deleted successfully!");
		return "redirect:/user";
	}

}
