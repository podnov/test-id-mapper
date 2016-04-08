package com.evanzeimet.testidmapper;

import java.util.List;

public class DefaultTestIdMapper<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	private final IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer;
	private final IdMap<ReferencePersistenceIdType> idMap = new IdMap<>();

	public DefaultTestIdMapper(IdProducer<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer) {
		this.idProducer = idProducer;
	}

	public void mapReferenceActualPersistenceIdsToGivenTestIds(List<? extends ReferencePersistenceType> persistedReferences,
			List<? extends ReferenceTestType> givenTestReferences) {
		int persistedReferenceCount = persistedReferences.size();
		int testReferenceCount = givenTestReferences.size();

		if (persistedReferenceCount != testReferenceCount) {
			String message = String.format("Found [%s] persisted references and [%s] test references. In order to map correctly, the persisted references and test references must map 1-to-1 in order",
					persistedReferenceCount,
					testReferenceCount);
			throw new IllegalArgumentException(message);
		}

		for (int i = 0; i < persistedReferenceCount; i++) {
			ReferencePersistenceType persistedReference = persistedReferences.get(i);
			ReferenceTestType testReference = givenTestReferences.get(i);

			mapReferenceActualPersistenceIdToGivenTestId(persistedReference, testReference);
		}
	}

	protected void mapReferenceActualPersistenceIdToGivenTestId(ReferencePersistenceType persistedReference,
			ReferenceTestType testReference) {
		ReferencePersistenceIdType persistenceId = idProducer.produceReferenceActualPersistenceId(persistedReference);
		String testId = idProducer.produceReferenceGivenTestId(testReference);
		idMap.putPersistenceId(testId, persistenceId);
	}

	protected void setReferrerExpectedPersistenceIdForGivenTestId(ReferrerType referrer) {
		String testId = idProducer.produceReferrerExpectedTestId(referrer);
		ReferencePersistenceIdType persistenceId = idMap.getPersistenceId(testId);
		idProducer.setReferrerExpectedPersistenceId(referrer, persistenceId);
	}

	public void setReferrerExpectedPersistenceIdsForGivenTestIds(List<? extends ReferrerType> referrers) {
		for (ReferrerType referrer : referrers) {
			setReferrerExpectedPersistenceIdForGivenTestId(referrer);
		}
	}
}
