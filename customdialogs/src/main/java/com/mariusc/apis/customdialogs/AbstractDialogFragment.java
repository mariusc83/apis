package com.mariusc.apis.customdialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
/*
import android.support.v7.app.AlertDialog;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.security.InvalidParameterException;
import java.util.WeakHashMap;

public abstract class AbstractDialogFragment extends DialogFragment implements View.OnClickListener
{
	public interface InternalDialogBundleKeys
	{
		String TITLE_KEY                        = "title";
		String TITLE_STRING_KEY                 = "title_string";
		String MESSAGE_KEY                      = "message";
		String MESSAGE_STRING_KEY               = "message_string";
		String POSITIVE_BUTTON_LABEL_RES_ID_KEY = "button_label_p";
		String NEGATIVE_BUTTON_LABEL_RES_ID_KEY = "button_label_n";
	}

	protected WeakHashMap<IDialogActionCallback, Object> mCallbacks;
	private   LinearLayout                               mDialogFooter;
	protected Button                                     mDialogPositiveButton;
	protected Button                                     mDialogCancelButton;
	protected TextView                                   mDialogTitle;
	protected TextView                                   mDialogContent;

	// life cycle
	protected AbstractDialogFragment()
	{

	}

	@Override
	public void onActivityCreated(Bundle arg0)
	{
		super.onActivityCreated(arg0);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		final Bundle bundle = getArguments();
		final LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.dialog_info_layout, null);
		mDialogTitle = (TextView) view.findViewById(android.R.id.text1);
		mDialogContent = (TextView) view.findViewById(android.R.id.text2);
		mDialogCancelButton = (Button) view.findViewById(android.R.id.button2);
		mDialogPositiveButton = (Button) view.findViewById(android.R.id.button1);
		mDialogFooter = (LinearLayout) view.findViewById(R.id.dialog_footer);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		buildDialogBody();
		buildButtonsUI();
		builder.setView(view);
		return builder.create();
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		if (mCallbacks != null)
		{
			mCallbacks.clear();
			mCallbacks = null;
		}
	}

	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		mDialogFooter = null;
		mDialogPositiveButton.setOnClickListener(null);
		mDialogCancelButton.setOnClickListener(null);
		mDialogCancelButton = null;
		mDialogPositiveButton = null;
		mDialogTitle = null;
		mDialogContent = null;

	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}


	//	actions
	public void buildDialogBody()
	{
		final Bundle bundle = getArguments();
		if (bundle.containsKey(InternalDialogBundleKeys.TITLE_KEY))
		{
			mDialogTitle.setText(bundle.getInt(InternalDialogBundleKeys.TITLE_KEY));
		}
		else if (bundle.containsKey(InternalDialogBundleKeys.TITLE_STRING_KEY))
		{
			mDialogTitle.setText(bundle.getString(InternalDialogBundleKeys.TITLE_STRING_KEY, ""));
		}

		if (bundle.containsKey(InternalDialogBundleKeys.MESSAGE_KEY))
		{
			mDialogContent.setText(bundle.getInt(InternalDialogBundleKeys.MESSAGE_KEY));
		}
		else if (bundle.containsKey(InternalDialogBundleKeys.MESSAGE_STRING_KEY))
		{
			mDialogContent.setText(bundle.getString(InternalDialogBundleKeys.MESSAGE_STRING_KEY, ""));
		}
	}

	public void buildButtonsUI()
	{
		final Bundle bundle = getArguments();

		if (bundle.containsKey(InternalDialogBundleKeys.POSITIVE_BUTTON_LABEL_RES_ID_KEY) && bundle.containsKey
				(InternalDialogBundleKeys.NEGATIVE_BUTTON_LABEL_RES_ID_KEY))
		{
			// set the labels here
			mDialogCancelButton.setText(bundle.getInt(InternalDialogBundleKeys.NEGATIVE_BUTTON_LABEL_RES_ID_KEY));
			mDialogPositiveButton.setText(bundle.getInt(InternalDialogBundleKeys.POSITIVE_BUTTON_LABEL_RES_ID_KEY));
			mDialogCancelButton.setOnClickListener(this);
			mDialogPositiveButton.setOnClickListener(this);
		}
		else if (bundle.containsKey(InternalDialogBundleKeys.POSITIVE_BUTTON_LABEL_RES_ID_KEY))
		{
			mDialogPositiveButton.setText(bundle.getInt(InternalDialogBundleKeys.POSITIVE_BUTTON_LABEL_RES_ID_KEY));
			switchButtonsLayout(mDialogCancelButton, mDialogPositiveButton);
			mDialogPositiveButton.setOnClickListener(this);
		}
		else if (bundle.containsKey(InternalDialogBundleKeys.NEGATIVE_BUTTON_LABEL_RES_ID_KEY))
		{
			mDialogCancelButton.setText(bundle.getInt(InternalDialogBundleKeys.NEGATIVE_BUTTON_LABEL_RES_ID_KEY));
			switchButtonsLayout(mDialogPositiveButton, mDialogCancelButton);
			mDialogCancelButton.setOnClickListener(this);
		}
		else
		{
			throw new InvalidParameterException("You need to provide at least one label(button) for this Dialog to " +
					"work properly");
		}
	}

	private void switchButtonsLayout(Button toHide, Button toShow)
	{
		final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) toShow.getLayoutParams();
		layoutParams.weight = 1;
		final LinearLayout.LayoutParams toHideLayoutParams = (LinearLayout.LayoutParams) toHide.getLayoutParams();
		toHideLayoutParams.weight = 0;
		toHide.setVisibility(View.GONE);
	}


	public AbstractDialogFragment addCallback(IDialogActionCallback callback)
	{
		if (mCallbacks == null)
		{
			mCallbacks = new WeakHashMap<>();
		}
		mCallbacks.put(callback, null);
		return this;
	}


	// handlers
	@Override
	public void onClick(View v)
	{
		int buttonClicked = IDialogActionCallback.NO_BUTTON;
		switch (v.getId())
		{
			case android.R.id.button1:
				// positive button clicked
				buttonClicked = IDialogActionCallback.POSITIVE_BUTTON;
				if (mCallbacks != null)
				{
					for (IDialogActionCallback callback : mCallbacks.keySet())
					{
						callback.onButtonClicked(buttonClicked);
					}
				}
				break;
			case android.R.id.button2:
				// negative button clicked
				buttonClicked = IDialogActionCallback.NEGATIVE_BUTTON;
				if (mCallbacks != null)
				{
					for (IDialogActionCallback callback : mCallbacks.keySet())
					{
						callback.onButtonClicked(buttonClicked);
					}
				}
				dismiss();
				break;

		}

	}

	public interface IDialogActionCallback
	{
		int NO_BUTTON       = 0;
		int NEGATIVE_BUTTON = 1;
		int POSITIVE_BUTTON = 2;

		/**
		 * @param buttonType (NEGATIVE_BUTTON or POSITIVE_BUTTON in case a button was clicked. NO_BUTTON in a weird
		 *                   case in which no button could be clicked.)
		 */
		public void onButtonClicked(int buttonType);
	}

	public static abstract class DialogBuilder<D extends AbstractDialogFragment>
	{
		protected final Bundle                                     dialogBundle;
		protected       WeakHashMap<IDialogActionCallback, Object> mBuildermCallbacks;

		protected DialogBuilder()
		{
			dialogBundle = new Bundle();
		}

		public DialogBuilder<D> setTitle(String mTitle)
		{
			dialogBundle.putString(InternalDialogBundleKeys.TITLE_STRING_KEY, mTitle);
			return this;
		}

		public DialogBuilder<D> setTitle(int mTitleResourceId)
		{
			dialogBundle.putInt(InternalDialogBundleKeys.TITLE_KEY, mTitleResourceId);
			return this;
		}

		public DialogBuilder<D> setMessage(String mMessage)
		{
			dialogBundle.putString(InternalDialogBundleKeys.MESSAGE_STRING_KEY, mMessage);
			return this;
		}

		public DialogBuilder<D> setMessage(int mMessageResourceId)
		{
			dialogBundle.putInt(InternalDialogBundleKeys.MESSAGE_KEY, mMessageResourceId);
			return this;
		}

		public DialogBuilder<D> setNegativeButtonLabel(int mNegativeButtonLabelId)
		{
			dialogBundle.putInt(InternalDialogBundleKeys.NEGATIVE_BUTTON_LABEL_RES_ID_KEY, mNegativeButtonLabelId);
			return this;
		}

		public DialogBuilder<D> setPositiveButtonLabelId(int mPositiveButtonLabelId)
		{
			dialogBundle.putInt(InternalDialogBundleKeys.POSITIVE_BUTTON_LABEL_RES_ID_KEY, mPositiveButtonLabelId);
			return this;
		}

		public DialogBuilder<D> setCallbacks(WeakHashMap<IDialogActionCallback, Object> mCallbacks)
		{
			this.mBuildermCallbacks = mCallbacks;
			return this;
		}

		public DialogBuilder<D> addCallback(IDialogActionCallback callback)
		{
			if (mBuildermCallbacks != null)
			{
				mBuildermCallbacks = new WeakHashMap<>();
			}
			mBuildermCallbacks.put(callback, null);
			return this;
		}

		public abstract D build();
	}

	// getters
	public TextView getDialogTitle()
	{
		return mDialogTitle;
	}

	public TextView getDialogContent()
	{
		return mDialogContent;
	}

	public Button getDialogCancelButton()
	{
		return mDialogCancelButton;
	}

	public Button getDialogPositiveButton()
	{
		return mDialogPositiveButton;
	}
}
