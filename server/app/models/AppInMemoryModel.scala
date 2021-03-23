package models

import collection.mutable

object AppInMemoryModel {
    //private val users = mutable.Map[String, Seq[String]]("user" -> "pass") 
    private val users = mutable.Map[String, String]("mlewis" -> "prof", "web" -> "apps", "user" -> "pass")
    /*private val friends = {

    }*/

    def validateUser(username: String, password:String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)
    }
}
