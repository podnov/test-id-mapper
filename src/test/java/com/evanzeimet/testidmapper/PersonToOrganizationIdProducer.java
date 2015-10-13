package com.evanzeimet.testidmapper;


public class PersonToOrganizationIdProducer
		implements IdProducer<Person, OrganizationEntity, TestOrganization, Long> {

	@Override
	public Long produceReferencePersistenceId(OrganizationEntity persistenceReference) {
		return persistenceReference.getId();
	}

	@Override
	public String produceReferenceTestId(TestOrganization testReference) {
		return testReference.getTestId();
	}

	@Override
	public String produceReferrerReferencedTestId(Person referrer) {
		return referrer.getOrganizationTestId();
	}

	@Override
	public void setReferrerReferencePersistenceId(Person referrer, Long referencePersistenceId) {
		referrer.setOrganizationId(referencePersistenceId);
	}

}
