package com.multi_loja.backend.modules.usuarios_lojas.domain.entity;

import com.multi_loja.backend.modules.acesso.domain.entity.NivelAcesso;
import com.multi_loja.backend.modules.lojas.domain.entity.Lojas;
import com.multi_loja.backend.modules.usuarios.domain.entity.Usuarios;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios_lojas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioLoja {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario_id", nullable = false)
    private Usuarios usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_loja_id", nullable = false)
    private Lojas loja;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_nivel_acesso_id", nullable = false)
    private NivelAcesso nivelAcesso;

    @Column(name = "data_associacao", nullable = false)
    @NotNull(message = "A data de associação é obrigatória.")
    private LocalDateTime dataAssociacao;

    @Column(nullable = false)
    private boolean ativo;
}