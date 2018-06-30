package lab7;

import lab8.orm.Table;

@Table(name = "users")

public class Users {
    public String login;
    public String passwordHash;

    public Users () {}
    Users (String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
