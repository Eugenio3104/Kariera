package it.kariera.api.dto;

public class UserDTO {


        private Integer id;
        private String name;
        private String surname;
        private String email;

        public UserDTO(){}

        public UserDTO(Integer id, String name, String surname, String email) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.email = email;
        }

        public int getId() {return id;}
        public void setId(int id) {this.id = id;}

        public String getName() {return name;}
        public void setName(String nome) {this.name = nome;}

        public String getSurname() {return surname;}
        public void setSurname(String surname) {this.surname = surname;}

        public String getEmail() {return email;}
        public void setEmail(String email) {this.email = email;}

}
