$(document).ready(function () {


    var rows = document.getElementsByClassName('item_row');

    for (var row of rows) {
        row.firstChild.nextSibling.style.visibility = "hidden";
    }

    for (var row of rows) {
        row.addEventListener('mouseover', function () {
            this.firstChild.nextSibling.style.visibility = "visible";
            this.style.backgroundColor = "#f8f8f8";
        });
        row.addEventListener('mouseout', function () {
            this.firstChild.nextSibling.style.visibility = "hidden";
            this.style.backgroundColor = "white";
        });

    }
});