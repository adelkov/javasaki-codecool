$( document ).ready(function() {
    let comparisonButton = document.getElementById("doComparison");
    let comparedItems = [];
    let checkboxes = document.getElementsByClassName("productCompare");
    for (let chBox of checkboxes) {
        chBox.checked = false;
        chBox.addEventListener("change", function() {

            let productID = this.name.split('_')[1];

            if (this.checked) {
                console.log("Add to comparison, ID: " + productID);
                comparedItems.push(this.name);
            } else {
                console.log("Remove from comparison, ID: " + productID);
                comparedItems.splice(comparedItems.indexOf(this.name), 1);
            }

            if (comparedItems.length > 2) {
                this.checked = false;
                comparedItems.splice(comparedItems.indexOf(this.name), 1);
                alert("You can compare only two items at once!");
            } else if (comparedItems.length === 2) {
                comparisonButton.style.visibility = "visible";
            } else {
                comparisonButton.style.visibility = "hidden";
            }
        })
    }

    comparisonButton.addEventListener("click", function() {
       window.location.replace("/compare?p1ID=" + comparedItems[0].split('_')[1] + '&p2ID=' + comparedItems[1].split('_')[1]);
    });
});
