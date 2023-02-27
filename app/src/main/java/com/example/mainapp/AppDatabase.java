package com.example.mainapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// 데이터베이스 클래스

@Database(entities = {Checklist.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ChecklistDao checklistDao();

    private static AppDatabase sInstance;


    /**
     * Migrate from:
     * version 1 - using the SQLiteDatabase API
     * to
     * version 2 - using Room
     */
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase a_database) {
//            // Create the new table
//            a_database.execSQL(
//                    "CREATE TABLE checklist_new (" +
//                            "_id INTEGER NOT NULL, " +
//                            "mainarea TEXT NOT NULL, " +
//                            "subarea TEXT NOT NULL, " +
//                            "detailarea TEXT, " +
//                            "list TEXT NOT NULL, " +
//                            "result TEXT NOT NULL, " +
//                            "edittext TEXT NOT NULL, " +
//                            "image INTEGER NOT NULL, " +
//                            "date TEXT NOT NULL, " +
//                            "PRIMARY KEY(_id))"
//            );
//
//            // Copy the data
//            a_database.execSQL(
//                    "INSERT INTO checklist_new (_id, mainarea, subarea, detailarea, list, result, edittext, image, date) " +
//                            "SELECT _id, mainarea, subarea, detailarea, list, result, edittext, image, date FROM checklist");
//
//            // Remove the old table
//            a_database.execSQL("DROP TABLE checklist");
//
//            // Change the table name to the correct one
//            a_database.execSQL("ALTER TABLE checklist_new RENAME TO checklist");
//        }
//    };

        public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "ICT 설비점검.db")
//                    .createFromAsset("ICT 설비점검.db")
//                    .addMigrations(MIGRATION_1_3)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return sInstance;
    }

//    static final Migration MIGRATION_1_3 = new Migration(1, 3) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // Add new columns
//            database.execSQL("ALTER TABLE checklist ADD COLUMN image INTEGER DEFAULT 0");
//            database.execSQL("ALTER TABLE checklist ADD COLUMN id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0");
//        }
//    };
}
