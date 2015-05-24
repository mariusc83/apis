package com.mariusc.apis;

import android.app.Application;
import android.support.v4.app.FragmentTransaction;
import com.mariusc.apis.customdialogs.CustomWaitDialog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by Marius on 5/24/2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class CustomWaitDialogTest
{
	private ActivityController<TestActivity> mActivityController;
	private TestActivity mTestActivity;
	private Application mApplication;
	private CustomWaitDialog mCustomWaitDialog;


	@Before
	public void setup(){
		mApplication= RuntimeEnvironment.application;
		mActivityController= Robolectric.buildActivity(TestActivity.class);
		mTestActivity=mActivityController.setup().get();
		mCustomWaitDialog=new CustomWaitDialog();
	}

	@Test
	public void testDialogShowDismiss(){
		final FragmentTransaction ft=mTestActivity.getSupportFragmentManager().beginTransaction();
		mCustomWaitDialog.show(ft,null);
		assertNotNull(mCustomWaitDialog.getProgressBar());
		assertTrue(mCustomWaitDialog.isVisible());
		Shadows.shadowOf(mTestActivity).getContentView().performClick();
		assertTrue(mCustomWaitDialog.isVisible());
		Shadows.shadowOf(mCustomWaitDialog.getDialog()).dismiss();
		assertTrue(!mCustomWaitDialog.isVisible());

	}
}
