package br.com.rezende.jhm.spring.services;

import br.com.rezende.jhm.spring.model.User;
import br.com.rezende.jhm.spring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class UserService {

    private UserRepository userRepository;

    public List<User> readUserByName(String name) {
        return userRepository.findByName(name);
    }
}
