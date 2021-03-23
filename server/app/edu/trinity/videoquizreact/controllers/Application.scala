package edu.trinity.videoquizreact.controllers

import javax.inject._

import edu.trinity.videoquizreact.shared.SharedMessages
import play.api.mvc._
import models.AppInMemoryModel

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def login = Action {
    Ok(views.html.login())
  }

  def userColor(username:String, color:String) = Action {
    
    Ok(views.html.usercolor(username, color))  
    
  }

  /*def loginPost = Action { request =>
    val queries = request.body.asFormUrlEncoded
    queries.map { args =>
      val username = args("username").head
      //val password = args("password").head
      if (AppInMemoryModel.validateUser(username))
      Ok(views.html.userpost(username))
      else Ok(Redirect(views.html.login))
    }.getOrElse(Ok("Messed up"))
  }*/

  def validateLoginPost = Action { implicit request =>
    val postvals = request.body.asFormUrlEncoded
    postvals.map { args =>
      val username = args("username").head
      val password = args("password").head
      println(s"$username and $password")
      if (AppInMemoryModel.validateUser(username, password)) {
        Redirect(routes.Application.userPost()).withSession("username" -> username)
      } else {
        Redirect(routes.Application.login()).flashing("error" -> "Invalid username/password combination.")
      }
    }.getOrElse(Redirect(routes.Application.login())).flashing("error" -> "something weird happened.")
  }

  def userPost = Action { implicit request => 
    val maybeUser = request.session.get("username")
    maybeUser.map { username =>
      Ok(views.html.userpost(username))
    }.getOrElse(Redirect(routes.Application.login()))
  }

  def logout = Action {
    Redirect(routes.Application.login()).withNewSession
  }

}
