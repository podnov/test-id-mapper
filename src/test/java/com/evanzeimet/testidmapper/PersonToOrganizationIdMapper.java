package com.evanzeimet.testidmapper;

class PersonToOrganizationIdMapper extends AbstractTestIdMapper<Person, OrganizationEntity, TestOrganization, Long> {

	public PersonToOrganizationIdMapper() {
		super(new PersonToOrganizationIdProducer());
	}

}