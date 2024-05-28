package api.school.springboot.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import api.school.springboot.controller.UserController;
import api.school.springboot.dtos.UserRecordDto;
import api.school.springboot.models.UserModel;
import api.school.springboot.repositories.UserRepository;
import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	private UserRepository<UUID> userRepository;
	@Autowired
	private BCryptPasswordEncoder passworderEncoder;

	@Transactional
	public ResponseEntity<List<UserModel>> findAll() {
		List<UserModel> usersList = userRepository.findAll();
		if (!usersList.isEmpty()) {
			for (UserModel userModel : usersList) {
				UUID idUser = userModel.getIdUser();
				userModel.add(linkTo(methodOn(UserController.class).findById(idUser)).withSelfRel());
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
	}

	@Transactional
	public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserRecordDto userRecordDto) {
		var userModel = new UserModel();
		BeanUtils.copyProperties(userRecordDto, userModel);
		userModel.setPassword(passworderEncoder.encode(userModel.getPassword()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(userModel));
	}

	@Transactional
	public ResponseEntity<Object> findById(@PathVariable(value = "id") UUID idUser) {
		Optional<UserModel> userModel = userRepository.findById(idUser);
		if (!userModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body("user not found");
		}
		userModel.get().add(linkTo(methodOn(UserController.class).findAll()).withRel("Users List"));
		return ResponseEntity.status(HttpStatus.OK).body(userModel.get());
	}

	@Transactional
	public ResponseEntity<Object> deleteById(@PathVariable(value = "id") UUID idUser) {
		Optional<UserModel> userModel = userRepository.findById(idUser);
		if (!userModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
		}
		userRepository.delete(userModel.get());
		return ResponseEntity.status(HttpStatus.OK).body("user deleted");
	}

	@Transactional
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID idUser,
			@RequestBody @Valid UserRecordDto userRecordDto) {
		Optional<UserModel> user = userRepository.findById(idUser);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
		}
		var userModel = user.get();
		BeanUtils.copyProperties(userRecordDto, userModel);
		return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));

	}
}
