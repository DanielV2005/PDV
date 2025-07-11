package com.multi_loja.backend.modules.lojas.domain.entity;

import com.multi_loja.backend.modules.usuarios_lojas.domain.entity.UsuarioLoja;
import jakarta.persistence.*;


import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lojas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_lojas_cnpj", columnNames = "cnpj")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lojas {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_fantasia", nullable = false)
    private String nomeFantasia;

    @NotBlank
    @Size(max = 150)
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter 14 dígitos numéricos")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Size(max = 50)
    @Column(name = "identificador_schema", nullable = false)
    private String identificadorSchema;

    @PastOrPresent
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.ALL)
    private List<UsuarioLoja> usuariosLojas;
}