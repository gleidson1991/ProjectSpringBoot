package api.school.springboot.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "Table_User")
@DynamicUpdate
public class UserModel extends RepresentationModel<UserModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USER", nullable = false, unique = true)
	private UUID idUser;

	@Column(name = "NAME", nullable = false, length = 250)
	private String name;

	@Email
	@Column(name = "EMAIL", nullable = false, unique = true, length = 250)
	private String email;

	@Column(name = "PASSWORD", nullable = false,length = 60)
	private String password;


	public UserModel(UUID idUser, String name, @Email String email, String password) {
		super();
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UserModel() {
		// TODO Auto-generated constructor stub
	}

	public UUID getIdUser() {
		return idUser;
	}

	public void setIdUser(UUID idUser) {
		this.idUser = idUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, idUser, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		return Objects.equals(email, other.email) && Objects.equals(idUser, other.idUser)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "UserModel [idUser=" + idUser + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}

}
