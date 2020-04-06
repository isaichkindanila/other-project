<#-- @ftlvariable name="dir" type="ru.itis.other.project.dto.DirectoryDto" -->
<#-- @ftlvariable name="dirToken" type="java.lang.String" -->
<#-- @ftlvariable name="fileToken" type="java.lang.String" -->
<#import "lib/html.ftl" as H>
<#assign isRoot = dir.self??/>
<@H.html>
    <@H.head "${isRoot?then("", dir.self.name)}">
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/generateKey.js"></script>
    </@H.head>
    <@H.body>
        <script>
            const tokenList = [<#list dir.tokenList as token>"${token}",</#list>];

            const dirForm = document.getElementById("dirForm");
            const dirTokens = tokenList.slice().push("${dirToken}");

            const fileForm = document.getElementById("fileDorm");
            const fileTokens = tokenList.slice().push("${fileToken}");

            function doRequest(form, tokens) {
                const keyInput = form.children.namedItem("key");

                if (keyInput === null) {
                    throw new Error("cannot find 'key' input on form");
                }

                keyInput.value = generateKey(tokens);
                form.submit();
            }
        </script>

        <form id="dirForm" action="/storage${isRoot?then("", "/" + dir.self.token)}" method="post">
            <input type="text" name="name" placeholder="directory name">
            <input type="hidden" name="token" value="${dirToken}">
            <input type="hidden" name="key" value="">
            <input type="button" value="create directory" onclick="doRequest(dirForm, dirTokens)">
        </form>
        <br>
        <form id="fileForm" action="/files" method="post">
            <input type="file" name="file">
            <input type="hidden" name="fileToken" value="${fileToken}">
            <#if !isRoot><input type="hidden" name="parentToken" value="${dir.self.token}"></#if>
            <input type="hidden" name="key" value="">
            <input type="button" value="upload file" onclick="doRequest(fileForm, fileTokens)">
        </form>
        <hr>
        <#if !isRoot>
            <#if dir.parent??>
                <a href="/storage/${dir.parent.token}">..</a>
            <#else>
                <a href="/storage">..</a>
            </#if>
            <br>
            <br>
        </#if>

        <#list dir.directories>
            <#items as directory>
                <p><a href="/storage/${directory.token}">&gt; ${directory.name}</a></p>
            </#items>
            <br>
        </#list>

        <#list dir.files as file>
            <p><a href="/files/${file.token}">* ${file.name}</a></p>
        </#list>
    </@H.body>
</@H.html>