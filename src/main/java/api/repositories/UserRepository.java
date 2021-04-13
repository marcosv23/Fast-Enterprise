package api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findBycpf(String cpf);

	public Optional<User> findByEmail(String email);

	public Optional<User> findByLogin(String login);

}
