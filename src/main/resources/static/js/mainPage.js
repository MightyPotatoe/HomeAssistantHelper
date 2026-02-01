function loadContent(url) {
    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.querySelector('.app-content').innerHTML = html;
        });
}

function copyCode(elementId) {
    const codeElement = document.getElementById(elementId);
    if (!codeElement) return;

    // Use innerText to preserve formatting exactly as displayed
    const code = codeElement.innerText;

    // Use the Clipboard API
    navigator.clipboard.writeText(code)
    .then(() => {
        alert("Code copied to clipboard!");
    })
    .catch(err => {
        console.error("Failed to copy code:", err);
    });
}