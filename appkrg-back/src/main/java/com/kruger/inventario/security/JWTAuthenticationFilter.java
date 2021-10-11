package com.kruger.inventario.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kruger.inventario.model.User;
import com.kruger.inventario.repository.PermissionProfileRepository;
import com.kruger.inventario.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.kruger.inventario.security.Constants.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    @Autowired
    private PermissionProfileRepository permissionProfileRepository;

    @Autowired
    private UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword(), new ArrayList<>()));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        LocalDateTime currentTime = LocalDateTime.now();
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + TOKEN_EXPIRATION_TIME * 1000 * 60);
        String username = ((UserDetails) auth.getPrincipal()).getUsername();

        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
                .setId("MININTER-JWT")
                .setSubject(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername())
                .claim("authorities",((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getAuthorities())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(expirationDate)
                //.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME).toInstant()))
                .signWith(SignatureAlgorithm.HS512, SUPER_SECRET_KEY).compact();
        System.out.println("___TOK3N: "+TOKEN_BEARER_PREFIX+" "+token);
        response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX+" "+token);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println(auth.getAuthorities());
        System.out.println(userDetails.toString());
        System.out.println("--------- User has authorities: " + userDetails.getAuthorities());
    }

}
