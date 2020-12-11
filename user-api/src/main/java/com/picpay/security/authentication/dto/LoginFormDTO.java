package com.picpay.security.authentication.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginFormDTO {

    private String username;
    private String password;

    protected LoginFormDTO() {
    }

    private LoginFormDTO(Builder builder) {
        username = builder.username;
        password = builder.password;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public UsernamePasswordAuthenticationToken converter(LoginFormDTO form) {
        return new UsernamePasswordAuthenticationToken(form.username, form.password);
    }


    public static final class Builder {
        private String username;
        private String password;

        private Builder() {
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public LoginFormDTO build() {
            return new LoginFormDTO(this);
        }
    }

}
