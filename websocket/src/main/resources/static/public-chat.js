const publicChatStompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

publicChatStompClient.onConnect = (frame) => {
    setConnectedPublicChat(true);
    console.log('Connected: ' + frame);
    publicChatStompClient.subscribe('/sub/public-room', (greeting) => {
        showPublicChat(greeting.body);
    });
};

publicChatStompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

publicChatStompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnectedPublicChat(connected) {
    $("#public-chat-connect").prop("disabled", connected);
    $("#public-chat-disconnect").prop("disabled", !connected);
    if (connected) {
        $("#public-chat-conversation").show();
    }
    else {
        $("#public-chat-conversation").hide();
    }
    $("#greetings").html("");
}

function connectPublicChat() {
    publicChatStompClient.activate();
}

function disconnectPublicChat() {
    publicChatStompClient.deactivate();
    setConnectedPublicChat(false);
    console.log("Disconnected");
}

function sendPublicChatMessage() {
    publicChatStompClient.publish({
        destination: "/pub/public-room",
        body: JSON.stringify({'content': $("#public-chat-msg").val()})
    });
}

function showPublicChat(message) {
    $("#public-chat-messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#public-chat-connect" ).click(() => connectPublicChat());
    $( "#public-chat-disconnect" ).click(() => disconnectPublicChat());
    $( "#public-chat-send" ).click(() => sendPublicChatMessage());
});
