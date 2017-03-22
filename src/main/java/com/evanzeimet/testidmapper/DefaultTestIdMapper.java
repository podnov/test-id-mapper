package com.evanzeimet.testidmapper;

import java.util.List;

public class DefaultTestIdMapper<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> {

	protected final IdGetterAndSetter<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer;
	protected final IdMap<ReferencePersistenceIdType> idMap = new IdMap<>();

	public DefaultTestIdMapper(IdGetterAndSetter<ReferrerType, ReferencePersistenceType, ReferenceTestType, ReferencePersistenceIdType> idProducer) {
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
		ReferencePersistenceIdType persistenceId = idProducer.getReferenceActualPersistenceId(persistedReference);
		String testId = idProducer.getReferenceGivenTestId(testReference);
		idMap.mapIds(testId, persistenceId);
	}

	protected void setReferrerReferencePersistenceIdForTestId(ReferrerType referrer) {
		String testId = idProducer.getReferrersReferenceTestId(referrer);
		ReferencePersistenceIdType persistenceId = idMap.getPersistenceId(testId);
		idProducer.setReferrersReferencePersistenceId(referrer, persistenceId);
	}

	public void setReferrerReferencePersistenceIdsForTestIds(List<? extends ReferrerType> referrers) {
		for (ReferrerType referrer : referrers) {
			setReferrerReferencePersistenceIdForTestId(referrer);
		}
	}

	protected void setReferrerReferenceTestIdForPersistenceId(ReferrerType referrer) {
		ReferencePersistenceIdType persistenceId = idProducer.getReferrersReferencePersistenceId(referrer);
		String testId = idMap.getTestId(persistenceId);
		idProducer.setReferrersReferenceTestId(referrer, testId);
	}

	public void setReferrerReferenceTestIdsForPersistenceIds(List<? extends ReferrerType> referrers) {
		for (ReferrerType referrer : referrers) {
			setReferrerReferenceTestIdForPersistenceId(referrer);
		}
	}
}
