package io.egen.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.egen.api.constants.URI;
import io.egen.api.entity.User;
import io.egen.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = URI.USERS)
@Api(tags = "users")
public class UserController {

	private UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Find All Users", notes = "Returns all users")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public List<User> findAll() {
		return service.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = URI.ID)
	@ApiOperation(value = "Find User By Id", notes = "Returns a user if Id exists")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public User findOne(@PathVariable("id") String id) {
		return service.findOne(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create New User", notes = "Creates new User if does not exist")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public User create(@RequestBody User user) {
		return service.create(user);
	}

	@RequestMapping(method = RequestMethod.PUT, value = URI.ID)
	@ApiOperation(value = "Update User", notes = "Updates a user if exists")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public User update(@PathVariable("id") String id, @RequestBody User user) {
		return service.update(id, user);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = URI.ID)
	@ApiOperation(value = "Delete User", notes = "Deletes the user if exists")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public void delete(@PathVariable("id") String id) {
		service.delete(id);
	}
}
