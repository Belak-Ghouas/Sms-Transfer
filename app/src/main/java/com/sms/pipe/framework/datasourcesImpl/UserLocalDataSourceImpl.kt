package com.sms.pipe.framework.datasourcesImpl

import com.sms.pipe.data.dao.UserDao
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.models.SlackTeamModel
import com.sms.pipe.data.models.UserEntity
import com.sms.pipe.data.models.UserModel

class UserLocalDataSourceImpl(private val userDao: UserDao): UserLocalDataSource {

    private var user: UserModel?=null

    override suspend fun save(user: UserModel) {
        this.user=user
        userDao.insert(user.toEntity())
    }

    override suspend fun getLoggedUser(): UserModel? {
        user?.let {
            return it
        }?: kotlin.run {
            return userDao.getLoggedUser()?.toModel()
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