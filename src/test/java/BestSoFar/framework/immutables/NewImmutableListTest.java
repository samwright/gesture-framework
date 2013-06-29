package BestSoFar.framework.immutables;

import BestSoFar.framework.immutables.common.NewImmutableReplacement;
import BestSoFar.framework.immutables.common.NewMutationHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

/**
 * User: Sam Wright Date: 29/06/2013 Time: 10:30
 */
public class NewImmutableListTest {
    class TestHandler implements NewMutationHandler {
        private NewImmutableList<String> handledList;
        private boolean willAssignReplacement = true;
        private boolean willForgetReplacement = false;

        public TestHandler() {
            handledList = new NewImmutableList<>(this);
        }

        public NewImmutableList<String> getHandledList() {
            return handledList;
        }

        @Override
        public void handleReplacement(NewImmutableReplacement existingObject,
                                      NewImmutableReplacement proposedReplacement) {
            hasBeenNotified = true;
            if (willAssignReplacement)
                handledList = (NewImmutableList<String>) existingObject.assignReplacementTo(this);
            if (willForgetReplacement)
                handledList.discardReplacement();

        }
    }

    private boolean hasBeenNotified = false;
    private NewImmutableList<String> list;
    private TestHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new TestHandler();
        list = handler.getHandledList();
    }

    @Test
    public void testMutability() throws Exception {
        list.add("Hello");
        assertTrue(list.isEmpty());
        assertEquals(Arrays.asList("Hello"), handler.getHandledList());
    }

    @Test
    public void testWriteLock() throws Exception {
        list.startMutation();
        list.endMutation();
        assertTrue(hasBeenNotified);
    }

    @Test
    public void testNotifyMutationHandler() throws Exception {
        assertFalse(hasBeenNotified);
        list.size(); // read-only operations don't notify handler
        assertFalse(hasBeenNotified);
        list.add("hello"); // mutation operations will notify handler
        assertTrue(hasBeenNotified);
    }

    @Test
    public void testUnmodifiedHandling() throws Exception {
        list.addAll(Arrays.asList("hello", "goodbye"));
        list = handler.getHandledList();

        NewImmutableList<String> replacementList = list.assignReplacementTo(handler);

        assertEquals(replacementList, list);
        assertTrue(list.getReplacement() == replacementList);
        assertTrue(list.hasReplacement());
        assertFalse(replacementList.isMutated());
    }

    @Test
    public void testForgetReplacement() throws Exception {
        testUnmodifiedHandling();

        list.discardReplacement();

        assertFalse(list.hasReplacement());
        assertNull(list.getReplacement());

        // I can mutate again without getting exception
        list.add("boo");
    }

    @Test(expected = NewImmutableReplacement.AlreadyMutatedException.class)
    public void testMutationAfterReplacement() throws Exception {
        testUnmodifiedHandling();
        list.add("should fail");
    }

    @Test
    public void testModifiedHandling() throws Exception {
        list.addAll(Arrays.asList("hello", "goodbye"));
        list = handler.getHandledList();
        list.add("third");
        NewImmutableList<String> replacementList = handler.getHandledList();

        assertEquals(Arrays.asList("hello", "goodbye"), list);
        assertEquals(Arrays.asList("hello", "goodbye", "third"), replacementList);

        assertTrue(list.getReplacement() == replacementList);
        assertTrue(list.hasReplacement());
        assertTrue(replacementList.isMutated());
    }

    @Test
    public void testForgetModifiedReplacement() throws Exception {
        testModifiedHandling();

        list.discardReplacement();

        assertFalse(list.hasReplacement());
        assertNull(list.getReplacement());

        // I can mutate again without getting exception
        list.add("boo");
    }

    @Test(expected = NewImmutableReplacement.AlreadyMutatedException.class)
    public void testSecondMutation() throws Exception {
        testModifiedHandling();
        list.add("should fail");
    }
}
