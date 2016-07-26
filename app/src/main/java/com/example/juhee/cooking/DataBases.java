package com.example.juhee.cooking;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by juhee on 2016. 7. 26..
 */
public final class DataBases {
    public static final class CreateDB implements BaseColumns{
        public static final String FOOD = "food";
        public static final String MATERIALS = "materials";
        public static final String _TABLENAME = "cook";
        public static final String _CREATE =
                "create table " + _TABLENAME+"("
                +_ID+" integer primary key autoincrement, "
                +FOOD+" text not null, "
                +MATERIALS + "text not null );";

    }
}
