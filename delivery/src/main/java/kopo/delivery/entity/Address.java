package kopo.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "address")
@Table
@Getter
@Setter
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
	

}
