package br.com.rezende.jhm.spring.model;

import br.com.rezende.jhm.spring.configuration.db.MySQLDataSetup;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Getter
@Entity
@Table(schema = MySQLDataSetup.DB_NAME, name = "users")
public class User {
    @Id
    private int id;
    private String name;
    private String email;
    private int age;

    public User() {
    }

    public User(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
