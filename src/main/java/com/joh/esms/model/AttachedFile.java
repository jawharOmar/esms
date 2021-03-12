package com.joh.esms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTACHED_FILES")
public class AttachedFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "I_ATTACHED_FILE")
	private Integer id;

	@Column(name = "extension")
	private String extension;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "AttachedFile [id=" + id + ", extension=" + extension + "]";
	}

}
