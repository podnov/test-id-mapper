package com.evanzeimet.testidmapper;

import java.util.HashMap;
import java.util.Map;

public class IdMap<ReferencePersistenceIdType> {

	private final Map<String /* testId */, ReferencePersistenceIdType> testIdToPersistenceIdMap = new HashMap<>();
	private final Map<ReferencePersistenceIdType, String /* testId */> persistenceIdToTestIdMap = new HashMap<>();

	public ReferencePersistenceIdType getPersistenceId(String testId) {
		return testIdToPersistenceIdMap.get(testId);
	}

	public String getTestId(ReferencePersistenceIdType persistenceId) {
		return persistenceIdToTestIdMap.get(persistenceId);
	}

	public void mapIds(String testId, ReferencePersistenceIdType persistenceId) {
		testIdToPersistenceIdMap.put(testId, persistenceId);
		persistenceIdToTestIdMap.put(persistenceId, testId);
	}

}
