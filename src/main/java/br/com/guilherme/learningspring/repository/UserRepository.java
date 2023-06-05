package br.com.guilherme.learningspring.repository;

import br.com.guilherme.learningspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
