function getHash(email, password) {
    if (typeof email !== "string" || typeof password !== "string") {
        throw new Error("email and passwords must be strings");
    }

    return forge_sha256(email + password);
}

function getKey(email, password) {
    if (typeof email !== "string" || typeof password !== "string") {
        throw new Error("email and passwords must be strings");
    }

    return forge_sha256(password + email);
}