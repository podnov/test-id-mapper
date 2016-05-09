package com.evanzeimet.testidmapper;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public class AbstractTestIdMapperTest {

	private static final Type ORGANIZATION_ENTITIES_LIST_TYPE = new TypeReference<List<OrganizationEntity>>() {
	}.getType();

	private static final Type PEOPLE_LIST_TYPE = new TypeReference<List<DefaultPerson>>() {
	}.getType();

	private static final Type TEST_ORGANIZATION_LIST_TYPE = new TypeReference<List<OrganizationEntity>>() {
	}.getType();

	private PersonToOrganizationIdMapper idMapper;

	private TestUtils testUtils;

	@Before
	public void setUp() {
		idMapper = new PersonToOrganizationIdMapper();

		testUtils = new TestUtils();
	}

	@Test
	public void setReferrerReferencePersistenceIdsForTestIds() throws JsonParseException,
			JsonMappingException,
			IOException {
		List<OrganizationEntity> givenOrganizationEntities = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferencePersistenceIdsForTestIds/givenOrganizationEntities.json",
				ORGANIZATION_ENTITIES_LIST_TYPE);
		List<OrganizationEntity> givenTestOrganizations = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferencePersistenceIdsForTestIds/givenTestOrganizations.json",
				TEST_ORGANIZATION_LIST_TYPE);

		idMapper.mapReferenceActualPersistenceIdsToGivenTestIds(givenOrganizationEntities, givenTestOrganizations);

		List<Person> people = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferencePersistenceIdsForTestIds/givenPeople.json",
				PEOPLE_LIST_TYPE);

		idMapper.setReferrerReferencePersistenceIdsForTestIds(people);

		String actualPeopleJson = testUtils.stringify(people);
		String expectedPeopleJson = readRelativeResource("AbstractTestIdMapperTestResources/setReferrerReferencePersistenceIdsForTestIds/expectedPeople.json");

		assertEquals(expectedPeopleJson, actualPeopleJson);
	}

	@Test
	public void setReferrerReferenceTestIdsForPersistenceIds() throws JsonParseException,
			JsonMappingException,
			IOException {
		List<OrganizationEntity> givenOrganizationEntities = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferenceTestIdsForPersistenceIds/givenOrganizationEntities.json",
				ORGANIZATION_ENTITIES_LIST_TYPE);
		List<OrganizationEntity> givenTestOrganizations = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferenceTestIdsForPersistenceIds/givenTestOrganizations.json",
				TEST_ORGANIZATION_LIST_TYPE);

		idMapper.mapReferenceActualPersistenceIdsToGivenTestIds(givenOrganizationEntities,
				givenTestOrganizations);

		List<Person> people = objectifyRelativeJsonResource("AbstractTestIdMapperTestResources/setReferrerReferenceTestIdsForPersistenceIds/givenPeople.json",
				PEOPLE_LIST_TYPE);

		idMapper.setReferrerReferenceTestIdsForPersistenceIds(people);

		String actualPeopleJson = testUtils.stringify(people);
		String expectedPeopleJson = readRelativeResource("AbstractTestIdMapperTestResources/setReferrerReferenceTestIdsForPersistenceIds/expectedPeople.json");

		assertEquals(expectedPeopleJson, actualPeopleJson);
	}

	protected <T> T objectifyRelativeJsonResource(String relativePath, Type typeOfT) throws JsonParseException,
			JsonMappingException,
			IOException {
		String givenOrganizationEntitiesJson = readRelativeResource(relativePath);
		return testUtils.objectify(givenOrganizationEntitiesJson,
				typeOfT);
	}

	protected String readRelativeResource(String relativePath) {
		return testUtils.readRelativeResource(getClass(),
				relativePath);
	}
}
