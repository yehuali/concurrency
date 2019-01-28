package com.example.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * SpringSecurity认证流程
 * http://blog.didispace.com/xjf-spring-security-1/
 */
public class AuthenticationExample {
    private static AuthenticationManager am = new SampleAuthenticationManager();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("Please enter your username:");
            String name = in.readLine();
            System.out.println("Please enter your password:");
            String password = in.readLine();
            try{
                Authentication request = new UsernamePasswordAuthenticationToken(name,password);
                Authentication result = am.authenticate(request);
            }catch(AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
            System.out.println("Successfully authenticated. Security context contains: " +
                    SecurityContextHolder.getContext().getAuthentication());

        }
    }

}

class SampleAuthenticationManager implements AuthenticationManager{

    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
       if(auth.getName().equals(auth.getCredentials())){
           return new UsernamePasswordAuthenticationToken(auth.getName(),auth.getCredentials(),AUTHORITIES);
       }
        throw new BadCredentialsException("Bad Creditials");
    }
}
