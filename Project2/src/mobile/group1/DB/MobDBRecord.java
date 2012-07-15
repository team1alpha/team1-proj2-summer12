package mobile.group1.DB;

import mobile.group1.DB.MobDBTransaction.MobDBTransactionListener;
import mobile.group1.DB.MobDBTransaction.QueryType;

import com.mobdb.android.DeleteRowData;
import com.mobdb.android.GetRowData;
import com.mobdb.android.InsertRowData;

import android.os.Bundle;

public abstract class MobDBRecord
{
	///////////////////////////////////////////////////////////////////////////
	// Constants
	///////////////////////////////////////////////////////////////////////////
	
	// indicates the type of request
	public enum RecordQueryType { Get, GetAll, Save, Delete; }

	///////////////////////////////////////////////////////////////////////////
	// Listener Definitions
	///////////////////////////////////////////////////////////////////////////

	// used to set a callback for giving responses back o client code
	public static abstract class ResponseListener
	{
		public abstract void onResponse(Bundle bundle);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// Methods subclasses must provide in order for this base class to do 
	// work on the subclasses part. These contain all the specific code for
	// a subclass.
	///////////////////////////////////////////////////////////////////////////	
	protected abstract String        AppKey();
	protected abstract String        TableName();
	public    abstract String        toString();
	protected abstract InsertRowData Inserter();
	protected abstract Bundle        PrepareResponse(RecordQueryType queryType, Bundle bundle);
	protected abstract String        KeyField();
	protected abstract String        KeyValue();
	
	///////////////////////////////////////////////////////////////////////////	
	// private var
	///////////////////////////////////////////////////////////////////////////	
	private ResponseListener listener;

	///////////////////////////////////////////////////////////////////////////	
	// the only real method that does work on behalf of this class
	///////////////////////////////////////////////////////////////////////////	
	public MobDBRecord(ResponseListener listener)
	{
		this.listener = listener;
	}
	
	///////////////////////////////////////////////////////////////////////////
	// all these are completely parameterized on subclass override methods
	///////////////////////////////////////////////////////////////////////////
	
	// SELECT * FROM TableName()
	protected GetRowData AllGetter()
	{
		return new GetRowData(TableName());
	}
	
	// SELECT * FROM TableName() Where KeyField() == KeyValue()
	protected GetRowData Getter()
	{
		GetRowData getRowData = new GetRowData(TableName());
		getRowData.whereEqualsTo(KeyField(), KeyValue());
		return getRowData;
	}
	
	// DELETE FROM TableName() Where KeyField() == KeyValue()
	protected DeleteRowData Deleter()
	{
		DeleteRowData deleteRowData = new DeleteRowData(TableName());
		deleteRowData.whereEqualsTo(KeyField(), KeyValue());
		return deleteRowData;
	}
	
	// Saves a record to its given database and table. For simplicity the item is first deleted
	// then reinserted.
	public void Save()
	{
		new MobDBTransaction(AppKey(), QueryType.Delete, Deleter(), new MobDBTransactionListener()
		{
			@Override
			public void onTransactionFinished(Bundle bundle)
			{
				new MobDBTransaction(AppKey(), QueryType.Save, Inserter(), new MobDBTransactionListener()
				{
					@Override
					public void onTransactionFinished(Bundle bundle)
					{
						listener.onResponse(PrepareResponse(RecordQueryType.Delete, bundle));
					}
				}); 

				listener.onResponse(PrepareResponse(RecordQueryType.Save, bundle));
			};
		});
	}
	
	// Deletes a record from its given database and table
	public void Delete()
	{
		new MobDBTransaction(AppKey(), QueryType.Delete, Deleter(), new MobDBTransactionListener()
		{
			@Override
			public void onTransactionFinished(Bundle bundle)
			{
				listener.onResponse(PrepareResponse(RecordQueryType.Delete, bundle));
			}
		}); 
	}
	
	// Gets a record from its given database and table
	public void Get()
	{
		new MobDBTransaction(AppKey(), QueryType.Get, Getter(), new MobDBTransactionListener()
		{
			@Override
			public void onTransactionFinished(Bundle bundle)
			{
				listener.onResponse(PrepareResponse(RecordQueryType.Get, bundle));
			}
		});
	}
	
	// Gets all record keys from their given database and table
	public void GetALL()
	{
		new MobDBTransaction(AppKey(), QueryType.Get, AllGetter(), new MobDBTransactionListener()
		{
			@Override
			public void onTransactionFinished(Bundle bundle)
			{
				listener.onResponse(PrepareResponse(RecordQueryType.GetAll, bundle));
			}
		});
	}
}
