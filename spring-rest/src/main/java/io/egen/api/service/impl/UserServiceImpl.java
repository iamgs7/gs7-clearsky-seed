package io.egen.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.api.entity.User;
import io.egen.api.exception.BadRequestException;
import io.egen.api.exception.NotFoundException;
import io.egen.api.repository.UserRepository;
import io.egen.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository repository;

	public UserServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(String id) {
		return repository.findOne(id)
				.orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
	}

	@Override
	@Transactional
	public User create(User user) {
		Optional<User> exists = repository.findByEmail(user.getEmail());
		if (exists.isPresent()) {
			throw new BadRequestException("User with email " + user.getEmail() + " does not exist.");
		}
		return repository.create(user);
	}

	@Override
	@Transactional
	public User update(String id, User user) {
		repository.findOne(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist."));
		return repository.update(user);
	}

	@Override
	@Transactional
	public void delete(String id) {
		User existing = repository.findOne(id)
				.orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
		repository.delete(existing);
	}

}
