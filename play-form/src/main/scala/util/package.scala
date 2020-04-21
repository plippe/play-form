package com.github.plippe.play.form

package object util {

  val MethodQueryArgumentName = "_method"

  object implicits extends CallSyntax with RequestHeaderSyntax

}
