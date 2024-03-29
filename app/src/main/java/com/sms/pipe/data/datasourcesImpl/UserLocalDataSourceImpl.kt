package com.sms.pipe.data.datasourcesImpl

import com.sms.pipe.data.db.dao.UserDao
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.models.SlackTeamModel
import com.sms.pipe.data.db.entity.UserEntity
import com.sms.pipe.data.models.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSourceImpl(private val userDao: UserDao): UserLocalDataSource {

    private var user: UserModel?=null

    override suspend fun save(user: UserModel) {
        this.user=user
        userDao.insert(user.toEntity())
    }

    override suspend fun getLoggedUser(): Flow<UserModel?> {
        return userDao.getLoggedUser().map {
            user = it?.toModel()
            user
        }
    }

    override suspend fun logout(): Boolean {
        return userDao.logout()>0
    }

    override suspend fun getUser(): UserModel? {
        user?.let {
            return it
        }?:run{
            user = userDao.getUser()?.toModel()
            return user
        }

    }

    override fun userSession(): UserModel? {
        return user
    }

    override suspend fun deleteAccount(token: String) {
        user?.toEntity()?.let {
            userDao.delete(it)
            user = null
        }
    }


    private fun UserModel.toEntity(): UserEntity {
       return UserEntity(
           email =this.email,
           username = this.username,
           slack_access_token = this.slack_access_token?:"",
           slack_app_id = this.slack_app_id?: "",
           slack_token_type = this.slack_token_type?:"",
           bot_user_id = this.bot_user_id?:"",
           teamId = this.slack_team?.teamId,
           teamName = this.slack_team?.teamName,
           logged = this.logged)
    }

    private fun UserEntity.toModel(): UserModel {
        return UserModel(
            email =this.email,
            username = this.username,
            slack_access_token = this.slack_access_token.ifEmpty { null },
            slack_app_id = this.slack_app_id.ifEmpty { null },
            slack_token_type = this.slack_token_type.ifEmpty { null },
            bot_user_id = this.bot_user_id.ifEmpty { null },
            slack_team = SlackTeamModel(teamName=this.teamName,teamId=this.teamId),
            logged = this.logged
        )
    }
}