package com.example.personal_stretch_api.dto;

import jakarta.validation.constraints.NotBlank;

public record TrainersDTO(
        @NotBlank(message = "ユーザー名は必須です") String adminName,

        @NotBlank(message = "パスワードは必須です") String adminPassword,

        Integer id,
        String adminEmail,
        Integer role_id) {

}
