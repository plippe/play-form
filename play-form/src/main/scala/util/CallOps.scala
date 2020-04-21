package com.github.plippe.play.form.util

import play.api.mvc.Call
import play.api.http.HttpVerbs.{GET, POST}

trait CallSyntax {
  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit final def comGithubPlippePlayFormUtilCall(call: Call): CallOps =
    new CallOps(call)
}

final class CallOps(private val self: Call) extends AnyVal {
  import implicits._

  def toBrowserCompatibleCall(): Call =
    if (Set(GET, POST).exists(self.method.equalsIgnoreCase)) self
    else
      self
        .withQueryArgument(MethodQueryArgumentName, self.method)
        .withMethod(POST)

  def withMethod(method: String): Call =
    self.copy(method = method)

  def withQueryArgument(name: String, value: String): Call = {
    val querySeparator = if (self.url.contains('?')) "&" else "?"
    val urlWithMethod = s"${self.url}${querySeparator}${name}=${value}"

    self.copy(url = urlWithMethod)
  }
}
