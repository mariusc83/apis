package com.mariusc.apis.customdialogs;

import android.support.v4.app.FragmentActivity;
import com.mariusc.apis.BuildConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

/**
 * Created by Marius on 5/22/2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig, emulateSdk = 21)
public class CustomAlertDialogTest
{
	CustomAlertDialog                mCustomAlertDialog;
	MockActivity                     mMockActivity;
	ActivityController<MockActivity> mMockActivityController;
	final String dialogTitle   = "Title";
	final String dialogMessage = "Dialog message";

	@Before
	public void setup()
	{

		mMockActivityController = Robolectric.buildActivity(MockActivity.class);
		mMockActivity = mMockActivityController.get();
		mMockActivityController.create().resume();
		mCustomAlertDialog = new CustomAlertDialog.CustomAlertDialogBuilder().setTitle(R.string.dialog_title_label)
																			 .setMessage(R.string
																					 .dialog_message_label)
																			 .setNegativeButtonLabel(R.string
																					 .dialog_no_button_label)
																			 .setPositiveButtonLabelId(R.string
																					 .dialog_ok_button_label).build();
	}

	@Test
	public void testDialogPersistentState()
	{
		DialogTestUtils.showDialog(mCustomAlertDialog,mMockActivity);
		final AbstractDialogFragment.IDialogActionCallback dialogActionCallback = new AbstractDialogFragment.IDialogActionCallback()
		{
			@Override
			public void onButtonClicked(int buttonType)
			{

			}
		};

	}

	public static class MockActivity extends FragmentActivity
	{

	}
}
