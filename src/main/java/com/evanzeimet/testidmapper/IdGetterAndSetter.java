package com.evanzeimet.testidmapper;


public interface IdGetterAndSetter<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	ReferencePersistenceIdType getReferenceActualPersistenceId(ReferencePersistenceType persistenceReference);

	String getReferenceGivenTestId(ReferenceTestType givenTestReference);

	ReferencePersistenceIdType getReferrersReferencePersistenceId(ReferrerType referrer);

	String getReferrersReferenceTestId(ReferrerType referrer);

	void setReferrersReferencePersistenceId(ReferrerType referrer,
			ReferencePersistenceIdType referencePersistenceId);

	void setReferrersReferenceTestId(ReferrerType referrer,
			String referenceTestId);

}