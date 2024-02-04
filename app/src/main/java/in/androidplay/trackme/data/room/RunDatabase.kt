package `in`.androidplay.trackme.data.room

import `in`.androidplay.trackme.util.Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 12:23 AM
 */

@Database(entities = [Run::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class  RunDatabase : RoomDatabase() {

    abstract fun getRunDao(): RunDAO
}