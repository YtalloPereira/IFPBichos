package ifpb.edu.br.pj.ifpbichos.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifpb.edu.br.pj.ifpbichos.model.entity.User;
import ifpb.edu.br.pj.ifpbichos.model.repository.UserRepository;

@Service
public class AuthorizationService  {
	
	@Autowired
	UserRepository userRepository;

	public User loadUserByUsername(String username) {
		return userRepository.findByLogin(username);
	}

}
