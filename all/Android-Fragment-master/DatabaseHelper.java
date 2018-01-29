package com.css.bcg.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.css.bcg.R;
import com.css.bcg.util.Loger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/*
 * Class contains all the functions of Database.
 * This is global class for database which use though out the system
 */
@SuppressLint("SdCardPath")
public class DatabaseHelper extends SQLiteOpenHelper {


    //singleton/ single instance reference of database instance
    public static DatabaseHelper _dbHelper;

    //The Android's default system path of your application database.
    public static String DB_PATH;
    //database name
    public static String DB_NAME = "bcg.sqlite";
    public final Context _context;
    //reference of database
    public SQLiteDatabase sqlDB;
    //
    public String _searchToken;
    //Contains search result when user search with any text
    public Vector<Object> _searchResultVec;

    SQLiteDatabase sqliteDatabase;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);// 1? its Database Version
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this._context = context;


    }

    public static DatabaseHelper getInstance(Context context) {
        if (_dbHelper == null) {
            _dbHelper = new DatabaseHelper(context);
        }
        return _dbHelper;
    }

    public String getLastSearchToken() {
        return _searchToken;
    }

    //Creating DB if not created then else nothing
    public void createDataBase() throws IOException {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assets
                copyDataBase();
                //Log.e("FirstTime :: ", "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        } else {
            //Log.e("Already exist :: ", "DB");
        }
    }

    /*
     * Method will return boolean value according to table have data or not
     */
    public boolean isTableDataExists(String tableName) {
        Cursor cur = getTableAllData(tableName);
        return cur.getCount() > 0;

    }


    /*
     * Check that the database exists here: /data/data/your package/databases/Da Name
     */
    private boolean checkDataBase() {

        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /*
     * Copy the database from assets to phone memory
     */
    private void copyDataBase() throws IOException {
        InputStream mInput = _context.getAssets().open("database" + "/" + DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    /*
     * Method will return all data of table in Cursor
     */
    public Cursor getTableAllData(String Table) {

        Cursor cur = null;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cur = db.query(true, Table, null, null, null, null, null, null, null);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(_context, "Error :: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return cur;

    }


    /*
    * copy database to Mobile sdCard
    */
    public String copyDataBaseToMobile() {
        try {
            File sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

            if (sd.canWrite()) {
                String currentDBPath = DB_PATH + DB_NAME;
                String appName = _context.getResources().getString(R.string.app_name);
                //String backupDBPath = new DateUtils().getTime() + "_" + DB_NAME;
                String backupDBPath = DB_NAME;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }

                return "Database copied :: " + backupDB;
            } else {
                return "canWrite";
            }

        } catch (Exception e) {
            return "error in copying database :: " + e.getMessage();
        }
    }


    /*
     * Method contains custom query and return choice
     * data in Cursor format
     */
    public Cursor getChoiceData(String table, ArrayList<String> columns) {

        Cursor cur = null;
        SQLiteDatabase db = null;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {

            if (i < columns.size() - 1)
                builder.append(columns.get(i) + ",");
            else
                builder.append(columns.get(i));
        }

        String params = builder.toString();


        try {

            db = this.getReadableDatabase();

            String query = "SELECT " + params + " FROM " + table;
            cur = db.rawQuery(query, null);

            //Log.v("Query >> ", query);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(_context, "Error :: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return cur;

    }

    public Cursor getDataUsingCustomQuery(String MY_QUERY, String[] value) {

        SQLiteDatabase db = null;
        Cursor cur = null;

        try {

            db = this.getReadableDatabase();
            cur = db.rawQuery(MY_QUERY, value);
            //Log.v("MY_QUERY >> ", MY_QUERY);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(_context, "Error :: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return cur;
    }

    /*
     * Method will close DB if it is opened
     */
    @Override
    public synchronized void close() {

        if (sqlDB != null)
            sqlDB.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    /**
     * Method will insert data into table
     *
     * @param tableName -> table name
     * @param cv        -> data array
     * @return
     */
    public long insertRecord(String tableName, ContentValues cv) {
        long rowId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            rowId = db.insert(tableName, null, cv);

        } catch (Exception e) {
            e.printStackTrace();
            rowId = 0;
        } finally {
            db.close();
        }

        return rowId;
    }


    /**
     * Method will delete record from table
     *
     * @param TABLE    -> table name
     * @param KEY_NAME -> Where clause key
     * @return rowId -> recordId to be delete
     */
    public int deleteRecord(String TABLE, String KEY_NAME, int rowId) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            rowId = db.delete(TABLE, KEY_NAME + " = " + rowId, null);

            result = 1;

        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        } finally {
            db.close();
        }

        return result;
    }


    /*
     * Delete record by your own custom query and parameters
     */
    public int deleteRecordByCustomQuery(String tableName, String whereClause) {

        int result = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        try {

            String query = "DELETE FROM " + tableName + " WHERE " + whereClause;

            Loger.d("DELETE QUERY ", "Query == >> " + query);
            db.execSQL(query);

            result = 1;
            //Log.v("deleteRecordByCustomQuery >> ", query);
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();

        } finally {

            db.close();
        }

        return result;
    }


    public void batchInsert(String table, ArrayList<HashMap<String, String>> listData) {

        boolean newQuery = true;
        String query = "";
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            //loop through array list which contains ContentValues
            for (int i = 0; i < listData.size(); i++) {

                HashMap<String, String> mapValue = listData.get(i);
                //mapValue.get(i);

                //cv.get(i);

                if (newQuery) {
                    query = "INSERT INTO " + table + " (server_item_id, server_topping_id, name, description, price_size_10, price_size_12, price_size_16)";
                    newQuery = false;

                    //Log.v("Query :: ", query);
                } else {
                    query += " UNION";
                }

                query += " SELECT 1 , 2, desc test, 10, 12, 16";

                if (i != 0 && i % 499 == 0) {

                    db.execSQL(query);
                    newQuery = true;
                }
            }

        } catch (Exception e) {

            Log.v("Batch Error >> ", e.getMessage());

        } finally {

            Log.v("Batch Insert  >> ", "Execute");
            db.close();
        }


        //executing remaining lines
        /*if (i%499!=0) {
            sqlDB.execSQL(query);
		}*/
    }

    /**
     * update particular row of table
     *
     * @param rowId -> id (P.K)
     * @param table -> table name
     * @param cv    -> data to update
     */

    public void updateRow(long rowId, String table, ContentValues cv) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.update(table, cv, "id=" + rowId, null);


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {

            db.close();
        }
    }


    /**
     * Method will return row count of cursor
     *
     * @param table -> table name
     * @return -> row count
     */
    public int getCursorRowCount(String table) {
        int count = 0;
        try {
            Cursor cCount = getCursor(table);
            count = cCount.getCount();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            Toast.makeText(_context, "DB Error:: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return count;
    }

    /**
     * Method will execute Custom query with condition
     *
     * @param table     -> table name
     * @param condition -> condition
     * @return -> result cursor
     */
    public Cursor getCursor(String table, String condition) {
        sqliteDatabase = getReadableDatabase();
        Cursor mCursor = sqliteDatabase.rawQuery("Select * from " + table + " " + condition, null);
        return mCursor;

    }

    public void updateProfilePicture(int rowId, String table, ContentValues cv) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.update(table, cv, "id=" + rowId, null);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {

            db.close();
        }
    }

    /**
     * Method will execute simple query
     *
     * @param table -> table name
     * @return -> result cursor
     */
    public Cursor getCursor(String table) {

        Cursor mCursor = null;
        try {

            sqliteDatabase = getReadableDatabase();
            mCursor = sqliteDatabase.rawQuery("SELECT * FROM '" + table + "'", null);

        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(_context, "DB Error:: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return mCursor;
    }

    /**
     * this will truncate table
     *
     * @param TABLE -> table name
     */
    public void truncateTable(String TABLE) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
            db.delete(TABLE, null, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public SQLiteDatabase getReadPermission() {
        SQLiteDatabase db = null;

        try {
            db = this.getReadableDatabase();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
        return db;
    }

    public SQLiteDatabase getWritePermission() {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            db.close();
        }
        return db;
    }


    public SQLiteDatabase getWriteableDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }


    public String getLatInsertIdString(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String getLastInsertId = "SELECT last_insert_rowid() as id FROM " + tableName;
        Cursor curGetLastInsertId = getDataUsingCustomQuery(getLastInsertId, null);
        if (curGetLastInsertId != null) {
            curGetLastInsertId.moveToFirst();
            return curGetLastInsertId.getString(curGetLastInsertId.getColumnIndex("id"));
        }
        return "";
    }


    public int getLatInsertIdInt(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String getLastInsertId = "SELECT last_insert_rowid() as id FROM " + tableName;
        Cursor curGetLastInsertId = getDataUsingCustomQuery(getLastInsertId, null);
        if (curGetLastInsertId != null) {
            curGetLastInsertId.moveToFirst();
            return Integer.parseInt(curGetLastInsertId.getString(curGetLastInsertId.getColumnIndex("id")));
        }
        return 0;
    }


}

