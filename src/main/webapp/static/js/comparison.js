$( document ).ready(function() {
    let shownImage = "fas fa-angle-down";
    let hiddenImage = "fas fa-angle-up";
    let hideButton = document.getElementById("hide-and-seek");
    let snapshotHidden = false;

    let compareSnapshot = document.getElementById("compare-snapshot");
    let comparisonButton = document.getElementById("compare-button");
    let comparedItems = [];
    let checkboxes = document.getElementsByClassName("productCompare");

    function fillCompareSnapshot(items) {
        if (items.length > 0) {

            let prod1NameHTML = document.getElementById("prod-1-name");
            let prod1ImgHTML = document.getElementById("prod-1-img");
            let prod1SuppHTML = document.getElementById("prod-1-supp");

            let prod2NameHTML = document.getElementById("prod-2-name");
            let prod2ImgHTML = document.getElementById("prod-2-img");
            let prod2SuppHTML = document.getElementById("prod-2-supp");

            let item1ID = items[0].split('_')[1];
            if (item1ID != null) {
                $.ajax({
                    type: 'POST',
                    data: {'id': item1ID},
                    url: '/shop',
                    success: function (response) {
                        console.log("Got the data!");
                        prod1NameHTML.innerText = response.name;
                        prod1SuppHTML.innerText = response.supplier;
                        prod1ImgHTML.setAttribute('src', '/static/img/product_' + item1ID + '.jpg');
                    }
                });
            }

            if (items.length > 1) {
                let item2ID = items[1].split('_')[1];
                if (item2ID != null) {
                    $.ajax({
                        type: 'POST',
                        data: {'id': item2ID},
                        url: '/shop',
                        success: function (response) {
                            console.log("Got the data!");
                            prod2NameHTML.innerText = response.name;
                            prod2SuppHTML.innerText = response.supplier;
                            prod2ImgHTML.setAttribute('src', '/static/img/product_' + item2ID + '.jpg');
                        }
                    });
                }
            }
        }
    }

    function clearColumn(columnID) {
        document.getElementById("prod-"+ (columnID + 1) +"-name").innerText = "";
        document.getElementById("prod-"+ (columnID + 1) +"-img").setAttribute('src', '');
        document.getElementById("prod-"+ (columnID + 1) +"-supp").innerText = "";
    }

    for (let chBox of checkboxes) {
        chBox.checked = false;
        chBox.addEventListener("change", function() {

            let productID = this.name.split('_')[1];

            if (this.checked) {
                console.log("Add to comparison, ID: " + productID);
                comparedItems.push(this.name);
                fillCompareSnapshot(comparedItems);
            } else {
                clearColumn(comparedItems.indexOf(this.name));
                console.log("Remove from comparison, ID: " + productID);
                comparedItems.splice(comparedItems.indexOf(this.name), 1);
            }

            if (comparedItems.length > 0) {
                compareSnapshot.style.visibility = "visible";
            } else {
                clearColumn(0);
                clearColumn(1);
                compareSnapshot.style.visibility = "hidden";
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

    hideButton.addEventListener("click", function() {
        let itemsDiv = document.getElementById("items");
        if (snapshotHidden) {
            itemsDiv.style.visibility = "visible";
            compareSnapshot.style.top = "70%";
            comparisonButton.style.visibility = (comparedItems.length === 2) ? "visible" : "hidden";
            hideButton.classList.add("fa-angle-down");
            hideButton.classList.remove("fa-angle-up");
            snapshotHidden = false;
        } else {
            itemsDiv.style.visibility = "hidden";
            compareSnapshot.style.top = "90%";
            comparisonButton.style.visibility = "hidden";
            hideButton.classList.remove("fa-angle-down");
            hideButton.classList.add("fa-angle-up");
            snapshotHidden = true;
        }

    });
});
