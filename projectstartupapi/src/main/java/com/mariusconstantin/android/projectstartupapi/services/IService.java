package com.mariusconstantin.android.projectstartupapi.services;

/**
 * Created by Marius on 4/28/2015.
 */
public interface IService
{
	public void setCallback(IServicesCallback callback);

	public void unregisterCallback(IServicesCallback callback);

	public void sendError(Exception e);

	public void sendAnswer(ServicesAnswer answer);
}
