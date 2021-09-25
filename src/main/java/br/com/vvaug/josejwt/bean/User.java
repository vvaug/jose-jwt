package br.com.vvaug.josejwt.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class User {

	private String name;
	private String email;
	private String document;
}
