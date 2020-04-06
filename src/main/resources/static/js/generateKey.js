function signOut() {
    const form = document.createElement("form");

    form.action = "/signOut";
    form.method = "post";

    document.body.appendChild(form).submit();
}

function generateKey(tokenList) {
    let key = localStorage.getItem("key");

    if (key === null) {
        signOut();
    } else {
        for (let i = 0; i < tokenList.length; i++) {
            key = forge_sha256(key + tokenList[i]);
        }

        return key;
    }
}