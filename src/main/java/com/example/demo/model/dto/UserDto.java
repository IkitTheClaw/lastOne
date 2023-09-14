package com.example.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Objects;


@Builder
public class UserDto {
    private String username;
    private String name;
    private String surname;
    private String address;
    private String password;
    private List<String> roles;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && Objects.equals(name, userDto.name) && Objects.equals(surname, userDto.surname) && Objects.equals(address, userDto.address) && Objects.equals(password, userDto.password) && Objects.equals(roles, userDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, surname, address, password, roles);
    }
}
    //    public static void doSomething(int i){
//        System.out.println(i);
//        UserDto userDto = new UserDto("gello");
//        userDto.doSomethingElse(6);
//    }
//    public void doSomethingElse(int j){
//        doSomething(5);
//    }

