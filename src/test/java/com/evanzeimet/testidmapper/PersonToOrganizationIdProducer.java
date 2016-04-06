package com.evanzeimet.testidmapper;


public class PersonToOrganizationIdProducer
		implements IdProducer<Person, OrganizationEntity, OrganizationEntity, Long> {

	@Override
	public Long produceReferenceActualPersistenceId(OrganizationEntity persistenceReference) {
		return persistenceReference.getId();
	}

	@Override
	public String produceReferenceGivenTestId(OrganizationEntity testReference) {
		return testReference.getName();
	}

	@Override
	public String produceReferrerGivenTestId(Person referrer) {
		return referrer.getOrganizationTestId();
	}

	@Override
	public void setReferrerExpectedPersistenceId(Person referrer, Long expectedPersistenceId) {
		referrer.setOrganizationId(expectedPersistenceId);
	}

}
