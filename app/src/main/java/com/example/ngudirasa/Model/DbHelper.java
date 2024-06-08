package com.example.ngudirasa.Model;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ngudi_rasa_db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_NAME_PRODUK = "Produk";
    public static final String TABLE_NAME_HISTORY = "History";
    public static final String TABLE_NAME_USER = "User";

    // Columns for Produk table
    public static final String COLUMN_ID_PRODUK = "id_produk";
    public static final String COLUMN_NAMA_PRODUK = "nama_produk";
    public static final String COLUMN_HARGA_PREMIUM = "harga_premium";
    public static final String COLUMN_HARGA_EKONOMIS = "harga_ekonomis";
    public static final String COLUMN_KATEGORI = "kategori";
    public static final String COLUMN_DESKRIPSI = "deskripsi";

    public static final String COLUMN_NOMOR = "nomor";
    public static final String COLUMN_KUANTITAS = "kuantitas";
    public static final String COLUMN_TIPE_HARGA = "tipe_harga";
    public static final String COLUMN_TANGGAL = "tanggal";

    public static final String COLUMN_TOTAL = "total";


    public static final String COLUMN_ID_USER = "id_user";

    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAMA_USER = "nama_user";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_VALID = "valid";
    // Columns for History table


    // SQL statements to create tables
    private static final String CREATE_TABLE_PRODUK =
            "CREATE TABLE " + TABLE_NAME_PRODUK + "(" +
                    COLUMN_ID_PRODUK + " TEXT PRIMARY KEY," +
                    COLUMN_NAMA_PRODUK + " TEXT," +
                    COLUMN_HARGA_PREMIUM + " TEXT," +
                    COLUMN_HARGA_EKONOMIS + " TEXT," +
                    COLUMN_KATEGORI + " TEXT," +
                    COLUMN_DESKRIPSI + " TEXT" +
                    ")";


    private static final String CREATE_TABLE_HISTORY =
            "CREATE TABLE " + TABLE_NAME_HISTORY + "(" +
                    COLUMN_NOMOR + " TEXT PRIMARY KEY," +
                    COLUMN_ID_PRODUK + " TEXT," +
                    COLUMN_KUANTITAS + " TEXT," +
                    COLUMN_TIPE_HARGA + " TEXT," +
                    COLUMN_TANGGAL + " DATE," +
                    COLUMN_NAMA_PRODUK + " TEXT," +
                    COLUMN_TOTAL + " TEXT" +
                    ")";

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_NAME_USER + "(" +
                    COLUMN_ID_USER + " TEXT PRIMARY KEY," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_NAMA_USER + " TEXT," +
                    COLUMN_PASSWORD + " TEXT," +
                    COLUMN_VALID + " TEXT" +
                    ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Produk and History tables
        db.execSQL(CREATE_TABLE_PRODUK);
        db.execSQL(CREATE_TABLE_HISTORY);
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade if needed
    }
}
