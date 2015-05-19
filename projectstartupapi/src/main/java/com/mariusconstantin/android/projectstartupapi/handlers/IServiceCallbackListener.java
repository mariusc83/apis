package com.mariusconstantin.android.projectstartupapi.handlers;

import com.mariusconstantin.android.projectstartupapi.services.ServicesAnswer;

/**
 * Created by Marius on 5/7/2015.
 */
public interface IServiceCallbackListener
{
	/**
	 * Triggered whenever an Exception is thrown from WorkerThread. This method is executed on the UIThread and it
	 * follows the Activity/Fragment life-cycle.
	 *
	 * @param e
	 */
	public void onErrorUI(Exception e);

	/**
	 * Triggered whenever an answer is returned from the WorkerThread. This method is executed on the UIThread and it
	 * follows the Activity/Fragment life-cycle.
	 *
	 * @param servicesAnswer
	 */
	public void onResultUI(ServicesAnswer servicesAnswer);
}
