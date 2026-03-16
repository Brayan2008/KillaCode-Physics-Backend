package app.killacode.back_app.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/test")
public class TestSecurity {

    @Autowired
    private SessionRegistry session;

    @GetMapping("/session")
    public ResponseEntity<?> getSesion() {

        String id = "";

        User user = null;

        List<Object> sesiones = session.getAllPrincipals();

        for (Object object : sesiones) {
            if (object instanceof User) {
                user = (User) object;
                
            }
            
            List<SessionInformation> infoSesion = session.getAllSessions(object, false);

            for (SessionInformation info : infoSesion) {
                id = info.getSessionId();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("Session", user);
        response.put("id", id);

        return ResponseEntity.ok(response);
    }
    

}
