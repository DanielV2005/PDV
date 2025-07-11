package com.multi_loja.backend.modules.acesso.domain.entity;

import com.multi_loja.backend.modules.usuarios_lojas.domain.entity.UsuarioLoja;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nivel_acesso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelAcesso {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O nome do nível de acesso é obrigatório.")
    @Size(max = 50, message = "O nome pode ter no máximo 50 caracteres.")
    private String nome;

    @Column(columnDefinition = "text")
    @Size(max = 500, message = "A descrição pode ter no máximo 500 caracteres.")
    private String descricao;

    @OneToMany(mappedBy = "nivelAcesso", cascade = CascadeType.ALL)
    private List<UsuarioLoja> usuariosLojas;
}