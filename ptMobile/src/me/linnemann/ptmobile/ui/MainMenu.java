package me.linnemann.ptmobile.ui;

import me.linnemann.ptmobile.About;
import me.linnemann.ptmobile.Preferences;
import me.linnemann.ptmobile.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu {

	public static final boolean SHOW_MENU = true;
	public static final boolean FINISH_PROCESSING = true;
	public static final int DEFAULT_MENU_GROUP = Menu.NONE;
	public static final int DEFAULT_MENU_ORDER = Menu.NONE;
	
	private static final int MENU_REFRESH = 10000123;
	private static final int MENU_PREFERENCES = 10000124;
	private static final int MENU_ABOUT = 10000125;
	private static final int MENU_ADD = 10000126;

	private RefreshableListActivityWithMainMenu activity;
	private boolean showAddMenu;
	
	public MainMenu(RefreshableListActivityWithMainMenu activity, boolean showAddMenu) {
		this.activity = activity;
		this.showAddMenu = showAddMenu;
	}

	public void addMenuItemsToMenu(Menu menu) {
		if (showAddMenu)
			addMenuItemWithIcon(menu, MENU_ADD, R.string.menu_add_story, android.R.drawable.ic_menu_add);
		
		addMenuItemWithIcon(menu, MENU_REFRESH, R.string.menu_refesh, R.drawable.ic_menu_refresh);
		addMenuItemWithIcon(menu, MENU_PREFERENCES, R.string.menu_prefs, android.R.drawable.ic_menu_preferences);
		addMenuItemWithIcon(menu, MENU_ABOUT, R.string.menu_about, android.R.drawable.ic_menu_help);
	}

	private void addMenuItemWithIcon(Menu parentMenu, int menuId, int name, int icon) {
		MenuItem menu = parentMenu.add(	DEFAULT_MENU_GROUP,
									   	menuId,
									   	DEFAULT_MENU_ORDER,
										name);
		menu.setIcon(icon);
	}
	
	public void performMenuAction(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ADD:
				executeAddStory();
				break;
			case MENU_REFRESH:
				executeRefresh();
				break;
			case MENU_PREFERENCES:
				showPreferences();
				break;
			case MENU_ABOUT:
				showAbout();
				break;
			default:
				throw new RuntimeException("trying to perform action on nonexisting menu");
		}
	}

	private void executeRefresh() {
		activity.refresh();
	}
	
	private void executeAddStory() {
		activity.addStory();
	}

	private void showPreferences() {
		showActivityFromClass(Preferences.class);
	}

	private void showAbout() {
		showActivityFromClass(About.class);
	}

	private void showActivityFromClass(Class<? extends Activity> activityToStart) {
		activity.startActivity(new Intent(activity, activityToStart));
	}
}
