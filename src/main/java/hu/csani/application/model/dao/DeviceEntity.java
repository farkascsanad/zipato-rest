package hu.csani.application.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import hu.csani.application.model.zipato.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceEntity {

	@Id
	private String uuid;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "room")
	private String room;

	@Transient
	private Device device;

}