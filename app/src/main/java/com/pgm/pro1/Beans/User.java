package com.pgm.pro1.Beans;

public class User {

    private  String name, dependencia;
    private Boolean isLogged;

    public User() {
    }

    public User(String name, String dependencia, Boolean isLogged) {
        this.name = name;
        this.dependencia = dependencia;
        this.isLogged = isLogged;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public Boolean isLogged() {
        return isLogged;
    }

    public void setLogged(Boolean logged) {
        isLogged = logged;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", dependencia='" + dependencia + '\'' +
                ", isLogged=" + isLogged +
                '}';
    }
}
