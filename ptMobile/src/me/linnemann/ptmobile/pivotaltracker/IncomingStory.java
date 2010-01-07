package me.linnemann.ptmobile.pivotaltracker;

import java.util.HashMap;
import java.util.Map;

import me.linnemann.ptmobile.pivotaltracker.fields.StoryData;
import android.content.ContentValues;

public class IncomingStory implements IncomingData {

	private Map<StoryData,String> stringData;
	private DBAdapter db;	
	private String iteration_group;
	
	public IncomingStory(DBAdapter db, Integer project_id, String iteration_number, String iteration_group) {
		this.db = db;
		
		stringData = new HashMap<StoryData,String>(); 
		addDataForKey(StoryData.PROJECT_ID, project_id.toString());
		addDataForKey(StoryData.ITERATION_NUMBER, iteration_number);
		this.iteration_group = iteration_group;
	}
	
	public void addDataForKey(Object key, String value) {
		
		if (!stringData.containsKey(key)) {
			stringData.put((StoryData) key, value);
		} else {
			String tmp = stringData.get(key);
			stringData.put((StoryData) key, tmp+value);
		}
	}
	
	public Integer getStoryId() {
		return new Integer(stringData.get(StoryData.ID));
	}
	
	/**
	 * Get data in a db friendly way
	 * 
	 * @return
	 */
	private ContentValues getDataAsContentValues() {
		ContentValues v = new ContentValues();
		
		for (StoryData f : stringData.keySet()) {
			
			if (f.equals(StoryData.DEADLINE) || f.equals(StoryData.CREATED_AT) || f.equals(StoryData.ACCEPTED_AT)) {
				v.put(f.getDBFieldName(), stringData.get(f).replaceAll("/", "-").replaceAll(" UTC",""));
			} else {
				v.put(f.getDBFieldName(), stringData.get(f));
			}
		}
		
		v.put("updatetimestamp", Long.toString(System.currentTimeMillis()));	// TODO constant/enum?
		v.put("iteration_group", iteration_group);
	    return v;
	}

	/* (non-Javadoc)
	 * @see me.linnemann.ptmobile.pivotaltracker.IncomingData#save()
	 */
	public void save() {
		db.insertStory(getDataAsContentValues());
	}
}
