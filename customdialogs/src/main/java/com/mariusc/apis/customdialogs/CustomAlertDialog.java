package com.mariusc.apis.customdialogs;

/**
 * Created by Marius on 5/20/2015.
 */
public class CustomAlertDialog extends AbstractDialogFragment
{
	public CustomAlertDialog()
	{
		super();
	}


	public static class CustomAlertDialogBuilder extends DialogBuilder<CustomAlertDialog>
	{

		@Override
		public CustomAlertDialog build()
		{
			final CustomAlertDialog customAlertDialog = new CustomAlertDialog();
			customAlertDialog.setArguments(dialogBundle);
			customAlertDialog.mCallbacks = mBuildermCallbacks;
			return customAlertDialog;
		}
	}
}
