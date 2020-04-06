<#-- @ftlvariable name="tokens" type="java.util.List<java.lang.String>" -->
<#-- @ftlvariable name="token" type="java.lang.String" -->
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
            const form = new HTMLFormElement();

            form.action = "/files/${token}";
            form.method = "post";

            const tokenInput = new HTMLInputElement();
            tokenInput.name = "token";
            tokenInput.value = "${token}";

            const keyInput = new HTMLInputElement();
            keyInput.name = "key";
            keyInput.value = key;

            form.append(tokenInput, keyInput);
            form.submit();
        </script>
    </@H.body>
</@H.html>