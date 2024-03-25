package ifpb.edu.br.pj.ifpbichos.presentation.controller;

import ifpb.edu.br.pj.ifpbichos.business.service.TokenService;
import ifpb.edu.br.pj.ifpbichos.business.service.UserRegistrationService;
import ifpb.edu.br.pj.ifpbichos.model.entity.User;
import ifpb.edu.br.pj.ifpbichos.model.repository.UserRepository;
import ifpb.edu.br.pj.ifpbichos.presentation.dto.AuthenticationDTO;
import ifpb.edu.br.pj.ifpbichos.presentation.dto.LoginResponseDTO;
import ifpb.edu.br.pj.ifpbichos.presentation.dto.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRegistrationService userRegistrationService;



	public AuthenticationController() {
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setUserRegistrationService(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}
	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
		if (!userRepository.existsByLogin(dto.login())) {
			return ResponseEntity.badRequest().body("Usuário inexistente");
		}

		User user = (User) userRepository.findByLogin(dto.login());

		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);

		var token = tokenService.generateToken((User) auth.getPrincipal());


		return ResponseEntity.ok(new LoginResponseDTO(token, dto.login(),user.getUserRole()));

	}

	@PostMapping("/userRegistration")
	public ResponseEntity userRegistration(@RequestBody UserRegistrationDTO dto) {

		if (userRepository.existsByLogin(dto.login())) {
			return ResponseEntity.badRequest().body("Ja existe uma conta cadastrada com esse email");
		}
		if (userRepository.existsByCPF(dto.CPF())) {
			return ResponseEntity.badRequest().body("CPF já registrado");
		}

		userRegistrationService.registerUser(dto);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/isValidToken")
	public ResponseEntity isValidToken(@RequestParam String token){
		try{
			if(!tokenService.isValidToken(token)) {
				return ResponseEntity.badRequest().body("token invalid!!");
			}
			else{
				return ResponseEntity.ok().build();
			}
		}catch (Exception e) {
			return ResponseEntity.internalServerError().body(e);
		}
	}
}