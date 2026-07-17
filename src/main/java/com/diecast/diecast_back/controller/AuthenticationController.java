package com.diecast.diecast_back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diecast.diecast_back.config.TokenService;
import com.diecast.diecast_back.dto.AuthenticationDTO;
import com.diecast.diecast_back.dto.LoginResponseDTO;
import com.diecast.diecast_back.dto.UsuarioCadastroDTO;
import com.diecast.diecast_back.dto.UsuarioRememberDTO;
import com.diecast.diecast_back.exception.ResourceNotFoundException;
import com.diecast.diecast_back.model.Usuario;
import com.diecast.diecast_back.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	TokenService tokenService;

	@GetMapping("/me")
	public ResponseEntity me(Authentication authentication) {
	    Usuario user = (Usuario) authentication.getPrincipal();

	    return ResponseEntity.ok(
	        new UsuarioRememberDTO(user.getLogin(), user.getRole())
	    );
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Validated AuthenticationDTO data, HttpServletResponse response) {
	    var auth = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(data.login(), data.password())
	    );

	    var usuario = (Usuario) auth.getPrincipal();
	    var token = tokenService.generateToken(usuario);

	    Cookie cookie = new Cookie("token", token);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false); // true em produção (HTTPS)
	    cookie.setPath("/");
	    cookie.setMaxAge(60 * 60 * 18); // 14 h
	    //cookie.setMaxAge(5);

	    response.addCookie(cookie);

	    return ResponseEntity.ok(new LoginResponseDTO(
	    	    token,
	    	    usuario.getLogin(),
	    	    usuario.getRole()
	    	));
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Validated UsuarioCadastroDTO data) {
		if (this.repository.findByLogin(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		} else {
			String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
			Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());
			this.repository.save(newUser);
			return ResponseEntity.ok().build();
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity logout(HttpServletResponse response) {
	    Cookie cookie = new Cookie("token", null);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false);
	    cookie.setPath("/");
	    cookie.setMaxAge(0);

	    response.addCookie(cookie);

	    return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Não é possível deletar: Usuário com ID " + id + " não encontrado.");
		}
		repository.deleteById(id);
	}
}
