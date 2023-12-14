package ifpb.edu.br.pj.ifpbichos.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifpb.edu.br.pj.ifpbichos.model.entity.User;
import ifpb.edu.br.pj.ifpbichos.model.repository.UserRepository;
import ifpb.edu.br.pj.ifpbichos.presentation.dto.AuthenticationDTO;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	

	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDTO dto) {
		User user = userRepository.findByLogin(dto.getEmail());
		
		if(dto.getEmail().equals(user.getEmail()) && dto.getPassword().equals(user.getPassword())){
			return ResponseEntity.ok(user);
		}
		
		return ResponseEntity.badRequest().build();
	}
}
