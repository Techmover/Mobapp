package com.jyotirmoy.officeparkingmanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import android.R.string;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.cloudcode.IBMCloudCode;
import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.data.IBMDataException;
import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMQuery;
import com.ibm.mobile.services.data.file.IBMFileSync;
import com.ibm.mobile.services.data.internal.IBMBaaSImpl;

import com.jyotirmoy.*;



	
public class MainActivity extends ActionBarActivity {
	/*
	MainActivity context ;
	private static final String APP_ID = "06640d88-fdee-4c21-811d-5c1d6a438cd0";
	private static final String APP_SECRET = "30cb7191d3958619510b8cea1cf2af9bda7edfb2";
	private static final String APP_ROUTE = "mobipark.mybluemix.net ";
	private static final String PROPS_FILE = "bluelist.properties";
	*/
	static int parking_num = 500;
	
	
	
	// On Create
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
    	
    	
    	IBMBluemix.initialize(this, "06640d88-fdee-4c21-811d-5c1d6a438cd0", "30cb7191d3958619510b8cea1cf2af9bda7edfb2", "mobipark.mybluemix.net");
    	IBMData dataService = IBMData.initializeService();  //Initializing object storage capability

    	IBMFileSync fileSync = IBMFileSync.initializeService();  //Initializing file storage capability

    	Parking.registerSpecialization(Parking.class);    	

    	
    	IBMCloudCode.initializeService();
    	IBMData.initializeService();
    	IBMFileSync.initializeService(); 
   	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
    	
    	// Display initial value
	    TextView t2 = (TextView)findViewById(R.id.textView1);                	
    	t2.setText(Integer.toString(parking_num));
    	
    	
    	// Find a set of objects by class
    	IBMQuery<Parking> queryByClass = null;
		try {
			queryByClass = IBMQuery.queryForClass(Parking.class);
		} catch (IBMDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	 	Parking prk = new Parking();
    	// Find a specific object
    	IBMQuery<Parking> queryForObject = prk.getQuery();
    	
    	List<Parking> itemList;
    
    	queryByClass.find().continueWith(new Continuation<List<Parking>, Void>() {


    	    @Override
    	    public Void then(Task<List<Parking>> task)  throws Exception  {
    	    	
    	    	
    	        if (task.isFaulted()) {
    	            // Handle errors
    	        } else {
    	        	
    	            // do more work
    	     
    	           // Log.e("read", "complete");
        	
    	            List<Parking> objects = task.getResult();
    	            IBMDataObject prk;
    	           sortDate(objects );
    	           // Collections.sort(objects);
           	        for (int i=0;i<objects.size();++i) {
           	        	Log.e(objects.get(i).getName(), objects.get(i).getInternal().getModifiedTime().toString());
           	        	//Log.e(objects.get(i).getInternal().getModifiedTime().toString(), ": value of time from cloud");
           	             
           	        	if (i <10) {
           	        		
           	           	   	parking_num = Integer.getInteger(objects.get(i).getName());
           	           //	 Log.e( ""+parking_num, ": parking values from cloud");
           	           	        
           	           	      	TextView t2 = (TextView)findViewById(R.id.textView1);                	
           	                	t2.setText(Integer.toString(parking_num));
           	        		
           	        	}
           	        	
           	        	

          	        }
           	        


        	        
    	        }
    	       

    	        return null;
    	        
    	    }
    	});
    	  

// Display the initial value       

    	
    	onButtonClick();
                        	
    }

    
	
   public void onButtonClick()
    
    {   	
	   
		IBMCloudCode.initializeService();
    	IBMData.initializeService();
    	IBMFileSync.initializeService(); 

          final Button btn_enter, btn_exit, btn_refresh;

          // In Constructor
          btn_enter = (Button) findViewById(R.id.enter);
          btn_exit = (Button) findViewById(R.id.exit);
          btn_refresh = (Button) findViewById(R.id.REFRESH);
         
    
         // for ENTER 
          btn_enter.setOnClickListener(new OnClickListener() {
        public void onClick(View arg0) {
        	
        	parking_num  = ( parking_num - 1);
        	
        	
        	TextView t2 = (TextView)findViewById(R.id.textView1);                	
        	t2.setText(Integer.toString(parking_num));
        	
  //      	
        	
        	 String parking_numc;
        	 parking_numc = ""+parking_num;
          	Parking prk = new Parking();
        	prk.setName(parking_numc);
        	
        	prk.save().continueWith(new Continuation<IBMDataObject, Void>() {

        	    public Void then(Task<IBMDataObject> task) throws Exception {
        	        if (task.isFaulted()) {
        	            // Handle errors
        	        } else {
        	            Log.e("Save", "complete");
        	            // Do more work
        	        }
        	        return null;
        	    }
        	});	  


        }
 
  	  
    });
    
    
          // for EXIT
    btn_exit.setOnClickListener(new OnClickListener() {
    	
        public void onClick(View arg0) {      	
              
        	
        	parking_num  = ( parking_num + 1);
        	
        
        	//EditText t1 = (EditText)findViewById(R.id.editText1);
        	
        	TextView t2 = (TextView)findViewById(R.id.textView1);
          
        	//
        	t2.setText(Integer.toString(parking_num));
        	
       	 	String parking_numc;
       	 	parking_numc = ""+parking_num;
       	 	Parking prk = new Parking();
       	 	prk.setName(parking_numc);
       	
       	 	prk.save().continueWith(new Continuation<IBMDataObject, Void>() 
       	 	{

       	 		public Void then(Task<IBMDataObject> task) throws Exception 
       	 		{
       	 				if (task.isFaulted()) {
       	 						// Handle errors
       	 				}
       	 				else {
       	 					Log.e("Save", "complete");
       	            // Do more work
       	 				}
       	 				return null;
       	        }
       	 	});	
        }});
       	// for Refresh
       	
        btn_refresh.setOnClickListener(new OnClickListener()
        {
            	public void onClick(View arg0) 
            	{      	
                  
            	
            	
            	
             	// Find a set of objects by class
            		IBMQuery<Parking> queryByClass = null;
            		try {
            				queryByClass = IBMQuery.queryForClass(Parking.class);
            				Log.e( "Refresh " , "Button pressed");
            			} 
            		catch (IBMDataException e1) {
        			// TODO Auto-generated catch block
            				e1.printStackTrace();
            			}

        		
            		Parking prk = new Parking();
            		IBMQuery<Parking> queryForObject = prk.getQuery();
            	           	
            
            		queryByClass.find().continueWith(new Continuation<List<Parking>, Void>()
            		{


            			@Override
            	    	public Void then(Task<List<Parking>> task)  throws Exception 
            	    	{
            	    	
            	    	
            				if (task.isFaulted())
            				{
            						// Handle errors
            					Log.e("not read", "refresh");
            				} 
            				else 
            				{
            	        	
            					// do more work
            	     
            					Log.e("read", "refresh");
                	
            					List<Parking> objects = task.getResult();
            					IBMDataObject prk;
            					sortDate(objects );
            	           // Collections.sort(objects);
            					for (int i=0;i<objects.size();++i) 
            					{
            						Log.e(objects.get(i).getName(), ": values from cloud in refresh");
            						Log.e(objects.get(i).getInternal().getModifiedTime().toString(), ": value of time from cloud");
                   	  
            						Log.e("count", "refresh");
            					}
                   	        
                   	  	   		parking_num = Integer.getInteger(objects.get(0).getName());
                   	  	   		Log.e( ""+parking_num, ": parking_but values from cloud");
                          	 
                   	  	   		TextView t2 = (TextView)findViewById(R.id.textView1);                	
                   	  	   		t2.setText(Integer.toString(parking_num));
                	        
            				}
            	       
       
            				return null;
            	        
            	    	} 
            	   });
            	
            	///
            	
            	
           	    }//on_click
           	});	//button refresh

        
    }//on button click
   

	//Sorting
	public List<Parking> sortDate(List<Parking> obj )
	{
		int len=obj.size();
		for (int i = 0; i < len - 1; ++i)
	    {
	      int minIndex = i;
	      for (int j = i + 1; j < len; ++j)
	      {
	        // "<" changed to use of compareTo()
	        if (obj.get(j).getInternal().getModifiedTime().toString().compareTo(obj.get(minIndex).getInternal().getModifiedTime().toString()) < 0)
	        {
	          minIndex = j;
	        }
	      }
	      
	      Parking temp = obj.get(i);
	      obj.set(i,obj.get(minIndex));
	      obj.set(minIndex, temp);
	    
	    }
		return obj;
	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
}
