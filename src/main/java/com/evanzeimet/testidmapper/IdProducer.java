package com.evanzeimet.testidmapper;


public interface IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	ReferencePersistenceIdType produceReferenceActualPersistenceId(ReferencePersistenceType persistenceReference);

	String produceReferenceGivenTestId(ReferenceTestType givenTestReference);

	String produceReferrerExpectedTestId(ReferrerType referrer);

	void setReferrerExpectedPersistenceId(ReferrerType referrer,
			ReferencePersistenceIdType expectedPeristenceId);
}