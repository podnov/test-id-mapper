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
	public Long produceReferrersReferencePersistenceId(Person referrer) {
		return referrer.getOrganizationId();
	}

	@Override
	public String produceReferrersReferenceTestId(Person referrer) {
		return referrer.getOrganizationTestId();
	}

	@Override
	public void setReferrersReferencePersistenceId(Person referrer, Long referencePersistenceId) {
		referrer.setOrganizationId(referencePersistenceId);
	}

	@Override
	public void setReferrersReferenceTestId(Person referrer, String referenceTestId) {
		referrer.setOrganizationTestId(referenceTestId);
	}

}
