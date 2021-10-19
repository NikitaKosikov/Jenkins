package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id==null){
            if (user.id!=null){
                return false;
            }
        }else if (!id.equals(user.id)){
            return false;
        }
        if (username ==null){
            if (user.username !=null){
                return false;
            }
        }else if (!username.equals(user.username)){
            return false;
        }
        if (password==null){
            if (user.password!=null){
                return false;
            }
        }else if (!password.equals(user.password)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}