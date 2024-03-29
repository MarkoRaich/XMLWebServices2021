package ftn.xws.userservice.security.auth;

//DTO klasa za login
public class JwtAuthenticationRequest {

private String username;
private String password;

public JwtAuthenticationRequest() {
    super();
}

public JwtAuthenticationRequest(String username, String password) {
    this.setUsername(username);
    this.setPassword(password);
}

public String getUsername() {
    return this.username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getPassword() {
    return this.password;
}

public void setPassword(String password) {
    this.password = password;
}

@Override
public String toString() {
	return "JwtAuthenticationRequest [username=" + username + ", password=" + password + "]";
}


}