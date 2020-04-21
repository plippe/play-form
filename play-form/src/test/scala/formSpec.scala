package com.github.plippe.play.form

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.mvc.Call
import play.api.http.HttpVerbs._
import play.twirl.api.Html

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
class formSpec extends AnyFlatSpec with Matchers {
  "form" should "keep the call" in {
    Set(GET, POST).foreach { method =>
      val call = new Call(method, "https://example.com/")
      val body = form(call)(Html(None)).body

      body should include("""action="https://example.com/"""")
      body should include(s"""method="${method}"""")
    }
  }

  it should "replace the call with a browser compatible one" in {
    Set(PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { method =>
      val call = new Call(method, "https://example.com/")
      val body = form(call)(Html(None)).body

      body should include(
        s"""action="https://example.com/?${util.MethodQueryArgumentName}=${method}""""
      )
      body should include("""method="POST"""")
    }
  }
}
