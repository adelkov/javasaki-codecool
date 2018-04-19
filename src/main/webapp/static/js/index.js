$(document).ready(function () {
    var cartLabel = document.getElementById("cartLabel");

    if (cartLabel.getAttribute("data") === "0") {
        cartLabel.style.visibility = "hidden";
    } else {
        cartLabel.style.visibility = "visible";
    }
});
