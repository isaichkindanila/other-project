<#-- @ftlvariable name="error" type="boolean" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Sign in"/>
    <@H.body>
        <h1>Hello, world! ${_csrf.token}</h1>
        <#if error>
            <h1>Bad credentials</h1>
        </#if>
        <form action="/signIn" method="post">
            <input type="email" name="email" placeholder="email">
            <input type="password" name="password" placeholder="password">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="sign in">
        </form>
    </@H.body>
</@H.html>