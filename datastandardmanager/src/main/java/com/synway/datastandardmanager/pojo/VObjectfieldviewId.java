//package com.synway.datastandardmanager.pojo;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//
///**
// * VObjectfieldviewId entity. @author MyEclipse Persistence Tools
// */
//@Embeddable
//public class VObjectfieldviewId implements java.io.Serializable {
//
//	// Fields
//
//	private Long		objectid;
//
//	private String		fieldid;
//
//	// Constructors
//
//	/** default constructor */
//	public VObjectfieldviewId() {
//	}
//	// Property accessors
//
//	@Column(name = "OBJECTID", nullable = false, precision = 10, scale = 0)
//	public Long getObjectid() {
//		return this.objectid;
//	}
//
//	public void setObjectid(Long objectid) {
//		this.objectid = objectid;
//	}
//
//
//	@Column(name = "FIELDID", nullable = false, length = 100)
//	public String getFieldid() {
//		return this.fieldid;
//	}
//
//	public void setFieldid(String fieldid) {
//		this.fieldid = fieldid;
//	}
//
//
//}