package com.evanzeimet.testidmapper;


public interface IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	ReferencePersistenceIdType produceReferenceActualPersistenceId(ReferencePersistenceType persistenceReference);

	String produceReferenceGivenTestId(ReferenceTestType givenTestReference);

	ReferencePersistenceIdType produceReferrersReferencePersistenceId(ReferrerType referrer);

	String produceReferrersReferenceTestId(ReferrerType referrer);

	void setReferrersReferencePersistenceId(ReferrerType referrer,
			ReferencePersistenceIdType referencePersistenceId);

	void setReferrersReferenceTestId(ReferrerType referrer,
			String referenceTestId);
}