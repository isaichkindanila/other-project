<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Home"/>
    <@H.body>
        <h1>Hello, world! ${_csrf.token}</h1>
    </@H.body>
</@H.html>