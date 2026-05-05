function getCsrfToken() {
    const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');
    return { token, header };
}

document.getElementById("chatForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const input = document.getElementById("messageInput");
    const message = input.value;
    const eventId = window.eventId;

    if (!message.trim()) return;

    const csrf = getCsrfToken();

    fetch("/events/chat/" + eventId + "/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            [csrf.header]: csrf.token
        },
        body: "message=" + encodeURIComponent(message)
    }).then(response => {
        if (response.ok) {
            input.value = "";
            loadMessages();
        } else {
            console.error('Failed to send message:', response.status);
        }
    }).catch(error => console.error('Error:', error));
});

function loadMessages() {
    const eventId = window.eventId;

    fetch("/events/chat/" + eventId + "/messages")
        .then(res => res.json())
        .then(data => {
            const container = document.getElementById("chat");
            container.innerHTML = "";

            data.forEach(msg => {
                container.innerHTML += renderMessage(msg);
            });

            container.scrollTop = container.scrollHeight;
        })
        .catch(error => console.error('Error loading messages:', error));
}

function renderMessage(msg) {
    return `
        <div class="chat-message">
            <span class="chat-user">${escapeHtml(msg.senderUsername)}</span>
            <span class="chat-text">${escapeHtml(msg.message)}</span>
        </div>
    `;
}

function escapeHtml(str) {
    if (!str) return '';
    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}


loadMessages();
setInterval(loadMessages, 2000);