package `in`.androidplay.trackme.repository

import `in`.androidplay.trackme.db.Run
import `in`.androidplay.trackme.db.RunDAO
import javax.inject.Inject

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/31/2020, 8:12 AM
 */
class MainRepository @Inject constructor(private val runDAO: RunDAO) {

    suspend fun insertRun(run: Run) = runDAO.insertRun(run)

    suspend fun deleteRun(run: Run) = runDAO.deleteRun(run)

    fun getAllRunsSortedByDate() = runDAO.getAllRunsSortedByDate()

    fun getAllRunsSortedByTimeInMillis() = runDAO.getAllRunsSortedByTimeInMillis()

    fun getAllRunsSortedByCaloriesBurnt() = runDAO.getAllRunsSortedByCaloriesBurnt()

    fun getAllRunsSortedByAvgSpeed() = runDAO.getAllRunsSortedByAvgSpeed()

    fun getAllRunsSortedByDistance() = runDAO.getAllRunsSortedByDistance()

    fun getTotalRunTimeInMillis() = runDAO.getTotalRunTimeInMillis()

    fun getTotalCaloriesBurnt() = runDAO.getTotalCaloriesBurnt()

    fun getTotalAvgSpeed() = runDAO.getTotalAvgSpeed()

    fun getTotalDistance() = runDAO.getTotalDistance()
}