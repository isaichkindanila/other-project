<#-- @ftlvariable name="error" type="boolean" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<#import "/spring.ftl" as spring>
<@H.html>
    <@H.head "page.signIn.title">
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/createKey.js"></script>
    </@H.head>
    <@H.body>
        <h1 id="error">
            <#if error>
                <@spring.message "page.signIn.error"/>
            </#if>
        </h1>
        <form id="signInForm" action="/signIn" method="post">
            <input id="email" type="email" name="email" placeholder="<@spring.message "auth.email"/>">
            <input id="frontPassword" type="password" placeholder="<@spring.message "auth.password"/>">
            <input id="password" type="hidden" name="password">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="button" value="<@spring.message "page.signIn.form.button"/>" onclick="doSignIn()">
        </form>

        <script>
            const error = document.getElementById("error");
            const email = document.getElementById("email");
            const frontPassword = document.getElementById("frontPassword");
            const password = document.getElementById("password");
            const form = document.getElementById("signInForm");

            function doSignIn() {
                switch (true) {
                    case email.value.length === 0:
                        error.innerHTML = "<@spring.message "auth.email.missing"/>";
                        break;

                    case frontPassword.value.length === 0:
                        error.innerHTML = "<@spring.message "auth.password.missing"/>";
                        break;

                    default:
                        localStorage.setItem("key", getKey(email.value, frontPassword.value));
                        password.value = getHash(email.value, frontPassword.value);

                        form.submit();
                }
            }
        </script>
    </@H.body>
</@H.html>