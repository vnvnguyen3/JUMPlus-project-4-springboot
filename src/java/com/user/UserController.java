package com.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	UserRepository repo;
	
	//CREATE
	@PostMapping("/add/user")
	public User createUser(@RequestBody User user) {
		repo.save(user);
		return user;
	}
	
	//RETREIVE
	@GetMapping("users")
	public List<User> getAllUsers(){
		return repo.findAll();
	}
	
	//get by id
	@GetMapping("users/{id}")
	public User getUser(@PathVariable long id) {
		Optional<User> optUser = repo.findById(id);
		
		if(optUser.isPresent()) {
			return optUser.get();
		}
		
		return new User();
	}
	
	//update by id
	@PutMapping("update/user/{id}")
	public String updateUser(@RequestBody User user) {
		Optional<User> found = repo.findById(user.getId());
		
		if(found.isPresent()) {
			repo.save(user);
			return "Saved: = " + user.toString();
		} else {
			return "Could not update user with id = " + user.getId();
		}
	}
	
	//DELETE
	@DeleteMapping("/delete/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable long id){
		Optional<User> found = repo.findById(id);
		
		if(found.isPresent()) {
			repo.deleteById(id);
			return ResponseEntity.status(200).body("Deleted user with id = " + id);
		} else {
			return ResponseEntity.status(404).body("User with id = " + id + " not found.");
		}
	}
}
