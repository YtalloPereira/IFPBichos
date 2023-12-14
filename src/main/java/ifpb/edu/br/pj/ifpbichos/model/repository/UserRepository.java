package ifpb.edu.br.pj.ifpbichos.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ifpb.edu.br.pj.ifpbichos.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByLogin(String login);
}
