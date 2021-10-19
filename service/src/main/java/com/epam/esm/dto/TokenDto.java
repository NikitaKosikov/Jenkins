package com.epam.esm.dto;

public class TokenDto {
    private String username;
    private String token;
    private long validityInMilliseconds;

    public TokenDto() {
    }

    public TokenDto(String username, String token, long validityInMilliseconds) {
        this.username = username;
        this.token = token;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }

    public void setValidityInMilliseconds(long validityInMilliseconds) {
        this.validityInMilliseconds = validityInMilliseconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDto tokenDto = (TokenDto) o;
        if (username==null){
            if (tokenDto.username!=null){
                return false;
            }
        }else if (!username.equals(tokenDto.username)){
            return false;
        }
        if (token==null){
            if (tokenDto.token!=null){
                return false;
            }
        }else if (!token.equals(tokenDto.token)){
            return false;
        }
        return validityInMilliseconds == tokenDto.validityInMilliseconds;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + username.hashCode();
        result = 31 * result + token.hashCode();
        return result;
    }
}
