document.getElementById("chatForm").addEventListener("submit", function(e) {
e.preventDefault();

const input = document.getElementById("messageInput");
const message = input.value;

fetch("/events/chat/" + eventId + "/send", {
    method: "POST",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "message=" + encodeURIComponent(message)
}).then(() => {
    input.value = "";
    loadMessages();
});
});


function loadMessages() {
 fetch("/events/chat/" + eventId + "/messages")
     .then(res => res.json())
     .then(data => {
         const container = document.getElementById("chat");

         container.innerHTML = "";

         data.forEach(msg => {
             container.innerHTML += renderMessage(msg);
         });

         container.scrollTop = container.scrollHeight;
     });
}
function renderMessage(msg) {
 return `
    <div class="chat-message">
        <span class="chat-user">${msg.senderUsername}</span>
        <span class="chat-text">${msg.message}</span>
    </div>
`;
}
loadMessages();
setInterval(loadMessages, 2000);