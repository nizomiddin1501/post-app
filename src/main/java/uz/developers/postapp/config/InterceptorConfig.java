package uz.developers.postapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uz.developers.postapp.interceptor.AuthInterceptor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {


    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/posts/**") // Faqat `posts` marshrutlari uchun
                .excludePathPatterns("/api/users/**"); // Foydalanuvchilar uchun ochiq marshrutlar
    }










    //    private final GeneralInterceptor generalInterceptor;
//
//    private final CategoryInterceptor categoryInterceptor;
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(generalInterceptor).addPathPatterns("/api/users/**");
//        registry.addInterceptor(categoryInterceptor).addPathPatterns("/api/categories/**");
//    }
}
