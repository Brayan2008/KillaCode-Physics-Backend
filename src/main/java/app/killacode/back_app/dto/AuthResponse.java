package app.killacode.back_app.dto;

public record AuthResponse(
        String token,
        String tokenType,
        long expiresIn) {
}
