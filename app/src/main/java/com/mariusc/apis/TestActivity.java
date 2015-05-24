package com.mariusc.apis;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

/**
 * Created by Marius on 5/22/2015.
 */
public class TestActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		FrameLayout view = new FrameLayout(this);
		view.setId(R.id.main);
		setContentView(view);
	}
}
