package com.mariusc.apis.customdialogs;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Marius on 5/22/2015.
 */
public class DialogTestUtils
{
	public static void showDialog(DialogFragment dialogFragment, FragmentActivity activity)
	{

		dialogFragment.show(activity.getSupportFragmentManager(), null);
	}
}
