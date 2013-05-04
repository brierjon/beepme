/*
This file is part of BeepMe.

BeepMe is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

BeepMe is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with BeepMe. If not, see <http://www.gnu.org/licenses/>.

Copyright since 2012 Michael Glanznig
http://beepme.glanznig.com
*/

package com.glanznig.beepme.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StorageHandler {
	
	private static final String TAG = "StorageHandler";
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		protected static final String DB_NAME = "beepme";
		protected static final int DB_VERSION = 19;
		
		public DatabaseHelper(Context ctx) {
			super(ctx, DB_NAME, null, DB_VERSION);
		} 

		@Override
		public void onCreate(SQLiteDatabase db) {
			SampleTable.createTable(db);
			TagTable.createTable(db);
			SampleTagTable.createTable(db);
			UptimeTable.createTable(db);
			ScheduledBeepTable.createTable(db);
			VocabularyTable.createTable(db);
			TimerProfileTable.createTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// legacy schema "upgrade"
			if (newVersion < 17 || oldVersion < 16) {
				dropTables(db);
				onCreate(db);
			}
			
			
			if (oldVersion == 16 && newVersion == 17) {
				db.execSQL("ALTER TABLE " + TimerProfileTable.getTableName() +
						" ADD COLUMN minSizeBeepInterval INTEGER NOT NULL DEFAULT 60");
				ContentValues values = new ContentValues();
				values.put("minSizeBeepInterval", 60);
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "1" });
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "2" });
			}
			
			
			if (oldVersion == 16 && newVersion == 18) {
				db.execSQL("ALTER TABLE " + TimerProfileTable.getTableName() +
						" ADD COLUMN minSizeBeepInterval INTEGER NOT NULL DEFAULT 60");
				ContentValues values = new ContentValues();
				values.put("minSizeBeepInterval", 60);
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "1" });
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "2" });
				
				db.execSQL("ALTER TABLE " + ScheduledBeepTable.getTableName() +
						" ADD COLUMN received INTEGER");
			}
			
			if (oldVersion == 17 && newVersion == 18) {
				db.execSQL("ALTER TABLE " + ScheduledBeepTable.getTableName() +
						" ADD COLUMN received INTEGER");
			}
			
			
			if (oldVersion == 16 && newVersion == 19) {
				db.execSQL("ALTER TABLE " + TimerProfileTable.getTableName() +
						" ADD COLUMN minSizeBeepInterval INTEGER NOT NULL DEFAULT 60");
				ContentValues values = new ContentValues();
				values.put("minSizeBeepInterval", 60);
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "1" });
				db.update(TimerProfileTable.getTableName(), values, "_id=?", new String[] { "2" });
				
				db.execSQL("ALTER TABLE " + ScheduledBeepTable.getTableName() +
						" ADD COLUMN received INTEGER");
				
				db.execSQL("CREATE TABLE IF NOT EXISTS sample2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"timestamp INTEGER NOT NULL UNIQUE, " +
						"title TEXT, " +
						"description TEXT, " +
						"accepted INTEGER NOT NULL, " +
						"photoUri TEXT, " +
						"uptimeId INTEGER, " +
						"FOREIGN KEY (uptimeId) REFERENCES "  + UptimeTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO sample2 (_id, timestamp, title, description, accepted, photoUri)" +
						"SELECT _id, timestamp, title, description, accepted, photoUri FROM " + SampleTable.getTableName());
				db.execSQL("DROP TABLE " + SampleTable.getTableName());
				db.execSQL("ALTER TABLE sample2 RENAME TO " + SampleTable.getTableName());
				
				db.execSQL("CREATE TABLE IF NOT EXISTS uptime2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"start INTEGER NOT NULL UNIQUE, " +
						"end INTEGER UNIQUE, " +
						"timerProfileId INTEGER, " +
						"FOREIGN KEY (timerProfileId) REFERENCES "  + TimerProfileTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO uptime2 (_id, start, end)" +
						"SELECT _id, start, end FROM " + UptimeTable.getTableName());
				db.execSQL("DROP TABLE " + UptimeTable.getTableName());
				db.execSQL("ALTER TABLE uptime2 RENAME TO " + UptimeTable.getTableName());
				
				db.execSQL("UPDATE " + VocabularyTable.getTableName() + " SET name='keywords' WHERE _id = 1");
			}
			
			if (oldVersion == 17 && newVersion == 19) {
				db.execSQL("ALTER TABLE " + ScheduledBeepTable.getTableName() +
						" ADD COLUMN received INTEGER");
				
				db.execSQL("CREATE TABLE IF NOT EXISTS sample2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"timestamp INTEGER NOT NULL UNIQUE, " +
						"title TEXT, " +
						"description TEXT, " +
						"accepted INTEGER NOT NULL, " +
						"photoUri TEXT, " +
						"uptimeId INTEGER, " +
						"FOREIGN KEY (uptimeId) REFERENCES "  + UptimeTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO sample2 (_id, timestamp, title, description, accepted, photoUri)" +
						"SELECT _id, timestamp, title, description, accepted, photoUri FROM " + SampleTable.getTableName());
				db.execSQL("DROP TABLE " + SampleTable.getTableName());
				db.execSQL("ALTER TABLE sample2 RENAME TO " + SampleTable.getTableName());
				
				db.execSQL("CREATE TABLE IF NOT EXISTS uptime2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"start INTEGER NOT NULL UNIQUE, " +
						"end INTEGER UNIQUE, " +
						"timerProfileId INTEGER, " +
						"FOREIGN KEY (timerProfileId) REFERENCES "  + TimerProfileTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO uptime2 (_id, start, end)" +
						"SELECT _id, start, end FROM " + UptimeTable.getTableName());
				db.execSQL("DROP TABLE " + UptimeTable.getTableName());
				db.execSQL("ALTER TABLE uptime2 RENAME TO " + UptimeTable.getTableName());
				
				db.execSQL("UPDATE " + VocabularyTable.getTableName() + " SET name='keywords' WHERE _id = 1");
			}
			
			if (oldVersion == 18 && newVersion == 19) {
				db.execSQL("CREATE TABLE IF NOT EXISTS sample2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"timestamp INTEGER NOT NULL UNIQUE, " +
						"title TEXT, " +
						"description TEXT, " +
						"accepted INTEGER NOT NULL, " +
						"photoUri TEXT, " +
						"uptimeId INTEGER, " +
						"FOREIGN KEY (uptimeId) REFERENCES "  + UptimeTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO sample2 (_id, timestamp, title, description, accepted, photoUri)" +
						"SELECT _id, timestamp, title, description, accepted, photoUri FROM " + SampleTable.getTableName());
				db.execSQL("DROP TABLE " + SampleTable.getTableName());
				db.execSQL("ALTER TABLE sample2 RENAME TO " + SampleTable.getTableName());
				
				db.execSQL("CREATE TABLE IF NOT EXISTS uptime2 (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
						"start INTEGER NOT NULL UNIQUE, " +
						"end INTEGER UNIQUE, " +
						"timerProfileId INTEGER, " +
						"FOREIGN KEY (timerProfileId) REFERENCES "  + TimerProfileTable.getTableName() + " (_id)" +
						")");
				db.execSQL("INSERT INTO uptime2 (_id, start, end)" +
						"SELECT _id, start, end FROM " + UptimeTable.getTableName());
				db.execSQL("DROP TABLE " + UptimeTable.getTableName());
				db.execSQL("ALTER TABLE uptime2 RENAME TO " + UptimeTable.getTableName());
				
				db.execSQL("UPDATE " + VocabularyTable.getTableName() + " SET name='keywords' WHERE _id = 1");
			}
		}
		
		public void dropTables(SQLiteDatabase db) {
			SampleTagTable.dropTable(db);
			SampleTable.dropTable(db);
			TagTable.dropTable(db);
			UptimeTable.dropTable(db);
			ScheduledBeepTable.dropTable(db);
			VocabularyTable.dropTable(db);
			TimerProfileTable.dropTable(db);
		}
		
		public void truncateTables() {
			dropTables(this.getWritableDatabase());
			onCreate(this.getWritableDatabase());
		}
		
		public static String getDbName() {
			return DB_NAME;
		}
	}
	
	private static DatabaseHelper dbHelper = null;
	private Context ctx = null;
	
	public StorageHandler(Context ctx) {
		this.ctx = ctx;
		if (dbHelper == null) {
			dbHelper = new DatabaseHelper(ctx.getApplicationContext());
		}
	}
	
	public SQLiteDatabase getDb() {
		return dbHelper.getWritableDatabase();
	}
	
	public void truncateTables() {
		dbHelper.truncateTables();
	}
	
	public Context getContext() {
		return ctx;
	}
	
	public static String getDatabaseName() {
		return DatabaseHelper.getDbName();
	}
}
