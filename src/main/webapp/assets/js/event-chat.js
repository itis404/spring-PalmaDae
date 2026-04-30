 document.getElementById("chatForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const message = document.getElementById("messageInput").value;

     fetch("/events/chat/" + eventId + "/send", {
    method: "POST",
    headers: {
    "Content-Type": "application/x-www-form-urlencoded"
},
    body: "message=" + encodeURIComponent(message)
}).then(() => {
    document.getElementById("messageInput").value = "";
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
                 container.innerHTML += `
                    <p><b>${msg.senderUsername}</b>: ${msg.message}</p>
                `;
             });
         });
 }
 setInterval(loadMessages, 2000);