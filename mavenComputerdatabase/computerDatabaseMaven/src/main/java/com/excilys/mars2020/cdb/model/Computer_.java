package com.excilys.mars2020.cdb.model;

import java.time.LocalDate;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Computer.class)
public abstract class Computer_ {

	public static volatile SingularAttribute<Computer, Long> pcId;
	public static volatile SingularAttribute<Computer, String> name;
	public static volatile SingularAttribute<Computer, LocalDate> introduced;
	public static volatile SingularAttribute<Computer, LocalDate> discontinued;
	public static volatile SingularAttribute<Computer, Company> company;
	
}
