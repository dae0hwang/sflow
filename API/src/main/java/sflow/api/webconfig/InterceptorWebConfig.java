package sflow.api.webconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sflow.api.exceptionadvice.interceptor.ExceptionResponseInterceptor;
import sflow.api.exceptionadvice.threadlocal.ErrorInformationTlsContainer;

@Configuration
@RequiredArgsConstructor
public class InterceptorWebConfig implements WebMvcConfigurer {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ExceptionResponseInterceptor(errorInformationTlsContainer))
            .order(1)
            .addPathPatterns("/**");
    }
}
