package br.com.rezende.jhm.spring.controllers;

import br.com.rezende.jhm.spring.model.User;
import br.com.rezende.jhm.spring.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/users")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true)
public class UserController {

    public static final String USER_NOT_FOUND = "User not found";
    public static final String CONTACT_THE_ADMINISTRATOR = "Contact the administrator";
    UserService userService;

    @PostMapping(value = "/user", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> findByName(@RequestBody String name) {
        List<User> response = userService.readUserByName(name);
//        if(response.isEmpty()) {
//            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
//        }
        if(!CollectionUtils.isEmpty(response)) {
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        User user = response.get(0);
        try {
            return ResponseEntity.ok().body(objectMapper.writeValueAsString(user));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CONTACT_THE_ADMINISTRATOR);
        }
    }
}
