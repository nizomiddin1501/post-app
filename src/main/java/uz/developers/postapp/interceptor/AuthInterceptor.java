package uz.developers.postapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.developers.postapp.entity.User;
import uz.developers.postapp.exceptions.UserException;
import uz.developers.postapp.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // So‘rovdan "userId" va "password" olish (bu joylashish header orqali bo‘lishi mumkin)
        String userIdHeader = request.getHeader("userId");
        String passwordHeader = request.getHeader("password");

        if (userIdHeader == null || passwordHeader == null) {
            throw new UserException("Authentication headers are missing");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            throw new UserException("Invalid userId format");
        }

        // Foydalanuvchini topish
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(passwordHeader)) {
            throw new UserException("Invalid credentials");
        }

        // Autentifikatsiya muvaffaqiyatli
        request.setAttribute("authenticatedUser", userOptional.get());
        return true;
    }

}
