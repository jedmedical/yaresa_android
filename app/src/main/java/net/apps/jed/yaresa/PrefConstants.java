package net.apps.jed.yaresa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

public class PrefConstants {
    
    public static int getAppPrefInt(Context context, String prefName){
    	int result = -1;
		if(context != null){
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			if(sharedPreferences!=null){
				result = sharedPreferences.getInt(
						prefName, -1);
			}
		}
		return result;
    }



	public static String  getAppPrefString(Context context, String prefName){
		String result = null;
		if(context != null){
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			if(sharedPreferences!=null){
				result = sharedPreferences.getString(
						prefName, null);
			}
		}
		return result;
	}

	public static void putAppPrefInt(Context context, String prefName, int value) {
		if(context!=null){
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor edit = sharedPreferences.edit();
			edit.putInt(prefName, value);
			if(Build.VERSION.SDK_INT>=9){
				edit.apply();
			}else{
				edit.commit();
			}
		}
	}

	public static void putAppPrefString(Context context, String prefName, String value) {
		if(context!=null){
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			Editor edit = sharedPreferences.edit();
			edit.putString(prefName, value);
			if(Build.VERSION.SDK_INT>=9){
				edit.apply();
			}else{
				edit.commit();
			}
		}
	}
}
