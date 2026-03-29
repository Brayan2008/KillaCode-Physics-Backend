package app.killacode.back_app.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
public class TestSecurity {

    @GetMapping("/session")
    public ResponseEntity<?> getSesion(Authentication authentication) {

        Map<String, Object> response = new HashMap<>();
        if (authentication == null) {
            response.put("session", null);
            response.put("authenticated", false);
            response.put("authorities", List.of());
            return ResponseEntity.ok(response);
        }

        response.put("session", authentication.getName());
        response.put("authenticated", authentication.isAuthenticated());
        response.put("authorities", authentication.getAuthorities());

        return ResponseEntity.ok(response);
    }
    

}
