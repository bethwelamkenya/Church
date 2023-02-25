package com.bethwelamkenya.church.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.bethwelamkenya.church.models.Admin
import com.bethwelamkenya.church.models.Attendance
import com.bethwelamkenya.church.models.Date
import com.bethwelamkenya.church.models.Member
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class DatabaseAdapter (context: Context) : SQLiteOpenHelper(context, database_name, null, database_version) {

    companion object{
        private const val database_name = "church.db"
        private const val database_version = 1
        private const val member_table = "member"
        private const val admin_table = "admin"
        private const val attendance_table = "attendance"
        private const val date_table = "date"
        private const val id = "id"
        private const val name = "name"
        private const val email = "email"
        private const val regNo = "reg_no"
        private const val number = "number"
        private const val school = "school"
        private const val year = "year"
        private const val department = "department"
        private const val residence = "residence"
        private const val date = "date"
        private const val attendance_id = "attendance_id"
        private const val user_name = "username"
        private const val password = "password"
        private const val security = "security"
        private const val answer = "answer"
        private const val status = "status"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createMemberTable = ("create table $member_table ($id integer primary key autoincrement, " +
                "$name text not null, " +
                "$email text, " +
                "$regNo text," +
                "$number integer, " +
                "$school text, " +
                "$year integer, " +
                "$department text, " +
                "$residence text)")
        val createAdminTable = ("create table $admin_table ($id integer primary key autoincrement, " +
                "$name text not null, " +
                "$email text, " +
                "$number integer not null, " +
                "$user_name text not null, " +
                "$password integer not null, " +
                "$security text not null, " +
                "$answer text not null)")
        val createAttendanceTable = ("create table $attendance_table ($attendance_id integer primary key autoincrement, " +
                "$id integer not null, " +
                "$name text not null, " +
                "$residence text, " +
                "$date text not null, " +
                "$status integer not null)")
        val createDateTable = ("create table $date_table ($id integer primary key autoincrement, " +
                "$date text not null)")
        db?.execSQL(createMemberTable)
        db?.execSQL(createAdminTable)
        db?.execSQL(createAttendanceTable)
        db?.execSQL(createDateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists $member_table")
        db.execSQL("drop table if exists $admin_table")
        db.execSQL("drop table if exists $attendance_table")
        db.execSQL("drop table if exists $date_table")
        onCreate(db)
    }

    fun insertMember(member: Member) : Long{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(name, member.name)
        contentValue.put(email, member.email)
        contentValue.put(regNo, member.regNo)
        contentValue.put(number, member.number)
        contentValue.put(school, member.school)
        contentValue.put(year, member.year)
        contentValue.put(department, member.department)
        contentValue.put(residence, member.residence)
        val result = db.insert(member_table, null, contentValue)
        db.close()
        return result
    }

    fun insertAdmin(admin: Admin) : Long{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(name, admin.name)
        contentValue.put(email, admin.email)
        contentValue.put(number, admin.number)
        contentValue.put(user_name, admin.userName)
        contentValue.put(password, sha256(admin.password))
        contentValue.put(security, admin.security)
        contentValue.put(answer, admin.answer)
        val result = db.insert(admin_table, null, contentValue)
        db.close()
        return result
    }

    fun insertAttendance(attendance: Attendance) : Long{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(id, attendance.id)
        contentValue.put(name, attendance.name)
        contentValue.put(residence, attendance.residence)
        contentValue.put(date, attendance.date)
        contentValue.put(status, attendance.status)
        val result = db.insert(attendance_table, null, contentValue)
        db.close()
        return result
    }

    fun insertMember(theDate: String) : Long{
        val db = this.writableDatabase
        val contentValue = ContentValues()
        contentValue.put(date, theDate)
        val result = db.insert(date_table, null, contentValue)
        db.close()
        return result
    }

    fun updateMember(member: Member) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(id, member.id)
        contentValues.put(name, member.name)
        contentValues.put(email, member.email)
        contentValues.put(number, member.number)
        contentValues.put(regNo, member.regNo)
        contentValues.put(school, member.school)
        contentValues.put(year, member.year)
        contentValues.put(department, member.department)
        contentValues.put(residence, member.residence)
        val result = db.update(member_table, contentValues, "$id=" + member.id, null)
        db.close()
        return result
    }

    fun updateAdmin(admin: Admin) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(id, admin.id)
        contentValues.put(name, admin.name)
        contentValues.put(email, admin.email)
        contentValues.put(number, admin.number)
        contentValues.put(user_name, admin.userName)
        contentValues.put(password, sha256(admin.password))
        contentValues.put(security, admin.security)
        contentValues.put(answer, admin.answer)
        val result = db.update(admin_table, contentValues, "$id=" + admin.id, null)
        db.close()
        return result
    }

    fun updateAttendance(attendance: Attendance) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(attendance_id, attendance.attendanceId)
        contentValues.put(id, attendance.id)
        contentValues.put(name, attendance.name)
        contentValues.put(residence, attendance.residence)
        contentValues.put(date, attendance.date)
        contentValues.put(status, attendance.status)
        val result = db.update(attendance_table, contentValues, "$attendance_id=" + attendance.attendanceId, null)
        db.close()
        return result
    }

    @SuppressLint("Range")
    fun getMembers(): ArrayList<Member> {
        val members: ArrayList<Member> = ArrayList()
        val query = "select * from $member_table"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            members.add(Member(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(name)),
                cursor.getString(cursor.getColumnIndex(email)),
                cursor.getString(cursor.getColumnIndex(regNo)),
                cursor.getLong(cursor.getColumnIndex(number)),
                cursor.getString(cursor.getColumnIndex(school)),
                cursor.getInt(cursor.getColumnIndex(year)),
                cursor.getString(cursor.getColumnIndex(department)),
                cursor.getString(cursor.getColumnIndex(residence))
            )
            )
        }
        cursor.close()
        return members
    }

    @SuppressLint("Range")
    fun getMember(theName: String): ArrayList<Member> {
        val members = ArrayList<Member>()
//        val member: Member
        val query = "select * from $member_table where $name like ?"
        val db = this.readableDatabase
        val cursor: Cursor?
//        val context = applicationContext
        try {
            cursor = db.rawQuery(query, arrayOf(theName))
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        try {
            while (cursor.moveToNext()){
                members.add(Member(cursor.getLong(cursor.getColumnIndex(id)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(email)),
                    cursor.getString(cursor.getColumnIndex(regNo)),
                    cursor.getLong(cursor.getColumnIndex(number)),
                    cursor.getString(cursor.getColumnIndex(school)),
                    cursor.getInt(cursor.getColumnIndex(year)),
                    cursor.getString(cursor.getColumnIndex(department)),
                    cursor.getString(cursor.getColumnIndex(residence)))
                )
            }
        } catch (e: Exception){
            println(e.message)
            return ArrayList()
        }
        cursor.close()
        return members
    }

    @SuppressLint("Range")
    fun getAdmins(): ArrayList<Admin> {
        val admins: ArrayList<Admin> = ArrayList()
        val query = "select * from $admin_table"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            admins.add(Admin(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(name)),
                cursor.getString(cursor.getColumnIndex(email)),
                cursor.getLong(cursor.getColumnIndex(number)),
                cursor.getString(cursor.getColumnIndex(user_name)),
                cursor.getString(cursor.getColumnIndex(password)),
                cursor.getString(cursor.getColumnIndex(security)),
                cursor.getString(cursor.getColumnIndex(answer))
            )
            )
        }
        cursor.close()
        return admins
    }

    @SuppressLint("Range")
    fun getAdmin(userName: String): Admin? {
        val admin: Admin
        val query = "select * from $admin_table where $user_name like ?"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, arrayOf(userName))
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return null
        }
        try {
            admin = Admin(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(name)),
                cursor.getString(cursor.getColumnIndex(email)),
                cursor.getLong(cursor.getColumnIndex(number)),
                cursor.getString(cursor.getColumnIndex(user_name)),
                cursor.getString(cursor.getColumnIndex(password)),
                cursor.getString(cursor.getColumnIndex(security)),
                cursor.getString(cursor.getColumnIndex(answer))
            )
        } catch (e: SQLiteException){
            println(e.message)
            return null
        }
        cursor.close()
        return admin
    }

    @SuppressLint("Range")
    fun getAdmin(userName: String, thePassword: String): Admin? {
        val admin: Admin
        val arrays = arrayOf(userName, sha256(thePassword))
        val query = "select * from $admin_table where $user_name like ? and $password=?"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, arrays)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return null
        }
        try {
            admin = Admin(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(name)),
                cursor.getString(cursor.getColumnIndex(email)),
                cursor.getLong(cursor.getColumnIndex(number)),
                cursor.getString(cursor.getColumnIndex(user_name)),
                cursor.getString(cursor.getColumnIndex(password)),
                cursor.getString(cursor.getColumnIndex(security)),
                cursor.getString(cursor.getColumnIndex(answer))
            )
        } catch (e: SQLiteException){
            println(e.message)
            return null
        }
        cursor.close()
        return admin
    }

    @SuppressLint("Range")
    fun getAttendances(): ArrayList<Attendance> {
        val attendances: ArrayList<Attendance> = ArrayList()
        val query = "select * from $attendance_table"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            attendances.add(
                Attendance(cursor.getLong(cursor.getColumnIndex(attendance_id)),
                    cursor.getLong(cursor.getColumnIndex(id)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(residence)),
                    cursor.getString(cursor.getColumnIndex(date)),
                    cursor.getInt(cursor.getColumnIndex(status))
                )
            )
        }
        cursor.close()
        return attendances
    }

    @SuppressLint("Range")
    fun getAttendances(theDate: String): ArrayList<Attendance> {
        val attendances: ArrayList<Attendance> = ArrayList()
        val query = "select * from $attendance_table where $date=$theDate"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            attendances.add(
                Attendance(cursor.getLong(cursor.getColumnIndex(attendance_id)),
                    cursor.getLong(cursor.getColumnIndex(id)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(residence)),
                    cursor.getString(cursor.getColumnIndex(date)),
                    cursor.getInt(cursor.getColumnIndex(status))
                )
            )
        }
        cursor.close()
        return attendances
    }

    @SuppressLint("Range")
    fun getAttendancesByName(theName: String): ArrayList<Attendance> {
        val attendances: ArrayList<Attendance> = ArrayList()
        val query = "select * from $attendance_table where $name=$theName"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            attendances.add(
                Attendance(cursor.getLong(cursor.getColumnIndex(attendance_id)),
                    cursor.getLong(cursor.getColumnIndex(id)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(residence)),
                    cursor.getString(cursor.getColumnIndex(date)),
                    cursor.getInt(cursor.getColumnIndex(status))
                )
            )
        }
        cursor.close()
        return attendances
    }

    @SuppressLint("Range")
    fun getDates(): ArrayList<Date> {
        val dates: ArrayList<Date> = ArrayList()
        val query = "select * from $date_table"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            dates.add(Date(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(date))
            )
            )
        }
        cursor.close()
        return dates
    }

    @SuppressLint("Range")
    fun getDates(theDate: String): ArrayList<Date> {
        val dates: ArrayList<Date> = ArrayList()
        val query = "select * from $date_table where $date=?"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, arrayOf(theDate))
        } catch (ex: SQLiteException){
            println(ex.message)
            db.execSQL(query)
            return ArrayList()
        }
        while (cursor.moveToNext()){
            dates.add(Date(cursor.getLong(cursor.getColumnIndex(id)),
                cursor.getString(cursor.getColumnIndex(date))
            )
            )
        }
        cursor.close()
        return dates
    }

    fun deleteMember(theId: Long) : Int{
        val db = this.writableDatabase
        val result = db.delete(member_table, "$id=$theId", null)
        db.close()
        return result
    }

    fun deleteAdmin(theId: Long) : Int{
        val db = this.writableDatabase
        val result = db.delete(admin_table, "$id=$theId", null)
        db.close()
        return result
    }

    private fun sha256(input: String): String? {
        return try {
            val bytes = input.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            digest.joinToString("") { String.format("%02x", it) }
        } catch (e: NoSuchAlgorithmException) {
            println(e)
            null
        }
    }
}