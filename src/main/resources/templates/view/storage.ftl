<#-- @ftlvariable name="dir" type="ru.itis.other.project.dto.storage.DirectoryDto" -->
<#-- @ftlvariable name="dirToken" type="java.lang.String" -->
<#-- @ftlvariable name="fileToken" type="java.lang.String" -->
<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#import "lib/html.ftl" as H>
<#assign isRoot = !(dir.self??)/>
<@H.html>
    <@H.head isRoot?then("Storage", dir.self.name)>
        <script src="/static/js/forge-sha256.min.js"></script>
        <script src="/static/js/generateKey.js"></script>
    </@H.head>
    <@H.body>
        <script>
            if (localStorage.getItem("key") === null) {
                signOut();
            }
        </script>

        <form id="dirForm" action="/storage${isRoot?then("", "/" + dir.self.token)}" method="post">
            <input type="text" name="name" placeholder="directory name">
            <input type="hidden" name="token" value="${dirToken}">
            <input type="hidden" name="key" value="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="button" value="create directory" onclick="doRequest(dirForm, dirTokens)">
        </form>
        <br>
        <form id="fileForm" action="/files" method="post" enctype="multipart/form-data">
            <input type="file" name="file">
            <input type="hidden" name="fileToken" value="${fileToken}">
            <#if !isRoot><input type="hidden" name="parentToken" value="${dir.self.token}"></#if>
            <input type="hidden" name="key" value="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
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
                <a href="/storage/${directory.token}">&gt; ${directory.name}</a>
                <br>
            </#items>
            <br>
        </#list>
        <#list dir.files as file>
            <a href="/files/${file.token}" target="_blank">* ${file.name}</a>
            <br>
        </#list>
        <script>
            const tokenList = [<#list dir.tokenList as token>"${token}",</#list>];

            const dirForm = document.getElementById("dirForm");
            const dirTokens = tokenList.slice();
            dirTokens.push("${dirToken}");

            const fileForm = document.getElementById("fileForm");
            const fileTokens = tokenList.slice();
            fileTokens.push("${fileToken}");

            function doRequest(form, tokens) {
                const keyInput = form.children.namedItem("key");

                if (keyInput === null) {
                    throw new Error("cannot find 'key' input on form");
                }

                keyInput.value = generateKey(tokens);
                form.submit();
            }
        </script>
    </@H.body>
</@H.html>