var buttons = document.querySelectorAll("button");
var display = document.querySelector("#display");

var inputString = "";

for (var i = 0; i < buttons.length; i++) {
    var button = buttons[i];
    button.addEventListener("click", handleClick);
}

function handleClick (event) {
    var character = event.target.textContent;

    if (character === "=") {
        // Evaluates the string as if it was JavaScript
        // This can be dangerous
        var result = eval(inputString);
        renderDisplay(result);
        setInput(result);
    } else if (character === "C") {
        renderDisplay("");
        resetInput();
    } else {
        addToInput(character);
        renderDisplay(inputString);
    }
}

function renderDisplay (input) {
    display.textContent = input;
}

function setInput (input) {
    inputString = input;
}

function addToInput (character) {
    inputString = inputString + character;
}

function resetInput() {
    inputString = "";
}