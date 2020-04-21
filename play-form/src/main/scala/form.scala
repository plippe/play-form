package com.github.plippe.play.form

import play.api.mvc.Call
import play.twirl.api._
import play.twirl.api.HtmlFormat.Appendable

import com.github.plippe.play.form.util.implicits._

object form {
  def apply(action: Call, args: (Symbol, String)*)(
      body: => Html
  ): Appendable = {
    val newAction = action.toBrowserCompatibleCall
    views.html.helper.form(newAction, args: _*)(body)
  }
}
