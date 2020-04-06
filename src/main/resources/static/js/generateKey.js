function signOut() {
    const form = new HTMLFormElement();

    form.action = "/signOut";
    form.method = "post";

    form.submit();
}

if (localStorage.getItem("key") === null) {
    signOut();
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