<#-- @ftlvariable name="tokens" type="java.util.List<java.lang.String>" -->
<#-- @ftlvariable name="token" type="java.lang.String" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Loading...">
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/generateKey.js"></script>
    </@H.head>
    <@H.body>
        <script>
            const tokenList = [<#list tokens as token>"${token}",</#list>];

            const key = generateKey(tokenList);
            const form = document.createElement("form");

            form.action = "/files/${token}";
            form.method = "post";

            const tokenInput = document.createElement("input");
            tokenInput.name = "token";
            tokenInput.value = "${token}";

            const keyInput = document.createElement("input");
            keyInput.name = "key";
            keyInput.value = key;

            const csrfInput = document.createElement("input");
            csrfInput.name = "${_csrf.parameterName}";
            csrfInput.value = "${_csrf.token}";

            form.append(tokenInput, keyInput, csrfInput);
            document.body.appendChild(form).submit();
        </script>
    </@H.body>
</@H.html>