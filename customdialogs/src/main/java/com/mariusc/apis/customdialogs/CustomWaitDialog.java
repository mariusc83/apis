package com.mariusc.apis.customdialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by Marius on 5/24/2015.
 */
public class CustomWaitDialog extends DialogFragment
{
	private ProgressBar mProgressBar;

	public  CustomWaitDialog(){}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		final View dialogView=inflater.inflate(R.layout.wait_dialog_layout,container,false);
		return dialogView;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		mProgressBar=(ProgressBar)view.findViewById(android.R.id.progress);
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		mProgressBar=null;
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Dialog dialog= super.onCreateDialog(savedInstanceState);
		dialog.setCanceledOnTouchOutside(false);
		return dialog;
	}

	public ProgressBar getProgressBar()
	{
		return mProgressBar;
	}
}
