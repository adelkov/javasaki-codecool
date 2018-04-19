$(document).ready(function () {
    var cartButton = document.getElementById("shopping-cart");

    if (sessionStorage.getItem("cartList")) {
        cartButton.style.visibility = "visible";
    } else {
        cartButton.style.visibility = "hidden";
    }
});
