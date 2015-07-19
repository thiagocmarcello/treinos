package br.com.novotreino.entidade;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.ForeignKey;

import br.com.novotreino.enums.EPerfil;
import br.com.novotreino.util.CriptografiaUtil;

@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Getter
		private Integer id;
		
		@Getter
		@Setter
		private String nome;
		
		@Getter
		@Setter
		private String cpf;
		
		@Getter
		@Setter
		private String email;
		
		@Getter
		@Setter
		private String login;
		
		@Getter
		@Setter
		private String senha;
		
		@Getter
		@Setter
		private boolean ativo;
		
		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "_endereco")
		@ForeignKey(name = "fk_endereco")
		@Getter
		@Setter
		private Endereco endereco;
		
		@ManyToOne
		@JoinColumn(name = "_academia")
		@ForeignKey(name = "fk_academia")
		@Getter
		@Setter
		private Academia academia;
		
		@Enumerated(EnumType.STRING)
		@Getter
		@Setter
		private EPerfil perfil;
		
		public Usuario(){
		}
		
		public Usuario(Integer id) {
			this.id = id;
		}
		
		@PrePersist
		@PreUpdate
		public void criptografarSenha() {
			senha = CriptografiaUtil.gerarHashSha1(senha);
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public boolean isAtivo() {
			return ativo;
		}

		public void setAtivo(boolean ativo) {
			this.ativo = ativo;
		}

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
		}

		public Academia getAcademia() {
			return academia;
		}

		public void setAcademia(Academia academia) {
			this.academia = academia;
		}

		public EPerfil getePerfil() {
			return perfil;
		}

		public void setePerfil(EPerfil ePerfil) {
			this.perfil = ePerfil;
		}

		public Integer getId() {
			return id;
		}
}
