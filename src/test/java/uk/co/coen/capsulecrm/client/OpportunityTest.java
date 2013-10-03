package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.running;

public class OpportunityTest extends CapsuleTest {
    @Test
    public void testSaveDelete() {
        running(testServer(8080), new Runnable() {
            public void run() {
                CPerson person = createTestPerson();

                CMilestones milestones = COpportunity.listMilestones().get();

                COpportunity opportunity = new COpportunity();
                opportunity.name = "name";
                opportunity.description = "description";
                opportunity.partyId = person.id;
                opportunity.currency = "GBP";
                opportunity.value = "200.00";
                opportunity.milestoneId = milestones.iterator().next().id;
                opportunity.save().get();
                assertThat(COpportunity.listByParty(person).get()).hasSize(1);

                opportunity.delete();
                assertThat(COpportunity.listByParty(person).get()).hasSize(0);

                deleteTestPerson();
                assertThat(CPerson.listByEmailAddress(person.firstEmail().emailAddress).get()).hasSize(0);
            }
        });
    }
}
