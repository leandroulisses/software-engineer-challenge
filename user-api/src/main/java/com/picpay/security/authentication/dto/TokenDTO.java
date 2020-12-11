package com.picpay.security.authentication.dto;

public class TokenDTO {

    private String token;
    private String type;

    private TokenDTO(Builder builder) {
        token = builder.token;
        type = builder.type;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public static final class Builder {
        private String token;
        private String type;

        private Builder() {
        }

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public TokenDTO build() {
            return new TokenDTO(this);
        }
    }

}
