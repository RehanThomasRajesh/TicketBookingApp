package com.example.ticketbooking;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseRegister extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ticketbook4.db";
    public static final String TABLE_NAME = "tbluser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FNAME";
    public static final String COL_3 = "LNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "MOBILE";
    public static final String COL_6 = "PASSWORD";
    public static final String COL_7 = "TYPE";


    public static final String TABLE_NAME1 = "tblcategory";
    public static final String COL_11 = "CID";
    public static final String COL_12 = "CATEGORY";

    public static final String TABLE_NAME2 = "tblplace";
    public static final String COL_21 = "PID";
    public static final String COL_22 = "PLACE";

    public static final String TABLE_NAME3 = "tbltheater";
    public static final String COL_31 = "TID";
    public static final String COL_32 = "TNAME";
    public static final String COL_33 = "TEMAIL";
    public static final String COL_34 = "TPASSWORD";
    public static final String COL_35 = "TPHONE";
    public static final String COL_36 = "PID";

    public static final String TABLE_NAME4 = "tblmovie";
    public static final String COL_41 = "MID";
    public static final String COL_42 = "MNAME";
    public static final String COL_43 = "LANGUAGE";
    public static final String COL_44 = "DESCRIPTION";
    public static final String COL_45 = "CASTT";
    public static final String COL_46 = "POSTER";
    public static final String COL_47 = "CID";

    public static final String TABLE_NAME5 = "tblrate";
    public static final String COL_51 = "RTID";
    public static final String COL_52 = "STYPE";
    public static final String COL_53 = "SRATE";
    public static final String COL_54 = "NOSEATS";
    public static final String COL_55 = "TID";

    public static final String TABLE_NAME6 = "tblrunning";
    public static final String COL_61 = "RID";
    public static final String COL_62 = "SDATE";
    public static final String COL_63 = "EDATE";
    public static final String COL_64 = "MID";
    public static final String COL_65 = "TID";

    public static final String TABLE_NAME7 = "tblshow";
    public static final String COL_71 = "SID";
    public static final String COL_72 = "TIMEDURATION";
    public static final String COL_73 = "MID";
    public static final String COL_74 = "TID";

    public static final String TABLE_NAME8 = "tblbooking";
    public static final String COL_81 = "BID";
    public static final String COL_82 = "DATE";
    public static final String COL_83 = "NOOFSEATS";
    public static final String COL_84 = "TIME";
    public static final String COL_85 = "RID";
    public static final String COL_86 = "RTID";
    public static final String COL_87 = "ID";

    public static final String TABLE_NAME9 = "tblaccount";
    public static final String COL_91 = "AID";
    public static final String COL_92 = "ACNO";
    public static final String COL_93 = "CARDNO";
    public static final String COL_94 = "NAME";
    public static final String COL_95 = "CVV";
    public static final String COL_96 = "EXPDATE";
    public static final String COL_97 = "BALANCE";


    public DatabaseRegister(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,FNAME TEXT,LNAME TEXT,EMAIL TEXT,MOBILE TEXT,PASSWORD TEXT,TYPE TEXT)");
        db.execSQL("create table " + TABLE_NAME1 + "(CID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT)");
        db.execSQL("create table " + TABLE_NAME2 + "(PID INTEGER PRIMARY KEY AUTOINCREMENT,PLACE TEXT )");
        db.execSQL("create table " + TABLE_NAME3 + "(TID INTEGER PRIMARY KEY AUTOINCREMENT,TNAME TEXT,TEMAIL TEXT,TPASSWORD TEXT,TPHONE TEXT,PID INTEGER,FOREIGN KEY(PID)REFERENCES tblplace(PID) ON DELETE CASCADE)");
        db.execSQL("create table " + TABLE_NAME4 + "(MID INTEGER PRIMARY KEY AUTOINCREMENT,MNAME TEXT,LANGUAGE TEXT,DESCRIPTION TEXT,CASTT TEXT,POSTER BLOB,CID INTEGER,FOREIGN KEY(CID)REFERENCES tblcategory(CID) ON DELETE CASCADE )");
        db.execSQL("create table " + TABLE_NAME5 + "(RTID INTEGER PRIMARY KEY AUTOINCREMENT,STYPE TEXT,SRATE TEXT,NOSEATS TEXT,TID INTEGER,FOREIGN KEY(TID)REFERENCES tbltheater(TID) ON DELETE CASCADE )");
        db.execSQL("create table " + TABLE_NAME6 + "(RID INTEGER PRIMARY KEY AUTOINCREMENT,SDATE TEXT,EDATE TEXT,MID INTEGER,TID INTEGER,FOREIGN KEY(MID)REFERENCES tblmovie(MID) ON DELETE CASCADE,FOREIGN KEY(TID)REFERENCES tbltheater(TID) ON DELETE CASCADE)");
        db.execSQL("create table " + TABLE_NAME7 + "(SID INTEGER PRIMARY KEY AUTOINCREMENT,TIMEDURATION TEXT,MID INTEGER,TID INTEGER,FOREIGN KEY(MID)REFERENCES tblmovie(MID) ON DELETE CASCADE,FOREIGN KEY(TID)REFERENCES tbltheater(TID) ON DELETE CASCADE)");
        db.execSQL("create table " + TABLE_NAME8 + "(BID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,NOOFSEATS INTEGER,TIME TEXT,RID INTEGER,RTID INTEGER,ID INTEGER,FOREIGN KEY(RID)REFERENCES tblrunning(RID) ON DELETE CASCADE,FOREIGN KEY(RTID)REFERENCES tblrate(RTID) ON DELETE CASCADE,FOREIGN KEY(ID)REFERENCES tbluser(ID))");
        db.execSQL("create table " + TABLE_NAME9 + "(AID INTEGER PRIMARY KEY AUTOINCREMENT,ACNO INTEGER,CARDNO INTEGER,NAME TEXT,CVV INTEGER,EXPDATE TEXT,BALANCE INTEGER)");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, "Admin");
        contentValues.put(COL_3, "Tester");
        contentValues.put(COL_4, "admin@gmail.com");
        contentValues.put(COL_5, "ADMINPHONE");
        contentValues.put(COL_6, "admin");
        contentValues.put(COL_7, "admin");
        long result = db.insert(TABLE_NAME, null, contentValues);//insert process returns 1:success,-1:failed
        //db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME6);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME7);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME8);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME9);
        onCreate(db);
    }

    //user table
    public boolean insertData(String fname, String lname, String email, String mobile, String password, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fname);
        contentValues.put(COL_3, lname);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, mobile);
        contentValues.put(COL_6, password);
        contentValues.put(COL_7, type);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkemail(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_4 + " =?", new String[]{Email});
        if (cursor.getCount() > 0) return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);//null is used instead of where condition since we select all
        return res;
    }

    public Boolean emailpassword(String Email, String Password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_4 + "=? and " + COL_6 + "=?", new String[]{Email, Password});
        if (cursor.getCount() > 0) return true;
        else
            return false;
    }

    public boolean insertPlace(String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_22, place);
        long result = db.insert(TABLE_NAME2, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkplace(String Place) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select *from tblplace where PLACE =?", new String[]{Place});
        if (cursor.getCount() > 0) return false;
        else
            return true;
    }

    public List<String> getPlace() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT * From " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Boolean checkCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME1 + " where " + COL_12 + "=?", new String[]{category});
        if (cursor.getCount() > 0) return false;
        else
            return true;
    }

    public boolean insertCat(String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_12, category);
        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertData1(String tname, String temail, String tpassword, String tphone, String tplace) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_32, tname);
        contentValues.put(COL_33, temail);
        contentValues.put(COL_34, tpassword);
        contentValues.put(COL_35, tphone);
        Cursor res = db.rawQuery("select PID from tblplace where PLACE=?", new String[]{tplace});
        res.moveToFirst();
        String s = res.getString(0);
        contentValues.put(COL_36, s);

        long result = db.insert(TABLE_NAME3, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean checkemail1(String Email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select *from tbltheater where TEMAIL =?", new String[]{Email});
        if (cursor.getCount() > 0) return false;
        else
            return true;
    }

    public List<String> getCategory() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT * From " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Boolean checkMovie(String mvname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME4 + " WHERE mname = ?", new String[]{mvname});
        //Cursor cursor=db.rawQuery("Select * from "+TABLE_NAME4 +" where "+COL_42 +"=?",new String[]{mvname});
        if (cursor.getCount() > 0) return false;
        else
            return true;
    }

    public boolean insertMovie(String categry, String mname, String language, String description, String cast, byte[] b) {
        SQLiteDatabase db = this.getWritableDatabase();

        /* Cursor res=db.rawQuery("select CID from tblcategory where CATEGORY=?",new String[]{categry});

         */
        Cursor cursor = db.rawQuery("SELECT * FROM tblcategory WHERE category = ?", new String[]{categry});
        //Cursor cursor=db.rawQuery("Select * from "+TABLE_NAME4 +" where "+COL_42 +"=?",new String[]{mvname});

        int s = 0;
        while (cursor.moveToNext()) {
            s = Integer.parseInt((cursor.getString(0)));
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_42,mname);
        contentValues.put(COL_43,language);
        contentValues.put(COL_44,description);
        contentValues.put(COL_45,cast);
        contentValues.put(COL_46,b);
        contentValues.put(COL_47,s);
        long result = db.insert(TABLE_NAME4,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;


    }
    public Cursor getTheatreDetails2(String email)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select *from tbltheater inner join tblplace on tbltheater.PID=tblplace.PID where TNAME =? ",new String[]{email});
        return res;
    }
    public List<String> getTheatreList()
    {
        List<String> list=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM tbltheater",null);
        if(res.moveToFirst())
        {
            do {
                list.add(res.getString(res.getColumnIndex("TNAME")));
            }while (res.moveToNext());
        }
        res.close();
        db.close();
        return list;
    }
    public boolean deleteTheatre(String tmaill)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        int tid=0;
        Cursor cursor=database.rawQuery("select TID from tbltheater where TEMAIL=?",new String[]{tmaill});
        while (cursor.moveToNext())
        {
            tid=cursor.getInt(cursor.getColumnIndex("TID"));
        }
        int result=database.delete(TABLE_NAME3,"TEMAIL=?",new String[]{tmaill});
        int result1=database.delete(TABLE_NAME5,"TID=?",new String[]{String.valueOf(tid)});
        if(result>0 && result1>0)
            return true;
        else
            return false;
    }
}