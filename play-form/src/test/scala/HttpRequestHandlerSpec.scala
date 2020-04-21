package com.github.plippe.play.form

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.http.{HttpRequestHandler => PlayHttpRequestHandler}
import play.api.http.HttpVerbs._
import play.api.mvc.{Handler, RequestHeader}
import play.api.test.FakeRequest

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
class HttpRequestHandlerSpec extends AnyFlatSpec with Matchers {
  "HttpRequestHandler" should "foward the call to the underlying handler" in {
    Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { method =>
      val requestHeader = FakeRequest(method, "https://example.com/")
      val playHttpRequestHandler = new PlayHttpRequestHandler {
        def handlerForRequest(
            request: RequestHeader
        ): (RequestHeader, Handler) = {
          request shouldEqual requestHeader
          (request, new Handler {})
        }
      }

      val httpRequestHandler = new HttpRequestHandler {
        def underlying = playHttpRequestHandler
      }
      httpRequestHandler.handlerForRequest(requestHeader)
    }
  }

  it should "update the call for the underlying handler" in {
    Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { from =>
      Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { to =>
        val requestHeader = FakeRequest(
          from,
          s"https://example.com/?${util.MethodQueryArgumentName}=${to}"
        )
        val playHttpRequestHandler = new PlayHttpRequestHandler {
          def handlerForRequest(
              request: RequestHeader
          ): (RequestHeader, Handler) = {
            request.method shouldEqual to
            (request, new Handler {})
          }
        }

        val httpRequestHandler = new HttpRequestHandler {
          def underlying = playHttpRequestHandler
        }
        httpRequestHandler.handlerForRequest(requestHeader)
      }
    }
  }

}
