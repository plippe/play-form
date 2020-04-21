package com.github.plippe.play.form

import javax.inject.{Inject, Singleton}
import play.api.http.{
  DefaultHttpRequestHandler => PlayDefaultHttpRequestHandler,
  HttpRequestHandler => PlayHttpRequestHandler
}
import play.api.mvc.{Handler, RequestHeader}

import com.github.plippe.play.form.util.implicits._

trait HttpRequestHandler extends PlayHttpRequestHandler {

  def underlying: PlayHttpRequestHandler

  def handlerForRequest(request: RequestHeader): (RequestHeader, Handler) = {
    val newRequest = request.fromBrowserCompatibleRequestHeader
    underlying.handlerForRequest(newRequest)
  }

}

@Singleton
class DefaultHttpRequestHandler @Inject() (
    val underlying: PlayDefaultHttpRequestHandler
) extends HttpRequestHandler
