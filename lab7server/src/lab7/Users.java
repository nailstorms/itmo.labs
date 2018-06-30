package lab7;

import lab8.orm.Table;

@Table(name = "users")

public class Users {
    public String login;
    public String password;

    Users () {}
}
