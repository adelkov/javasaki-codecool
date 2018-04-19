$(document).ready(function () {
    var cartLabel = document.getElementById("cartLabel");
    var cartButton = document.getElementById("shopping-cart");

    if (cartLabel.getAttribute("data") === "0") {
        cartLabel.style.visibility = "hidden";
        cartButton.style.visibility = "hidden";
    } else {
        cartLabel.style.visibility = "visible";
        cartButton.style.visibility = "visible";
    }
});
