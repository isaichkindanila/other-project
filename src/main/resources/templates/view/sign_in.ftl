<#-- @ftlvariable name="form" type="ru.itis.other.project.dto.auth.SignInDto" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<#import "/spring.ftl" as spring>
<@H.html>
    <@H.head "page.signIn.title">
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/createKey.js"></script>
    </@H.head>
    <@H.body>
        <@spring.bind "form"/>
        <form id="signInForm" action="/signIn" method="post">
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
            <input type="button" value="<@spring.message "page.signIn.form.button"/>" onclick="doSignIn()">
        </form>

        <script>
            const email = document.getElementById("email");
            const frontPassword = document.getElementById("frontPassword");
            const password = document.getElementById("password");
            const form = document.getElementById("signInForm");

            function doSignIn() {
                localStorage.setItem("key", getKey(email.value, frontPassword.value));
                password.value = getHash(email.value, frontPassword.value);

                form.submit();
            }
        </script>
    </@H.body>
</@H.html>