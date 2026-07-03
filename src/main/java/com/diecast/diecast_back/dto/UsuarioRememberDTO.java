package com.diecast.diecast_back.dto;

import com.diecast.diecast_back.UserRole;

public record UsuarioRememberDTO(String login,
	    UserRole role) {

}
