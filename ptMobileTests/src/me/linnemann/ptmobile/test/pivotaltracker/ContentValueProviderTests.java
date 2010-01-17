package me.linnemann.ptmobile.test.pivotaltracker;

import me.linnemann.ptmobile.pivotaltracker.ContentValueProvider;
import me.linnemann.ptmobile.pivotaltracker.Story;
import me.linnemann.ptmobile.pivotaltracker.StoryImpl;
import me.linnemann.ptmobile.pivotaltracker.fields.StoryData;
import me.linnemann.ptmobile.pivotaltracker.value.StoryType;
import android.content.ContentValues;
import android.test.AndroidTestCase;

public class ContentValueProviderTests extends AndroidTestCase {

	
	private ContentValueProvider provider;
	private Story story;
	
	public void setUp() {
		story = new StoryImpl(StoryType.FEATURE);
		story.changeId(TestData.ANY_ID);
		story.resetModifiedFieldsTracking();
		provider = new ContentValueProvider(story);
	}
	
	public void test_storyContentValues_provideTimestamp() {
		story.changeEstimate(TestData.ANY_ESTIMATE);
		provider.fill();
		ContentValues values = provider.getValues();
		assertTrue(values.containsKey("updatetimestamp"));
	}
	
	public void test_storyContentValues_provideIDevenWhenNotChanged() {
		story.changeDescription(TestData.ANY_DESCRIPTION);
		provider.fill();
		ContentValues values = provider.getValues();
		assertEquals(TestData.ANY_ID.toString(), values.get("id"));
	}
	
	
	public void test_storyWithMultipleChanges_providesMultipleValues() {
		story.changeId(TestData.ANY_ID);
		story.changeEstimate(TestData.ANY_ESTIMATE);
		provider = new ContentValueProvider(story);
		provider.fill();
		ContentValues values = provider.getValues();
		assertEquals(3, values.size()); // expecting 2 values plus updatetimestamp
	}

	public void test_storyWithMultipleChanges_providesCorrectValues() {
		story.changeId(TestData.ANY_ID);
		story.changeEstimate(TestData.ANY_ESTIMATE);
		story.changeDescription(TestData.ANY_DESCRIPTION);
		provider.fill();
		
		checkContentValue(StoryData.ID, TestData.ANY_ID.toString());
		checkContentValue(StoryData.ESTIMATE, TestData.ANY_ESTIMATE.getValueAsString().toString());
		checkContentValue(StoryData.DESCRIPTION, TestData.ANY_DESCRIPTION);
	}
	
	public void test_resettedModifiedFields_provideNoData() {
		story.changeAcceptedAt(TestData.ANY_ACCEPTED_AT);
		story.changeDescription(TestData.ANY_DESCRIPTION);
		story.changeName(TestData.ANY_NAME);
		story.resetModifiedFieldsTracking();
		provider = new ContentValueProvider(story);
		provider.fill();
		ContentValues values = provider.getValues();
		assertEquals(0, values.size());
	}

	private void fillAndCheckContentValue(StoryData storyData, String expectedValue) {
		provider.fill();
		checkContentValue(storyData, expectedValue);
	}
	
	private void checkContentValue(StoryData storyData, String expectedValue) {
		String dataFromValues = (String) provider.getValues().get(storyData.getDBFieldName());
		assertEquals(expectedValue, dataFromValues);
	}

	public void test_storyIterationNumber_providedWhenChanged() {
		story.changeIterationNumber(TestData.ANY_ITERATION_NUMBER);
		fillAndCheckContentValue(StoryData.ITERATION_NUMBER, TestData.ANY_ITERATION_NUMBER.toString());	
	}

	public void test_storyProjectId_providedWhenChanged() {
		story.changeProjectId(TestData.ANY_PROJECT_ID);
		fillAndCheckContentValue(StoryData.PROJECT_ID, TestData.ANY_PROJECT_ID.toString());	
	}

	public void test_storyEstimate_providedWhenChanged() {
		story.changeEstimate(TestData.ANY_ESTIMATE);
		fillAndCheckContentValue(StoryData.ESTIMATE, TestData.ANY_ESTIMATE.getValueAsString());	
	}
	
	public void test_storyType_providedWhenChanged() {
		story.changeStoryType(TestData.ANY_STORYTYPE);
		fillAndCheckContentValue(StoryData.STORY_TYPE, TestData.ANY_STORYTYPE.getValueAsString());	
	}

	public void test_storyLabels_providedWhenChanged() {
		story.changeLabels(TestData.ANY_LABELS);
		fillAndCheckContentValue(StoryData.LABELS, TestData.ANY_LABELS);	
	}

	public void test_storyCurrentState_providedWhenChanged() {
		story.changeCurrentState(TestData.ANY_STATE);
		fillAndCheckContentValue(StoryData.CURRENT_STATE, TestData.ANY_STATE.getValueAsString());	
	}
	
	public void test_storyDescription_providedWhenChanged() {
		story.changeDescription(TestData.ANY_DESCRIPTION);
		fillAndCheckContentValue(StoryData.DESCRIPTION, TestData.ANY_DESCRIPTION);	
	}

	public void test_storyDeadline_providedWhenChanged() {
		story.changeDeadline(TestData.ANY_DEADLINE);
		fillAndCheckContentValue(StoryData.DEADLINE,TestData.ANY_DEADLINE);
	}

	public void test_storyRequestedBy_providedWhenChanged() {
		story.changeRequestedBy(TestData.ANY_REQUESTED_BY);
		fillAndCheckContentValue(StoryData.REQUESTED_BY, TestData.ANY_REQUESTED_BY);
	}

	public void test_storyOwnedBy_providedWhenChanged() {
		story.changeOwnedBy(TestData.ANY_OWNED_BY);
		fillAndCheckContentValue(StoryData.OWNED_BY, TestData.ANY_OWNED_BY);
	}

	public void test_storyCreatedAt_providedWhenChanged() {
		story.changeCreatedAt(TestData.ANY_CREATED_AT);
		fillAndCheckContentValue(StoryData.CREATED_AT, TestData.ANY_CREATED_AT);
	}
	
	public void test_storyAcceptedAt_providedWhenChanged() {
		story.changeAcceptedAt(TestData.ANY_ACCEPTED_AT);
		fillAndCheckContentValue(StoryData.ACCEPTED_AT, TestData.ANY_ACCEPTED_AT);
	}

	public void test_storyIterationGroup_providedWhenChanged() {
		story.changeIterationGroup(TestData.ANY_ITERATIONGROUP);
		fillAndCheckContentValue(StoryData.ITERATION_GROUP, TestData.ANY_ITERATIONGROUP);
	}

	public void test_storyName_providedWhenChanged() {
		story.changeName(TestData.ANY_NAME);
		fillAndCheckContentValue(StoryData.NAME, TestData.ANY_NAME);
	}
}