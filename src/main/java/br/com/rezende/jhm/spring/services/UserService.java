package br.com.rezende.jhm.spring.services;

import br.com.rezende.jhm.spring.objects.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

//    AerospikeUserRepository aerospikeUserRepository;

    public Optional<User> readUserById(int id) {
        return Optional.empty();//aerospikeUserRepository.findById(id);
    }

    public void addUser(User user) {

    }

    public void removeUserById(int id) {

    }
}
