package mobile.group1.DB;

import java.util.HashMap;
import java.util.Vector;
import android.os.Bundle;
import android.util.Log;

import com.mobdb.android.DeleteRowData;
import com.mobdb.android.GetFile;
import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;
import com.mobdb.android.MobDB;
import com.mobdb.android.MobDBResponseListener;

public class MobDBTransaction
{
	public static abstract class MobDBTransactionListener
	{
		public abstract void onTransactionFinished(Bundle bundle);
	}
	
	// number representing the type of request
	public static enum QueryType{ Get, Save, Delete, File };
		
	// names for various fields that will be put in the response bundle
	public static String GET_RESULT    = "dbresult";
	public static String SAVE_RESULT   = "saveresult";
	public static String DELETE_RESULT = "deleteresult";
	public static String RESULT_CODE   = "resultcode";
	public static String MSG           = "message";
	public static String QUERY_TYPE    = "querytype";
	public static String FILE_NAME     = "filename";
	public static String FILE_DATA     = "filedata";
	
	// implements all the methods for interface MobDBResponseListener so subclasses
	// only have to implement the listeners they care about.
	private class MobDBResponseListenerCustom implements MobDBResponseListener
	{
		@Override public void mobDBResponse(Vector<HashMap<String, Object[]>> result){}
		@Override public void mobDBResponse(String jsonObj)                          {}
		@Override public void mobDBFileResponse(String fileName, byte[] fileData)    {}
		@Override public void mobDBSuccessResponse()                                 {}	
		@Override public void mobDBErrorResponse(Integer errValue, String errMsg)    {}
	}
	
	public MobDBTransaction(String appKey, QueryType queryType, Object filter, final MobDBTransactionListener listener)
	{
		final Bundle bundle = new Bundle();
		bundle.putInt(QUERY_TYPE, queryType.ordinal());
		bundle.putBoolean(RESULT_CODE, false);
		
		switch(queryType)
		{	
		///////////////////////////////////////////////////////////////////////
		// a delete query
		///////////////////////////////////////////////////////////////////////
		case Delete:
			MobDB.getInstance().execute(appKey, (DeleteRowData) filter, null, false, new MobDBResponseListenerCustom()
			{
				@Override
				public void mobDBSuccessResponse()
				{
					bundle.putBoolean(RESULT_CODE, true);
					listener.onTransactionFinished(bundle);
				}
				
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg)
				{
					bundle.putString(MSG, errMsg);
					listener.onTransactionFinished(bundle);
				}
			});
			break;
			
		///////////////////////////////////////////////////////////////////////
		// a get query
		///////////////////////////////////////////////////////////////////////
		case Get:
			MobDB.getInstance().execute(appKey, (GetRowData) filter, null, false, new MobDBResponseListenerCustom()
			{
				@Override
				public void mobDBResponse(Vector<HashMap<String, Object[]>> result)
				{
					bundle.putBoolean(RESULT_CODE, true);
					bundle.putSerializable(GET_RESULT, result);
					listener.onTransactionFinished(bundle);
				}
				
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg)
				{
					bundle.putString(MSG, errMsg);
					listener.onTransactionFinished(bundle);
				}
			});
			break;
			
			
		///////////////////////////////////////////////////////////////////////
		// a save query
		///////////////////////////////////////////////////////////////////////			
		case Save:
			MobDB.getInstance().execute(appKey, (InsertRowData) filter, null, false, new MobDBResponseListenerCustom()
			{
				@Override
				public void mobDBSuccessResponse()
				{
					bundle.putBoolean(RESULT_CODE, true);
					listener.onTransactionFinished(bundle);
				}

				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg)
				{
					bundle.putString(MSG, errMsg);
					listener.onTransactionFinished(bundle);
				}
			});
			
		case File:
			MobDB.getInstance().execute(appKey, (GetFile) filter, null, false, new MobDBResponseListenerCustom()
			{
				@Override
				public void mobDBErrorResponse(Integer errValue, String errMsg)
				{
					bundle.putString(MSG, errMsg);
					listener.onTransactionFinished(bundle);
				}
				
				@Override
				public void mobDBFileResponse(String fileName, byte[] fileData)
				{
					bundle.putBoolean(RESULT_CODE, true);
					bundle.putString(FILE_NAME, fileName);
					bundle.putByteArray(FILE_DATA, fileData);
					listener.onTransactionFinished(bundle);					
				}
			});
		}
	}
}
