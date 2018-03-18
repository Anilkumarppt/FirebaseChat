package cs656.com.firebasemessengerapp.database;

/**
 * Created by swati on 3/17/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME="user_db";
    public static final String TABALE_NAME="usersinfo";
    public static final String COLUMN_USERNAME="username";
    public static final String COLUMAN_MOBILE="usermobile";
    public static final String COLUMN_EMAIL="useremail";
    public static final String COLUMN_ID="id";
    public static final int DB_VERSION=1;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABALE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMAN_MOBILE + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+TABALE_NAME);

    }
    public boolean insertUserdata(String username,String usermobile,String useremail ){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,username);
        values.put(COLUMN_EMAIL,useremail);
        values.put(COLUMAN_MOBILE,usermobile);
        db.insert(TABALE_NAME,null,values);
        return  true;
    }
    public boolean updateUserdata(Integer id,String usernamme,String usermobile,String useremail){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_USERNAME,usernamme);
        values.put(COLUMN_EMAIL,useremail);
        values.put(COLUMAN_MOBILE,usermobile);
        db.update(TABALE_NAME,values,"id= ?",new String[]{Integer.toString(id)});
        return true;
    }
    public ArrayList<String> getAllUsers(){
        ArrayList<String> sqlContactslist=new ArrayList<String >();
        SQLiteDatabase db=this.getReadableDatabase();
        String sqlquery="Select * from usersinfo";
        Cursor cursor=db.rawQuery(sqlquery,null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            sqlContactslist.add(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            sqlContactslist.add(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            sqlContactslist.add(cursor.getString(cursor.getColumnIndex(COLUMAN_MOBILE)));
        }
        return sqlContactslist;
    }
    public void dupliacates(){
        getWritableDatabase().execSQL("delete from usersinfo where _id not in (SELECT MIN(_id ) FROM post GROUP BY post_id)");

    }

}
