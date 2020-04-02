<#-- @ftlvariable name="error" type="boolean" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Sign up"/>
    <@H.body>
        <#if error>
            <h1>Email is already taken</h1>
        </#if>
        <form action="/signUp" method="post">
            <input type="email" name="email" placeholder="email">
            <input type="password" name="password" placeholder="password">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="submit" value="sign up">
        </form>
    </@H.body>
</@H.html>