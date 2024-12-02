document.addEventListener("DOMContentLoaded", function () {
  const chatBox = document.getElementById("chat-box");
  const userMessageInput = document.getElementById("user-message");
  const sendButton = document.getElementById("send-button");

  // 스크롤을 가장 아래로 이동하는 함수
  function scrollToBottom() {
    chatBox.scrollTop = chatBox.scrollHeight;
  }

  // 줄바꿈을 HTML에서 적용하도록 변환
  function formatMessage(text) {
    return text.replace(/\n/g, '<br>'); // \n을 <br>로 변환
  }

  // 메시지 추가 함수 수정
  function addMessage(sender, text) {
    const messageClass = sender === "me" ? "user-message" : "bot-message";
    const messageHtml = `
        <li class="${messageClass}">
            <p>${formatMessage(text)}</p>
        </li>`;
    chatBox.insertAdjacentHTML("beforeend", messageHtml);

    // 새 메시지 추가 후 스크롤 이동
    scrollToBottom();
  }

  // 메시지 전송 이벤트
  sendButton.addEventListener("click", function () {
    const userMessage = userMessageInput.value.trim();
    if (!userMessage) {
      return;
    }

    // 사용자 메시지 추가
    addMessage("me", userMessage);

    // 서버에 메시지 전송
    axios.post("/api/send-message", userMessage, {
      headers: {"Content-Type": "application/json"}
    })
    .then(response => {
      // 서버 응답 메시지 추가
      addMessage("bot", response.data);
    })
    .catch(error => {
      console.error("Error sending message:", error);
      addMessage("bot", "An error occurred. Please try again.");
    });

    // 입력 필드 비우기
    userMessageInput.value = "";
  });

  // 엔터 키로 메시지 전송
  userMessageInput.addEventListener("keypress", function (e) {
    if (e.key === "Enter") {
      sendButton.click();
    }
  });

  const closeButton = document.getElementById("close-chat");

  // [X] 버튼 클릭 시 이전 페이지로 이동
  closeButton.addEventListener("click", function () {
    window.history.back();  // 이전 페이지로 돌아가기
  });
});
