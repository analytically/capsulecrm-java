package uk.co.coen.capsulecrm.client;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class OpportunityTest extends CapsuleTest {
    @Test
    public void testSaveDelete() throws Exception {
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

        opportunity.delete().get();
        assertThat(COpportunity.listByParty(person).get()).hasSize(0);
    }
}
