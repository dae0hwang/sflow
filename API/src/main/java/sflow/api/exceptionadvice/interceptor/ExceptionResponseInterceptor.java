package sflow.api.exceptionadvice.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import sflow.api.dto.ErrorInformationDto;
import sflow.api.exceptionadvice.threadlocal.ErrorInformationTlsContainer;

/**
 * 예외가 발생했을 때(ThreadLocal<ErrorInformationDto>채워져 있을 때)
 * response 정보를 채워주는 인터셉터
 */
@Slf4j
@RequiredArgsConstructor
public class ExceptionResponseInterceptor implements HandlerInterceptor {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        threadLocalExceptionControl(response);
    }

    private void threadLocalExceptionControl(HttpServletResponse response)
        throws IOException {
        if (!errorInformationTlsContainer.getThreadLocal().get().getErrorType().equals("none")) {
            ErrorInformationDto errorInformation = errorInformationTlsContainer.getThreadLocal()
                .get();
            errorInformationTlsContainer.removeThreadLocal();
            String errorResponseBody = objectMapper.writeValueAsString(errorInformation);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(errorResponseBody);
        }
    }
}
