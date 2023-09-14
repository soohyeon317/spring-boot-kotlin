const privateChatStompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

privateChatStompClient.onConnect = (frame) => {
    setConnectedPrivateChat(true);
    console.log('Connected: ' + frame);
    privateChatStompClient.subscribe('/sub/private-room/MyBestFriend', (greeting) => {
        showPrivateChat(greeting.body);
    });
};

privateChatStompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

privateChatStompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnectedPrivateChat(connected) {
    $("#private-chat-connect").prop("disabled", connected);
    $("#private-chat-disconnect").prop("disabled", !connected);
    if (connected) {
        $("#private-chat-conversation").show();
    }
    else {
        $("#private-chat-conversation").hide();
    }
    $("#greetings").html("");
}

function connectPrivateChat() {
    privateChatStompClient.activate();
}

function disconnectPrivateChat() {
    privateChatStompClient.deactivate();
    setConnectedPrivateChat(false);
    console.log("Disconnected");
}

function sendPrivateChatMessage() {
    privateChatStompClient.publish({
        destination: "/pub/private-room",
        body: JSON.stringify({'roomId': 'MyBestFriend', 'content': $("#private-chat-msg").val()})
    });
}

function showPrivateChat(message) {
    $("#private-chat-messages").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#private-chat-connect" ).click(() => connectPrivateChat());
    $( "#private-chat-disconnect" ).click(() => disconnectPrivateChat());
    $( "#private-chat-send" ).click(() => sendPrivateChatMessage());
});
