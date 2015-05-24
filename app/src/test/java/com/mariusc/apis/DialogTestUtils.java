package com.mariusc.apis;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import org.robolectric.Shadows;
import org.robolectric.util.SupportFragmentTestUtil;

/**
 * Created by Marius on 5/22/2015.
 */
public class DialogTestUtils
{
	public static void showDialog(DialogFragment dialogFragment, FragmentActivity activity)
	{
		/*FragmentTransaction fragmentTransaction=activity.getSupportFragmentManager().beginTransaction();
		dialogFragment.show(fragmentTransaction,"test");*/
		SupportFragmentTestUtil.startVisibleFragment(dialogFragment, activity.getClass(),R.id.main);
		Shadows.shadowOf(dialogFragment.getDialog()).callOnCreate(null);

	}
}
