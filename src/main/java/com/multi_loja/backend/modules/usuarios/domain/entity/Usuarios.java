package com.multi_loja.backend.modules.usuarios.domain.entity;


import com.multi_loja.backend.modules.usuarios_lojas.domain.entity.UsuarioLoja;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "cpf")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuarios {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String senha;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos.")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioLoja> usuariosLojas;

    /**
     * Método utilitário para alterar senha já criptografada
     * (idealmente via serviço de domínio, aqui só exemplo simples).
     */
    public void alterarSenhaCriptografada(String novaSenhaCriptografada) {
        this.senha = novaSenhaCriptografada;
    }
}
