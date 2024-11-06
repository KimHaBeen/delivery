package kopo.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;


@Entity(name = "address")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@Id
	@Column(name="ADDRESS", length = 100)
	@NotBlank
	private String address;
	
	@Column(name="DETAILADDRESS", length = 100)
	private String detailAddress;
	
	@Column(name="ROLE", length = 2)
	private String role;
	
	@Column(name = "ID", length = 20)
	@NotBlank
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

}
