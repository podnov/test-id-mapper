# test-id-mapper

This utility helps you map static test-time ids to auto-generated persistence ids. Consider this Cucumber feature:

```
Feature: Test people REST retrieval

    Background:
        Given these organizations:
        | id                      | name     |
        | [auto-generated db key] | Google   |
        | [auto-generated db key] | Facebook |

        And these people:
        | name |
        | Jill |
        | Bill |

        And Jill works for Google
        And Bill works for Facebook

    Scenario: Retrieve people
        When I retrieve all people with their assigned organization id
        Then I should see these people:
        | name | organizationId |
        | Jill | ???            |
        | Bill | ???            |
```

You could make an assumption about the system, and assume that the keys are 1-based every time and expect this:

```
Feature: Test people REST retrieval

...

    Scenario: Retrieve people
        When I retrieve all people with their assigned organization id
        Then I should see these people:
        | name | organizationId |
        | Jill | 1              |
        | Bill | 2              |
```

Now consider adding more data with more relationships. Instead of 2 organizations and 2 people, you've got 20 organizations and 100 people. Add job positions and associate them with people. Associate people with the person record of their supervisor. You quickly end up with a lot of ambiguous, repeated integers everywhere.

```
Feature: Test people REST retrieval

    Background:
        Given these organizations:
        | name     |
        | Google   |
        | Facebook |
        ...

        And these people:
        | name |
        | Jill |
        | Bill |
        | John |
        | Jess |

        And these positions:
        | name       |
        | Engineer   |
        | Supervisor |
        | CEO        |
        ...

        And "Jill" works as an "Engineer" for "Google" under "John"
        And "Bill" works as an "Engineer" for "Facebook"
        And "John" works as a "Supervisor" for "Google" under "Jess"
        And "Jess" works as a "CEO" for "Google"
        ...

    Scenario: Retrieve people
        When I retrieve all people with their assigned organization id
        Then I should see these people:
        | name | organizationId | positionId | supervisorId |
        | Jill | 1              | 1          | 3            |
        | Bill | 2              | 1          |              |
        | John | 1              | 2          | 4            |
        | Jess | 1              | 3          |              |
        ...
```

Those integers are hard to follow around your feature files.

Using test-id-mapper, you can represent these associations like so:

```
Feature: Test people REST retrieval

    Background:
        Given these organizations:
        | name     |
        | Google   |
        | Facebook |
        ...

        And these people:
        | name |
        | Jill |
        | Bill |
        | John |
        | Jess |
        ...

        And these positions:
        | name       |
        | Engineer   |
        | Supervisor |
        | CEO        |
        ...

        And "Jill" works as an "Engineer" for "Google" under "John"
        And "Bill" works as an "Engineer" for "Facebook"
        And "John" works as a "Supervisor" for "Google" under "Jess"
        And "Jess" works as a "CEO" for "Google"
        ...

    Scenario: Retrieve people
        When I retrieve all people with their assigned organization id
        Then I should see these people:
        | name | testOrganizationId | testPositionId | testSupervisorId |
        | Jill | Google             | Engineer       | John             |
        | Bill | Facebook           | Engineer       |                  |
        | John | Google             | Supervisor     | Jess             |
        | Jess | Google             | CEO            |                  |
        ...
```

The work is managed by a [DefaultTestIdMapper](src/main/java/com/evanzeimet/testidmapper/DefaultTestIdMapper.java). The data bits used by the mapper are exposed by a [IdProducer](src/main/java/com/evanzeimet/testidmapper/IdProducer.java).

Using the organization to person mapping scenario, the organization is considered the "Reference," and the person is considered the "Referrer".  The person record refers to the organization by the organization id stored on the person record.

An example exists in the tests. [AbstractTestIdMapperTest](src/test/java/com/evanzeimet/testidmapper/AbstractTestIdMapperTest.java) invokes the [PersonToOrganizationIdMapper](src/test/java/com/evanzeimet/testidmapper/PersonToOrganizationIdMapper.java) which extends [DefaultTestIdMapper](src/main/java/com/evanzeimet/testidmapper/DefaultTestIdMapper.java) for the sake of removing the generics markup. The [PersonToOrganizationIdMapper](src/test/java/com/evanzeimet/testidmapper/PersonToOrganizationIdMapper.java) then feeds all records through the [PersonToOrganizationIdProducer](src/test/java/com/evanzeimet/testidmapper/PersonToOrganizationIdProducer.java).

