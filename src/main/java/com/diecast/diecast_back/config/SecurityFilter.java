package com.diecast.diecast_back.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.diecast.diecast_back.repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if (token != null) {
			var login = tokenService.validateToken(token);
			UserDetails user = usuarioRepository.findByLogin(login);
			
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);

	}

	private String recoverToken(HttpServletRequest request) {
	    // 🔹 1. tenta pelo header Authorization
	    var authHeader = request.getHeader("Authorization");
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        return authHeader.replace("Bearer ", "");
	    }

	    // 🔹 2. fallback: tenta pelos cookies
	    if (request.getCookies() != null) {
	        for (Cookie cookie : request.getCookies()) {
	            if ("token".equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }

	    // 🔹 3. não encontrou
	    return null;
	}
	
//	private String recoverToken(HttpServletRequest request) {
//		var authHeader = request.getHeader("Authorization");
//		if (authHeader == null) {
//			return null;
//		} else {
//			return authHeader.replace("Bearer ", "");
//		}
//	}
	
//	private String recoverToken(HttpServletRequest request) {
//	    if (request.getCookies() == null) return null;
//
//	    for (Cookie cookie : request.getCookies()) {
//	        if (cookie.getName().equals("token")) {
//	            return cookie.getValue();
//	        }
//	    }
//
//	    return null;
//	}

}
