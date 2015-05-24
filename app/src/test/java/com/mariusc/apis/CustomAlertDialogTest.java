package com.mariusc.apis;

import android.app.Application;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.mariusc.apis.customdialogs.AbstractDialogFragment;
import com.mariusc.apis.customdialogs.CustomAlertDialog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Marius on 5/22/2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class CustomAlertDialogTest
{
	CustomAlertDialog                mCustomAlertDialog;
	MockActivity                     mMockActivity;
	Application                      mApplication;
	ActivityController<MockActivity> mMockActivityController;
	final String dialogTitle   = "Title";
	final String dialogMessage = "Dialog message";
	String mDialogTitleFromResources;
	String mDialogMessageFromResources;
	private boolean positiveButtonPressed;
	private boolean negativeButtonPressed;


	@Before
	public void setup()
	{

		mApplication= RuntimeEnvironment.application;
		mMockActivityController = Robolectric.buildActivity(MockActivity.class);
		mMockActivity = mMockActivityController.setup().get();
		mCustomAlertDialog = new CustomAlertDialog.CustomAlertDialogBuilder().setTitle(R.string.dialog_title_label)
																			 .setMessage(R.string
																					 .dialog_message_label)
																			 .setNegativeButtonLabel(R.string
																					 .dialog_no_button_label)
																			 .setPositiveButtonLabelId(R.string
																					 .dialog_ok_button_label).build();
		mDialogMessageFromResources=mApplication.getResources().getString(R.string.dialog_message_label);
		mDialogTitleFromResources=mApplication.getResources().getString(R.string.dialog_title_label);

	}

	@Test
	public void testDialogPersistentState()
	{
		final FragmentTransaction fragmentTransaction = mMockActivity.getSupportFragmentManager().beginTransaction();
		mCustomAlertDialog.show(fragmentTransaction, "test");
		final AbstractDialogFragment.IDialogActionCallback dialogActionCallback = new AbstractDialogFragment.IDialogActionCallback()
		{

			@Override
			public void onButtonClicked(int buttonType)
			{

				switch (buttonType)
				{
					case POSITIVE_BUTTON:
						positiveButtonPressed = true;
						break;
					case NEGATIVE_BUTTON:
						negativeButtonPressed = true;
						break;
				}

			}

		};

		mCustomAlertDialog.addCallback(dialogActionCallback);

		mCustomAlertDialog.getDialogPositiveButton().performClick();
		assertEquals(mDialogTitleFromResources, mCustomAlertDialog.getDialogTitle().getText().toString());
		assertEquals(mDialogMessageFromResources, mCustomAlertDialog.getDialogContent().getText().toString());
		assertTrue(positiveButtonPressed);
		assertEquals(18, (int) mCustomAlertDialog.getDialogTitle().getTextSize());
		assertEquals(16, (int) mCustomAlertDialog.getDialogContent().getTextSize());
		mMockActivityController.restart();
		assertEquals(mDialogTitleFromResources, mCustomAlertDialog.getDialogTitle().getText().toString());
		assertEquals(mDialogMessageFromResources, mCustomAlertDialog.getDialogContent().getText().toString());
		mCustomAlertDialog.addCallback(dialogActionCallback);
		mCustomAlertDialog.getDialogCancelButton().performClick();
		assertTrue(negativeButtonPressed);
		assertTrue(!mCustomAlertDialog.isVisible());

	}

	public static class MockActivity extends AppCompatActivity
	{
		@Override
		public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
		{
			FrameLayout view = new FrameLayout(this);
			view.setId(R.id.main);
			setContentView(view);
		}
	}
}
