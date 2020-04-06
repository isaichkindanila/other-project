<#-- @ftlvariable name="tokens" type="java.util.List<java.lang.String>" -->
<#-- @ftlvariable name="token" type="java.lang.String" -->
<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Loading...">
        <script src="/static/js/forge-sha256.min.js"></script>
    </@H.head>
    <@H.body>
        <script>
            const tokenList = [<#list tokens as token>"${token}",</#list>];
            const token = "${token}";

            let key = localStorage.getItem("key");

            if (key === null) {
                fetch("/signOut", {"method": "POST"});
            } else {
                for (let i = 0; i < tokenList.length; i++) {
                    key = forge_sha256(key + tokenList[i]);
                }

                fetch("/files/" + token, {
                    "method": "POST",
                    "headers": {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    "body": "token=${token}&key=" + key
                })
            }
        </script>
    </@H.body>
</@H.html>