package com.mariusconstantin.android.projectstartupapi.services;

import com.mariusconstantin.android.projectstartupapi.handlers.IServiceCallbackListener;

/**
 * Created by Marius on 4/28/2015.
 */
public interface IServicesCallback
{
	/**
	 * This will not be called on the UI thread
	 * @param e
	 */
	public void onError(Exception e);

	/**
	 * This will not be called on the UI thread
	 * @param serviceError
	 */
	public void onServiceError(ServiceError serviceError);

	/**
	 * This will not be called on the UI thread
	 * @param servicesAnswer
	 */
	public void onResult(ServicesAnswer servicesAnswer);

	/**
	 * Register a listener on this callback
	 * @param listener
	 */
	public void addListener(IServiceCallbackListener listener);

	/**
	 * Remove a listener from this callback
	 * @param listener
	 */
	public void removeListener(IServiceCallbackListener listener);
}
