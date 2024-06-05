package com.vishnu.vitalsense;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "vitals_sense.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,email TEXT,password TEXT,age INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE spo2(id INTEGER PRIMARY KEY AUTOINCREMENT,spo2 INTEGER,user_id INTEGER,timestamp TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE heart_rate(id INTEGER PRIMARY KEY AUTOINCREMENT,heart_rate INTEGER,user_id INTEGER,timestamp TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE resp_rate(id INTEGER PRIMARY KEY AUTOINCREMENT,resp_rate INTEGER,user_id INTEGER,timestamp TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS spo2");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS heart_rate");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS resp_rate");
        onCreate(sqLiteDatabase);
    }

    public int addUser(String name, String email, String password, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO user(name,email,password,age) VALUES('" + name + "','" + email + "','" + password + "'," + age + ")");
        return 1;
    }

    public int addSpo2(int spo2, int user_id, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO spo2(spo2,user_id,timestamp) VALUES(" + spo2 + "," + user_id + ",'" + timestamp + "')");
        return 1;
    }

    public int addHeartRate(int heart_rate, int user_id, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO heart_rate(heart_rate,user_id,timestamp) VALUES(" + heart_rate + "," + user_id + ",'" + timestamp + "')");
        return 1;
    }

    public int addRespRate(int resp_rate, int user_id, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO resp_rate(resp_rate,user_id,timestamp) VALUES(" + resp_rate + "," + user_id + ",'" + timestamp + "')");
        return 1;
    }

    public int checkUser(String email, String password) {
        Log.i("DBHelper", "Checking user: " + email + " " + password);
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM user WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        int userId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(0);
            Log.i("DBHelper", "User ID: " + userId);
            cursor.close();
        }
        return userId;
    }
    public String getPassword(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT password FROM user WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        String password = null;
        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(0);
            cursor.close();
        }
        return password;
    }

    public HashMap<String, String> getUser(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE id = " + user_id;
        Cursor cursor = db.rawQuery(query, null);

        HashMap<String, String> userData = new HashMap<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");
            int passwordIndex = cursor.getColumnIndex("password");
            int ageIndex = cursor.getColumnIndex("age");

            if (idIndex != -1) {
                userData.put("id", cursor.getString(idIndex));
            }
            if (nameIndex != -1) {
                userData.put("name", cursor.getString(nameIndex));
            }
            if (emailIndex != -1) {
                userData.put("email", cursor.getString(emailIndex));
            }
            if (passwordIndex != -1) {
                userData.put("password", cursor.getString(passwordIndex));
            }
            if (ageIndex != -1) {
                userData.put("age", cursor.getString(ageIndex));
            }
            cursor.close();
            return userData;
        } else {
            return null;
        }
    }
    public int[] getSpo2(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM spo2 WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);

        int[] spo2Data = new int[5];
        int i = 4;

        if (cursor != null && cursor.getCount() >=5) {
            cursor.moveToFirst();
            int spo2Index = cursor.getColumnIndex("spo2");

            while (!cursor.isAfterLast()) {
                if (spo2Index != -1) {
                    spo2Data[i] = cursor.getInt(spo2Index);
                }
                i--;
                cursor.moveToNext();
            }
            cursor.close();
            return spo2Data;
        } else {
            return null;
        }
    }
    public int[] getHeartRate(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM heart_rate WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);

        int[] heartRateData = new int[5];
        int i = 4;

        if (cursor != null && cursor.getCount() >=5) {
            cursor.moveToFirst();
            int heartRateIndex = cursor.getColumnIndex("heart_rate");

            while (!cursor.isAfterLast()) {
                if (heartRateIndex != -1) {
                    heartRateData[i] = cursor.getInt(heartRateIndex);
                    Log.i("DBHelper", "Heart Rate: " + heartRateData[i]);
                }
                i--;
                cursor.moveToNext();
            }
            cursor.close();
            return heartRateData;
        } else {
            return null;
        }
    }
    public int[] getRespRate(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM resp_rate WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 5";
        Cursor cursor = db.rawQuery(query, null);

        int[] respRateData = new int[5];
        int i = 4;

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int respRateIndex = cursor.getColumnIndex("resp_rate");

            while (!cursor.isAfterLast()) {
                if (respRateIndex != -1) {
                    respRateData[i] = cursor.getInt(respRateIndex);
                }
                i--;
                cursor.moveToNext();
            }
            cursor.close();
            return respRateData;
        } else {
            return null;
        }
    }
    public HashMap<String, String> getLastData(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM spo2 WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        HashMap<String, String> lastData = new HashMap<>();

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int spo2Index = cursor.getColumnIndex("spo2");
            int timestampIndex = cursor.getColumnIndex("timestamp");

            if (spo2Index != -1) {
                lastData.put("spo2", cursor.getString(spo2Index));
            }
            if (timestampIndex != -1) {
                lastData.put("timestamp", cursor.getString(timestampIndex));
            }
            cursor.close();
        } else {
            lastData.put("spo2", "N/A");
            lastData.put("timestamp", "N/A");
        }

        query = "SELECT * FROM heart_rate WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 1";
        cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int heartRateIndex = cursor.getColumnIndex("heart_rate");
            int timestampIndex = cursor.getColumnIndex("timestamp");

            if (heartRateIndex != -1) {
                lastData.put("heart_rate", cursor.getString(heartRateIndex));
            }
            if (timestampIndex != -1) {
                lastData.put("timestamp_hr", cursor.getString(timestampIndex));
            }
            cursor.close();
        } else {
            lastData.put("heart_rate", "N/A");
            lastData.put("timestamp_hr", "N/A");
        }

        query = "SELECT * FROM resp_rate WHERE user_id = " + user_id + " ORDER BY id DESC LIMIT 1";
        cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int respRateIndex = cursor.getColumnIndex("resp_rate");
            int timestampIndex = cursor.getColumnIndex("timestamp");

            if (respRateIndex != -1) {
                lastData.put("resp_rate", cursor.getString(respRateIndex));
            }
            if (timestampIndex != -1) {
                lastData.put("timestamp_resp", cursor.getString(timestampIndex));
            }
            cursor.close();
        } else {
            lastData.put("resp_rate", "N/A");
            lastData.put("timestamp_resp", "N/A");
        }
        return lastData;
    }

}