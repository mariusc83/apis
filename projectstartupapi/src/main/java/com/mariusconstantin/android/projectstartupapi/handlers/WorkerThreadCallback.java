package com.mariusconstantin.android.projectstartupapi.handlers;

import android.os.Message;
import com.mariusconstantin.android.projectstartupapi.services.IServicesCallback;
import com.mariusconstantin.android.projectstartupapi.services.ServiceError;
import com.mariusconstantin.android.projectstartupapi.services.ServicesAnswer;
import java.util.WeakHashMap;

/**
 * Created by Marius on 4/28/2015.
 */
public class WorkerThreadCallback extends PauseHandler implements IServicesCallback
{

	private WeakHashMap<IServiceCallbackListener, Object> mListeners;
	public static final int ERROR_MESSAGE_ID  = 1;
	public static final int RESULT_MESSAGE_ID = 2;

	//	callbacks
	@Override
	public void onError(Exception e)
	{
		sendMessage(Message.obtain(null, ERROR_MESSAGE_ID, e));

	}

	@Override
	public void onServiceError(ServiceError serviceError)
	{
		sendMessage(Message.obtain(null, ERROR_MESSAGE_ID, serviceError));
	}

	@Override
	public void onResult(ServicesAnswer servicesAnswer)
	{
		sendMessage(Message.obtain(null, RESULT_MESSAGE_ID, servicesAnswer));

	}

	@Override
	public void addListener(IServiceCallbackListener listener)
	{
		if (mListeners == null)
		{
			mListeners = new WeakHashMap<>();
		}
		mListeners.put(listener, null);
	}

	@Override
	public void removeListener(IServiceCallbackListener listener)
	{
		if (mListeners != null)
		{
			mListeners.remove(listener);
		}
	}

	@Override
	protected boolean storeMessage(Message message)
	{
		// always return true in order to follow the Fragment/Activity life-cycle
		return true;
	}

	@Override
	protected void processMessage(Message message)
	{
		switch (message.what)
		{
			case ERROR_MESSAGE_ID:
				if (message.obj != null && message.obj instanceof Exception)
				{
					// search for any existing listener
					if (mListeners != null)
					{
						for (IServiceCallbackListener listener : mListeners.keySet())
						{
							listener.onErrorUI((Exception) message.obj);
						}
					}
				}
				break;
			case RESULT_MESSAGE_ID:
				if (message.obj != null && message.obj instanceof ServicesAnswer)
				{
					// search for any existing listener
					if (mListeners != null)
					{
						for (IServiceCallbackListener listener : mListeners.keySet())
						{
							listener.onResultUI((ServicesAnswer) message.obj);
						}
					}
				}
				break;
		}
	}
}
