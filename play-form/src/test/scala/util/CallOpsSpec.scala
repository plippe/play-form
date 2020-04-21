package com.github.plippe.play.form.util

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import play.api.mvc.Call
import play.api.http.HttpVerbs._

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
class CallOpsSpec extends AnyFlatSpec with Matchers {
  "CallOps.withMethod" should "update the method" in {
    Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { from =>
      Set(GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { to =>
        val call = new Call(from, "https://example.com/")
        val newCall = new CallOps(call).withMethod(to)

        newCall.method shouldEqual to
      }
    }
  }

  "CallOps.withQueryArgument" should "append a query argument" in {
    val call = new Call(GET, "https://example.com/")
    val newCall = new CallOps(call).withQueryArgument("foo", "bar")

    newCall.url should endWith("?foo=bar")
  }

  it should "use the proper query seperator" in {
    val call = new Call(GET, "https://example.com/?baz")
    val newCall = new CallOps(call).withQueryArgument("foo", "bar")

    newCall.url should endWith("&foo=bar")
  }

  "CallOps.toBrowserCompatibleCall" should "keep the call" in {
    Set(GET, POST).foreach { method =>
      val call = new Call(method, "https://example.com/")
      val newCall = new CallOps(call).toBrowserCompatibleCall

      newCall shouldEqual call
    }
  }

  it should "update the call" in {
    Set(PUT, PATCH, DELETE, HEAD, OPTIONS).foreach { method =>
      val call = new Call(method, "https://example.com/")
      val newCall = new CallOps(call).toBrowserCompatibleCall

      newCall.method shouldEqual POST
      newCall.url should endWith(s"${MethodQueryArgumentName}=${method}")
    }
  }
}
