package com.mariusconstantin.android.projectstartupapi.services;

/**
 * Created by Marius on 4/28/2015.
 */
public class ServicesAnswer<D>
{
	private int     mMethod;
	private boolean mSuccess;
	private D       mObject;
	private Object  mError;

	public ServicesAnswer(int mMethod, boolean success, D mObject)
	{
		this.mMethod = mMethod;
		this.mSuccess = success;
		this.mObject = mObject;
	}

	public ServicesAnswer(int mMethod, boolean mSuccess, D mObject, Object mError)
	{
		this.mMethod = mMethod;
		this.mSuccess = mSuccess;
		this.mObject = mObject;
		this.mError = mError;
	}

	public ServicesAnswer()
	{
	}

	public ServicesAnswer setMethod(int mMethod)
	{
		this.mMethod = mMethod;
		return this;
	}

	public D getObject()
	{
		return mObject;
	}


	public ServicesAnswer setObject(D mObject)
	{
		this.mObject = mObject;
		return this;
	}

	public ServicesAnswer setSuccess(boolean mSuccess)
	{
		this.mSuccess = mSuccess;
		return this;
	}

	public int getMethod()
	{
		return mMethod;
	}

	public Object getError()
	{
		return mError;
	}

	public ServicesAnswer setError(Object mError)
	{
		this.mError = mError;
		return this;
	}

	public boolean isSuccess()
	{
		return mSuccess;
	}
}
