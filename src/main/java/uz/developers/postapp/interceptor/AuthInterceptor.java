package uz.developers.postapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.developers.postapp.entity.User;
import uz.developers.postapp.exceptions.UserException;
import uz.developers.postapp.repository.UserRepository;

import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new UserException("Authentication headers are missing or invalid");
        }

        String base64Credentials = authHeader.substring(6);
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);
        if (values.length != 2) {
            throw new UserException("Invalid Basic Authentication format");
        }
        String email = values[0];
        String password = values[1];

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(password)) {
            throw new UserException("Invalid username or password");
        }
        request.setAttribute("authenticatedUser", userOptional.get());
        return true;
    }

}
