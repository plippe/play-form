package com.github.plippe.play.form.util

import play.api.mvc.RequestHeader

trait RequestHeaderSyntax {
  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit final def comGithubPlippePlayFormUtilRequestHeader(
      requestHeader: RequestHeader
  ): RequestHeaderOps = new RequestHeaderOps(requestHeader)
}

final class RequestHeaderOps(private val self: RequestHeader) extends AnyVal {
  def fromBrowserCompatibleRequestHeader(): RequestHeader =
    self.queryString
      .get(MethodQueryArgumentName)
      .flatMap(_.headOption)
      .fold(self)(self.withMethod)
}
