package com.mariusconstantin.android.projectstartupapi.services;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;

/**
 * Created by Marius on 4/28/2015.
 */
public abstract class AbstractService implements IService
{
	protected static final String THREAD_NOT_INITIALIZED_EXCEPTION_MESSAGE        = "The WorkerThread was not initialized";
	protected static final String OPERATION_CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE = "There is not callable " +
			"registered for this CODE";
	protected final HandlerThread                                mHandlerThread;
	protected       WorkerThreadHandler                          mWorkerThreadHandler;
	private         WeakHashMap<IServicesCallback, Object>       mCallbacks;
	protected       SparseArray<ServiceCallable<ServicesAnswer>> mFunctions; // here we will store all the

	// functions defined for this service
	protected AbstractService(Context context, String threadName)
	{
		mHandlerThread = new HandlerThread(threadName);
		try
		{

			mHandlerThread.start();
			if (mHandlerThread.getLooper() != null)
			{
				mWorkerThreadHandler = new WorkerThreadHandler(mHandlerThread.getLooper());
			}
		}
		catch (IllegalThreadStateException e)
		{
			e.printStackTrace();
		}

	}

	// actions
	public void performCall(int requestCode, Parcelable requestModel) throws IllegalArgumentException
	{
		if (mFunctions != null)
		{
			if (mFunctions.indexOfKey(requestCode) < 0)
			{
				throw new IllegalArgumentException(OPERATION_CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
			}

			if (mWorkerThreadHandler != null)
			{
				mWorkerThreadHandler.sendMessage(Message.obtain(null, requestCode, requestModel));
			}
		}
		else
		{
			throw new IllegalArgumentException(OPERATION_CODE_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
		}
	}

	/**
	 * Cancel all the pending or in progress actions
	 */
	public void cancel()
	{
		if (mWorkerThreadHandler != null)
		{
			mWorkerThreadHandler.removeCallbacksAndMessages(null);
		}
	}

	/**
	 * Quits the service HandlerThread
	 */
	public void quit()
	{
		if (mHandlerThread != null)
		{
			mHandlerThread.quit();
		}
	}

	@Override
	public void setCallback(IServicesCallback callback)
	{
		if (mCallbacks == null)
		{
			mCallbacks = new WeakHashMap<>();
		}
		mCallbacks.put(callback, null);
	}

	@Override
	public void unregisterCallback(IServicesCallback callback)
	{
		if (mCallbacks != null)
		{
			mCallbacks.remove(callback);
		}
	}

	@Override
	public void sendError(Exception e)
	{
		if (mCallbacks != null)
		{
			for (IServicesCallback callback : mCallbacks.keySet())
			{
				if (callback != null)
				{
					callback.onError(e);
				}
			}
		}
	}

	@Override
	public void sendAnswer(ServicesAnswer answer)
	{
		if (mCallbacks != null)
		{
			for (IServicesCallback callback : mCallbacks.keySet())
			{
				if (callback != null)
				{
					callback.onResult(answer);
				}
			}
		}
	}

	protected void executeOperation(final Message message)
	{
		if (mFunctions != null)
		{
			final ServiceCallable<ServicesAnswer> callable = mFunctions.get(message.what);
			callable.setRequestModel((Parcelable) message.obj);
			if (callable != null)
			{
				try
				{
					sendAnswer(callable.call());
				}
				catch (Exception e)
				{
					sendError(e);
				}
			}
		}
	}

	public final void initFunctions(List<FunctionRequestCodePair> functions)
	{
		if (functions != null)
		{
			for (FunctionRequestCodePair functionRequestCodePair : functions)
			{
				addFunction(functionRequestCodePair.function, functionRequestCodePair.requestCode);
			}
		}
	}

	protected void addFunction(ServiceCallable<ServicesAnswer> callable, int functionId)
	{
		if (mFunctions == null)
		{
			mFunctions = new SparseArray<>();
		}
		mFunctions.append(functionId, callable);
	}

	//	handlers
	protected final class WorkerThreadHandler extends Handler
	{
		protected WorkerThreadHandler(Looper looper)
		{
			super(looper);
		}

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			executeOperation(msg);
		}
	}

	// callable custom interface
	public static class ServiceCallable<D> implements Callable<D>
	{
		protected Parcelable mRequestModel;

		public ServiceCallable<D> setRequestModel(Parcelable model)
		{
			mRequestModel = model;
			return this;
		}

		@Override
		public D call() throws Exception
		{
			return null;
		}
	}

	public static class FunctionRequestCodePair
	{
		public FunctionRequestCodePair(int requestCode, ServiceCallable function)
		{
			this.requestCode = requestCode;
			this.function = function;
		}

		public int             requestCode;
		public ServiceCallable function;
	}

}
