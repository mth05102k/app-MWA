package com.example.some.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.some.model.Work;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CongViec.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE work(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT," +
                "date TEXT," +
                "status TEXT," +
                "isCooperated INT)";
        sqLiteDatabase.execSQL(sqlQuery);

        String sql1="CREATE TABLE userss("+
                "id INTEGER PRIMARY KEY,"+
                "email TEXT,pass TEXT,displayName TEXT,soDeep TEXT,birthDay TEXT,Gender TEXT)";
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // get all + order by date descending
    public List<Work> getAll() {
        List<Work> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("work", null, null,
                null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Work(id, title, content, date, status, isCooperated));
        }
        return list;
    }

    // add
    public long addWork(Work i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("content", i.getContent());
        values.put("date", i.getDate());
        values.put("status", i.getStatus());
        if (i.isCooperated()) {
            values.put("isCooperated", 1);
        } else {
            values.put("isCooperated", 0);
        }
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("work", null, values);
    }

    // lay cac item theo date
    public List<Work> getByDate(String date) {
        List<Work> list = new ArrayList<>();
        String whereClause = "date like ?"; // Dieu kien where
        String[] whereArgument = {date}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("work", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String status = rs.getString(4);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Work(id, title, content, date, status, isCooperated));
        }
        return list;
    }

    // update
    public int updateWork(Work i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("content", i.getContent());
        values.put("date", i.getDate());
        values.put("status", i.getStatus());
        if (i.isCooperated()) {
            values.put("isCooperated", 1);
        } else {
            values.put("isCooperated", 0);
        }
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return db.update("work", values, whereClause, whereArgs);
    }

    // delete
    public int delete(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("work", whereClause, whereArgs);
    }

    // lay cac item theo title
    public List<Work> searchByTitle(String key) {
        List<Work> list = new ArrayList<>();
        String whereClause = "title like ?"; // Dieu kien where
        String[] whereArgument = {"%"+key+"%"}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("work", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Work(id, title, content, date, status, isCooperated));
        }
        return list;
    }

    // lay cac item theo status
    public List<Work> searchByCategory(String status) {
        List<Work> list = new ArrayList<>();
        String whereClause = "status like ?"; // Dieu kien where
        String[] whereArgument = {status}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("work", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Work(id, title, content, date, status, isCooperated));
        }
        return list;
    }

//    /*
//    // lay cac item theo date bat dau + date ket thuc
//    // Để so sánh bắt buộc tất cả các trường liên quan đến ngày phải để ở dạng yyyy-MM-dd
    public List<Work> searchByDateFromTo(String from, String to) {
        List<Work> list = new ArrayList<>();
        String whereClause = "date BETWEEN (?) AND (?)"; // Dieu kien where
        String[] whereArgument = {getFormattedDate(from.trim()), getFormattedDate(to.trim())}; // Cho ? argument
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.query("work", null, whereClause, whereArgument,
                null, null, null);
        while(rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int coop = rs.getInt(5);
            boolean isCooperated;
            if (coop == 0) {
                isCooperated = false;
            } else {
                isCooperated = true;
            }
            list.add(new Work(id, title, content, date,status, isCooperated));
        }
        return list;
    }

    // Chuyển dd/MM/yyyy trong ô Search thành yyyy-MM-dd
    public String getFormattedDate(String date) {
        String[] s = date.split("/");
        StringBuilder res = new StringBuilder();
        for (int i = s.length - 1; i >= 0; i--) {
            res.append(s[i]);
            res.append("-");
        }
        res.deleteCharAt(res.length() - 1);
        System.out.println(res.toString());
        return res.toString();
    }

}
