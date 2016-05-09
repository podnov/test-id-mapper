package com.evanzeimet.testidmapper;

class PersonToOrganizationIdMapper extends DefaultTestIdMapper<Person, OrganizationEntity, OrganizationEntity, Long> {

	public PersonToOrganizationIdMapper() {
		super(new PersonToOrganizationIdGetterAndSetter());
	}

}