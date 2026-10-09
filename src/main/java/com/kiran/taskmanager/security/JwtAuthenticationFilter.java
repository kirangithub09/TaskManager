package com.kiran.taskmanager.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kiran.taskmanager.service.CustomUserDetailsService;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService userDetailsService;
	
	public JwtAuthenticationFilter(
			JwtTokenProvider jwtTokenProvider,
			CustomUserDetailsService userDetailsService) {
		this.jwtTokenProvider=jwtTokenProvider;
		this.userDetailsService= userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException{
				
				String authorizationHeader = request.getHeader("Authorization");
				
			if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String token = authorizationHeader.substring(7);
			
			if(jwtTokenProvider.validateToken(token)) {
				
				String username = jwtTokenProvider.getUsernameFromToken(token);
				
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			filterChain.doFilter(request, response);
		
	}
}


/*
 * doFilterInternal() runs on every incoming request:
 * 
 * Reads the Authorization header. If it's missing or doesn't start with Bearer
 * , it passes the request along unchanged (no authentication is set).
 *  Extracts the token by removing the first 7 characters ("Bearer "). Validates the token
 * with jwtTokenProvider.validateToken(). If valid, gets the username from the
 * token and loads the user through CustomUserDetailsService. Builds a
 * UsernamePasswordAuthenticationToken (user details, no credentials,
 * authorities/roles) and attaches request details such as IP and session info.
 * Stores it in SecurityContextHolder, which marks the current request as
 * authenticated. Calls filterChain.doFilter() to continue to the controller.
 * 
 * In short: this is the gatekeeper that turns a Bearer <token> header into an authenticated user for that request. It's registered in SecurityConfig before UsernamePasswordAuthenticationFilter.
 */
