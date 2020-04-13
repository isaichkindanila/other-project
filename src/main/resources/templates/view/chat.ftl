<#import "lib/html.ftl" as H>
<@H.html>
    <@H.head "Chat"/>
    <@H.body>
        <form>
            <input id="msg" type="text" placeholder="message">
            <input type="button" value="send" onclick="sendMessage()">
        </form>
        <hr>
        <ul id="chat"></ul>
    </@H.body>
    <script>
        const messageInput = document.getElementById("msg");
        const chat = document.getElementById("chat");

        function sendMessage() {
            const text = messageInput.value;
            if (text.length !== 0) {
                messageInput.value = "";

                const request = new XMLHttpRequest();
                request.open("POST", "/chat/messages");
                request.send(text);
            }
        }

        function escapeHtml(unsafe) {
            return unsafe
                .replace(/&/g, "&amp;")
                .replace(/</g, "&lt;")
                .replace(/>/g, "&gt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&#039;");
        }

        let lastId = 0;

        function handle(data) {
            <#--noinspection JSUnresolvedVariable-->
            lastId = data.lastId;
            const messages = data.messages;

            for (let i = 0; i < messages.length; i++) {
                const message = messages[i];
                const messageElement = "<li>" + escapeHtml(message.text) + "</li>";

                chat.innerHTML = messageElement + chat.innerHTML;
            }
        }

        function doPoll() {
            const request = new XMLHttpRequest();
            request.open("GET", "/chat/messages?after=" + lastId);

            request.onreadystatechange = (e) => {
                if (request.readyState !== 4) return;

                if (request.status === 200) {
                    handle(JSON.parse(request.response));
                }

                setTimeout(doPoll, 500);
            };

            request.send();
        }

        doPoll();
    </script>
</@H.html>