package co.id.wahyu.auth.jwt.config;

import co.id.wahyu.auth.jwt.util.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String CONTEXT_PATH;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(CONTEXT_PATH + "/login") || request.getServletPath().equals(CONTEXT_PATH + "refresh-token")) {
            log.info("Login refresh access");
            filterChain.doFilter(request, response);
        } else {
            log.info("Access with token");
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                try{
                    JwtService jwtService = new JwtService();
                    String token = authHeader.substring("Bearer ".length());

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    jwtService.getUsernameFromToken(token),
                                    null,
                                    jwtService.getAuthoritiesFromToken(token));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());

                    Map<String, String> errorBody = new HashMap<>();
                    errorBody.put("errorMessage", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errorBody);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
