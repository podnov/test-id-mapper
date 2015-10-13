package com.evanzeimet.testidmapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AbstractTestIdMapperTest {

	private static final String FACEBOOK = "Facebook";
	private static final String GOOGLE = "Google";

	private PersonToOrganizationIdMapper idMapper;

	@Before
	public void setUp() {
		idMapper = new PersonToOrganizationIdMapper();
	}

	@Test
	public void test() {
		List<OrganizationEntity> organizationEntities = new ArrayList<>();
		OrganizationEntity googleEntity;
		OrganizationEntity facebookEntity;

		{
			googleEntity = new OrganizationEntity();
			googleEntity.setId(24L);

			organizationEntities.add(googleEntity);
		}

		{
			facebookEntity = new OrganizationEntity();
			facebookEntity.setId(42L);
			organizationEntities.add(facebookEntity);
		}

		List<TestOrganization> testOrganizations = new ArrayList<>();
		TestOrganization testOrganization;

		{
			testOrganization = new TestOrganization();
			testOrganization.setTestId(GOOGLE);

			testOrganizations.add(testOrganization);
		}

		{
			testOrganization = new TestOrganization();
			testOrganization.setTestId(FACEBOOK);

			testOrganizations.add(testOrganization);
		}

		idMapper.mapReferenceIds(organizationEntities, testOrganizations);

		List<Person> people = new ArrayList<>();
		DefaultPerson evan;
		DefaultPerson skye;

		{
			evan = new DefaultPerson();
			evan.setOrganizationTestId(GOOGLE);

			people.add(evan);
		}

		{
			skye = new DefaultPerson();
			skye.setOrganizationTestId(FACEBOOK);

			people.add(skye);
		}

		idMapper.setReferrerReferencePersistenceIdsForTestIds(people);

		Long actualOrganizationId = evan.getOrganizationId();
		Long expectedOrganizationId = googleEntity.getId();

		assertEquals(expectedOrganizationId, actualOrganizationId);

		actualOrganizationId = skye.getOrganizationId();
		expectedOrganizationId = facebookEntity.getId();

		assertEquals(expectedOrganizationId, actualOrganizationId);
	}
}
