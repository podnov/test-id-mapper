package com.evanzeimet.testidmapper;


public interface IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	ReferencePersistenceIdType produceReferencePersistenceId(ReferencePersistenceType persistenceReference);

	String produceReferenceTestId(ReferenceTestType testReference);

	String produceReferrerReferencedTestId(ReferrerType referrer);

	void setReferrerReferencePersistenceId(ReferrerType referrer,
			ReferencePersistenceIdType referencePersistenceId);
}