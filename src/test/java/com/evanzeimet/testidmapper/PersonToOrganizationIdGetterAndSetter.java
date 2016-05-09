package com.evanzeimet.testidmapper;


public class PersonToOrganizationIdGetterAndSetter
		implements IdGetterAndSetter<Person, OrganizationEntity, OrganizationEntity, Long> {

	@Override
	public Long getReferenceActualPersistenceId(OrganizationEntity persistenceReference) {
		return persistenceReference.getId();
	}

	@Override
	public String getReferenceGivenTestId(OrganizationEntity testReference) {
		return testReference.getName();
	}

	@Override
	public Long getReferrersReferencePersistenceId(Person referrer) {
		return referrer.getOrganizationId();
	}

	@Override
	public String getReferrersReferenceTestId(Person referrer) {
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
