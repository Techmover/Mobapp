package com.jyotirmoy.officeparkingmanagement;

import java.io.Serializable;

import android.util.Log;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;
import com.ibm.mobile.services.data.IBMQuery;

@IBMDataObjectSpecialization("Item")
public class Parking extends IBMDataObject implements Serializable  {
	public static final String CLASS_NAME = "Item";
	private static final String NAME = "name";
	public String getName() {
		Log.e("Get value ",NAME);
		return (String) getObject(NAME);
	
	}
	public void setName(String itemName) {
		setObject(NAME, (itemName != null) ? itemName : "");
		
		Log.e("set value ",itemName);
	}
	
//	 IBMQuery<Parking> query = IBMQuery.queryForClass(Parking.CLASS_NAME);

	 
}
