package com.github.plippe.play.form.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.http.HttpVerbs._
import play.api.test.FakeRequest

class RequestHeaderOpsSpec extends AnyFlatSpec with Matchers {

  "RequestHeaderOps.fromBrowserCompatibleRequestHeader" should "return the same request" in {
    Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { method =>
      val requestHeader = FakeRequest(method, "https://example.com/")
      val newRequestHeader =
        new RequestHeaderOps(requestHeader).fromBrowserCompatibleRequestHeader

      newRequestHeader shouldEqual requestHeader
    }
  }

  it should "update the method" in {
    Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { from =>
      Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { to =>
        val requestHeader = FakeRequest(
          from,
          s"https://example.com/?${MethodQueryArgumentName}=${to}"
        )
        val newRequestHeader =
          new RequestHeaderOps(requestHeader).fromBrowserCompatibleRequestHeader

        newRequestHeader.method shouldEqual to
      }
    }
  }

}
