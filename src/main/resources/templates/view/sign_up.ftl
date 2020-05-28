<#-- @ftlvariable name="form" type="ru.itis.other.project.dto.auth.SignUpDto" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<#import "/spring.ftl" as spring>
<@H.html>
    <@H.head "page.signUp.title">
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/createKey.js"></script>
    </@H.head>
    <@H.body>
        <@spring.bind "form"/>
        <form id="signUpForm" action="/signUp" method="post">
            <span><@spring.message "auth.email"/>: </span>
            <@spring.formInput "form.email" "" "email"/>
            <@spring.showErrors " && "/>
            <br><br>

            <span><@spring.message "auth.password"/>: </span>
            <input id="frontPassword" type="password">
            <@spring.formInput "form.password" "hidden" "password"/>
            <@spring.showErrors " && "/>
            <br><br>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="button" value="<@spring.message "page.signUp.form.button"/>" onclick="doSignUp()">
        </form>

        <script>
            const form = document.getElementById("signUpForm");
            const frontPassword = document.getElementById("frontPassword");
            const email = document.getElementById("email");
            const password = document.getElementById("password");

            function doSignUp() {
                password.value = getHash(email.value, frontPassword.value);
                form.submit();
            }
        </script>
    </@H.body>
</@H.html>