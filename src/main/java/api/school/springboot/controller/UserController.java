package api.school.springboot.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api.school.springboot.dtos.UserRecordDto;
import api.school.springboot.models.UserModel;
import api.school.springboot.services.UserService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/school")
public class UserController {

	final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;

	}

	@GetMapping("/users")
	public ResponseEntity<ResponseEntity<List<UserModel>>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}

	@PostMapping("/save")
	public ResponseEntity<ResponseEntity<UserModel>> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRecordDto));
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID idUser) {
		ResponseEntity<Object> userModel = userService.findById(idUser);
		return ResponseEntity.status(HttpStatus.OK).body(userModel);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable(value = "id") UUID idUser) {
		userService.findById(idUser);
		return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(idUser));
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID idUser,
			@RequestBody @Valid UserRecordDto userRecordDto) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userRecordDto));

	}
}
