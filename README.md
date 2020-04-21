# Play Form ![Scala CI](https://github.com/plippe/play-form/workflows/Scala%20CI/badge.svg) ![Maven Central](https://img.shields.io/maven-central/v/com.github.plippe/play-form_2.13)

> The Rails framework encourages RESTful design of your applications, which means you'll be making a lot of "PATCH", "PUT", and "DELETE" requests (besides "GET" and "POST"). However, most browsers don't support methods other than "GET" and "POST" when it comes to submitting forms.
>
> Rails works around this issue by emulating other methods over POST with a hidden input named "_method", which is set to reflect the desired method:
>
> ```
> form_with(url: search_path, method: "patch")
> ```
>
> Output:
>
> ```
> <form accept-charset="UTF-8" action="/search" data-remote="true" method="post">
>   <input name="_method" type="hidden" value="patch" />
>   <input name="authenticity_token" type="hidden" value="f755bb0ed134b76c432144748a6d4b7a7ddf2b71" />
>    ...
> </form>
> ```
>
> When parsing POSTed data, Rails will take into account the special _method parameter and act as if the HTTP method was the one specified inside it ("PATCH" in this example).
>
> [Ruby on Rails](https://guides.rubyonrails.org/form_helpers.html#how-do-forms-with-patch-put-or-delete-methods-work-questionmark)

Play doesn't offer this feature, but this module does.

## Getting Started
To get started you add `play-form` as a dependency in SBT:

```scala
libraryDependencies += "com.github.plippe" %% "play-form" % "2.8.x"
```

After that you need to configure the [request handler](https://www.playframework.com/documentation/2.8.x/ScalaHttpRequestHandlers) inside your application.conf:

```hocon
play.http.requestHandler = "com.github.plippe.play.form.RequestHandler"
```

## Usage
Use `@com.github.plippe.play.form.form` instead of `@helper.form` like the bellow example from [Play's documentation](https://www.playframework.com/documentation/2.8.x/ScalaForms#Showing-forms-in-a-view-template).

```diff
-@helper.form(action = routes.Application.userPost()) {
+@com.github.plippe.play.form.form(action = routes.Application.userPost()) {
  @helper.inputText(userForm("name"))
  @helper.inputText(userForm("age"))
}
```

## Versioning
Play Form supports several different versions of Play.

| Play Form version | Play version |
|-------------------|--------------|
| 2.8.x             | 2.8.x        |
| 2.7.x             | 2.7.x        |
| 2.6.x             | 2.6.x        |

## Import
To remove the need to write the full package name, you can import the form in your `build.sbt`:
```scala
TwirlKeys.templateImports += "com.github.plippe.play.form.form"
```

This would simplify the views.
```diff
-@helper.form(action = routes.Application.userPost()) {
+@form(action = routes.Application.userPost()) {
  @helper.inputText(userForm("name"))
  @helper.inputText(userForm("age"))
}
```

## Cross Site Request Forgery (CSRF)
> By default, Play will require a CSRF check when all of the following are true:
> - The request method is not GET, HEAD or OPTIONS.
> - The request has one or more Cookie or Authorization headers.
> - The CORS filter is not configured to trust the request’s origin.
>
> [Play's documentation](https://www.playframework.com/documentation/2.8.x/ScalaCsrf)

If you encounter `Unauthorized` errors, your request most likely fails the CSRF check. [Play's documentation](https://www.playframework.com/documentation/2.8.x/ScalaCsrf) offers a few solutions. I choose the `@helper.CSRF.formField` in the [example project](https://github.com/plippe/play-form/tree/master/example).
