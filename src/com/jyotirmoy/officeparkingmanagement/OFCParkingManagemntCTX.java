package com.jyotirmoy.officeparkingmanagement;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.IBMBluemix;

import android.app.Application;
import android.content.ClipData.Item;

public class OFCParkingManagemntCTX extends Application {
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		IBMBluemix.initialize(this, "06640d88-fdee-4c21-811d-5c1d6a438cd0", "30cb7191d3958619510b8cea1cf2af9bda7edfb2", "mobipark.mybluemix.net");
		 
		   IBMCloudCode cloudCodeService = IBMCloudCode.initializeService();

		Parking.registerSpecialization(Parking.class);
	}
	
	

}
