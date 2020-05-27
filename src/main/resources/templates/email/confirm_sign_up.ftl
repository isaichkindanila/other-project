<#-- @ftlvariable name="serverURL" type="java.lang.String" -->
<#-- @ftlvariable name="token" type="java.lang.String" -->
<#import "/spring.ftl" as spring>
<html lang="en">
<head>
    <title><@spring.message "email.confirmSignUp.title"/></title>
</head>
<body>
    <a href="${serverURL}/signUp/confirm/${token}">
        <@spring.message "email.confirmSignUp.message"/>
    </a>
</body>
</html>