package com.datamate.bedrock.practice.domain.valueobject;

import com.datamate.bedrock.framework.common.security.vo.UserDetails;

public class AuthenticationResult {
    private final boolean success;
    private final String errorMessage;
    private final String token;
    private final String refreshToken;
    private final UserDetails userDetails;

    private AuthenticationResult(boolean success, String errorMessage, String token, String refreshToken,
            UserDetails userDetails) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.token = token;
        this.refreshToken = refreshToken;
        this.userDetails = userDetails;
    }

    public static AuthenticationResult success(String token, String refreshToken, UserDetails userDetails) {
        return new AuthenticationResult(true, null, token, refreshToken, userDetails);
    }

    public static AuthenticationResult failure(String errorMessage) {
        return new AuthenticationResult(false, errorMessage, null, null, null);
    }

    public static AuthenticationResult failure(String errorCode, String errorMessage) {
        return new AuthenticationResult(false, errorMessage, null, null, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getUsername() {
        return userDetails != null ? userDetails.username() : null;
    }

    public String getUserId() {
        return userDetails != null ? userDetails.userId() : null;
    }
}
