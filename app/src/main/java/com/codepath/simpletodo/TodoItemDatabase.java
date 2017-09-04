package com.codepath.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Shyam Sundar Ashok on 8/20/2017.
 */

public class TodoItemDatabase extends SQLiteOpenHelper {

    private static TodoItemDatabase sInstance;

    // Database Info
    private static final String DATABASE_NAME = "postsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODO = "todos";

    // Post Table Columns
    private static final String KEY_TODO_ID = "todoId";
    private static final String KEY_TODO_ITEM = "todoItem";

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_TODO_ITEM + " TEXT NOT NULL" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(sqLiteDatabase);
        }
    }


    // Get all posts in the database
    public List<ToDo> getAllToDos() {
        List<ToDo> todos = new ArrayList<>();

        // SELECT * FROM TODOS
        String TODOS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODO);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODOS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    ToDo todo = new ToDo();
                    todo.todoId = cursor.getInt(cursor.getColumnIndex(KEY_TODO_ID));
                    todo.todoItem = cursor.getString(cursor.getColumnIndex(KEY_TODO_ITEM));
                    todos.add(todo);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get todos from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return todos;
    }

    // Insert a post into the database
    public void addToDo(ToDo todo) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_ITEM, todo.todoItem);
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add todo to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateToDo(int todoIndex,String todoItem){
        // Update in DB where id match
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        // It's a good idea to wrap our delete in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        String TODOS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODO);
        List<Integer> database_ids = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(TODOS_SELECT_QUERY, null);
        try {
            while(cursor.moveToNext()){
                database_ids.add(Integer.parseInt(cursor.getString(0)));
            }
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_ITEM, todoItem);
            db.update(TABLE_TODO, values, KEY_TODO_ID + " = ?", new String[]{String.valueOf(database_ids.get(todoIndex))});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete todo from database");
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTodo(int todoIndex) {
        // Delete from DB where id match
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();
        // It's a good idea to wrap our delete in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        String TODOS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_TODO);
        List<Integer> database_ids = new ArrayList<Integer>();
        Cursor cursor = db.rawQuery(TODOS_SELECT_QUERY, null);
        try {
            while(cursor.moveToNext()){
                database_ids.add(Integer.parseInt(cursor.getString(0)));
            }
            db.delete(TABLE_TODO,KEY_TODO_ID + "=?",new String[]{String.valueOf(database_ids.get(todoIndex))});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete todo from database");
        } finally {
            db.endTransaction();
        }
    }


    public static synchronized TodoItemDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new TodoItemDatabase(context.getApplicationContext());
        }
        return sInstance;
    }
}
