<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<#import "/spring.ftl" as spring>
<@H.html>
    <@H.head "page.home.title"/>
    <@H.body>
        <h1><code>Hello, world!</code> ${_csrf.token}</h1>
    </@H.body>
</@H.html>