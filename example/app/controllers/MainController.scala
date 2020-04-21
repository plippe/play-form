package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class Application @Inject()(val controllerComponents: MessagesControllerComponents) extends MessagesBaseController {

  def index() = Action { implicit request =>
    Ok(views.html.index(request))
  }

  def get = index
  def post = index
  def put = index
  def patch = index
  def delete = index
  def head = index
  def options = index

}
