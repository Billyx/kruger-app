package com.kruger.inventario.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);

        if (token != null) {
            String tokenString = token.split(" ")[1];
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            request.setAttribute("Access",0);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY).replace("\"","");
        //String tokken = token.replace(Constants.TOKEN_BEARER_PREFIX+" ", "");
        //System.out.println("TOKEN:-- "+token);
        if (token != null) {
            // Se procesa el token y se recupera el usuario.
            try {
                String user = Jwts.parser()
                        .setSigningKey(Constants.SUPER_SECRET_KEY)
                        .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX + " ", ""))
                        .getBody()
                        .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
                return null;

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
