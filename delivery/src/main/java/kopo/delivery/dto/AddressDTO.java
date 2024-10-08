package kopo.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
	
	@JsonProperty("address")
	private String address;
	private String detailAddress;
	private String role;
	private String id;

}
