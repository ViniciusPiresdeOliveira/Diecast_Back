package com.diecast.diecast_back.dto;

import com.diecast.diecast_back.UserRole;

public record UsuarioCadastroDTO(String login, String password, UserRole role) {

}
