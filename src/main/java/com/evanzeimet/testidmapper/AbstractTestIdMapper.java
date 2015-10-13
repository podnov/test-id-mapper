package com.evanzeimet.testidmapper;

import java.util.List;

public abstract class AbstractTestIdMapper<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	private IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer;
	private IdMap<ReferencePersistenceIdType> idMap = new IdMap<>();

	public AbstractTestIdMapper(IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer) {
		this.idProducer = idProducer;
	}

	public void mapReferenceIds(List<? extends ReferencePersistenceType> persistedReferences,
			List<? extends ReferenceTestType> testReferences) {
		int persistedReferenceCount = persistedReferences.size();
		int testReferenceCount = testReferences.size();

		if (persistedReferenceCount != testReferenceCount) {
			String message = String.format("Found [%s] persisted references and [%s] test references. In order to map correctly, the persisted references and test references must map 1-to-1 in order",
					persistedReferenceCount,
					testReferenceCount);
			throw new IllegalArgumentException(message);
		}

		for (int i = 0; i < persistedReferenceCount; i++) {
			ReferencePersistenceType persistedReference = persistedReferences.get(i);
			ReferenceTestType testReference = testReferences.get(i);

			mapReferenceIds(persistedReference, testReference);
		}
	}

	protected void mapReferenceIds(ReferencePersistenceType persistedReference,
			ReferenceTestType testReference) {
		ReferencePersistenceIdType persistenceId = idProducer.produceReferencePersistenceId(persistedReference);
		String testId = idProducer.produceReferenceTestId(testReference);
		idMap.putPersistenceId(testId, persistenceId);
	}

	protected void setReferrerReferencePersistenceId(ReferrerType referrer) {
		String testId = idProducer.produceReferrerReferencedTestId(referrer);
		ReferencePersistenceIdType persistenceId = idMap.getPersistenceId(testId);
		idProducer.setReferrerReferencePersistenceId(referrer, persistenceId);
	}

	public void setReferrerReferencePersistenceIdsForTestIds(List<? extends ReferrerType> referrers) {
		for (ReferrerType referrer : referrers) {
			setReferrerReferencePersistenceId(referrer);
		}
	}
}
