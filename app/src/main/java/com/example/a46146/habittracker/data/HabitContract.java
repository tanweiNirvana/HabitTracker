package com.example.a46146.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by 46146 on 2017/3/31.
 */

public final class HabitContract {
    //An empty private constructor makes sure that the class is not going to be initialised.
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habit";
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_TIME = "time";
    }
}