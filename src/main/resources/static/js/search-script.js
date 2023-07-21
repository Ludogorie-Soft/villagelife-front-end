      function myFunction() {
        // Get the input value and convert it to lowercase
        var input = document.getElementById("myInput");
        var filter = input.value.toLowerCase();

        // Get the village container and village items
        var villageContainer = document.getElementById("village-container");
        var villageItems = villageContainer.getElementsByClassName("village-item");

        // Loop through all village items and hide those that don't match the search query
        for (var i = 0; i < villageItems.length; i++) {
          var villageName = villageItems[i].getElementsByTagName("h3")[1];
          var nameText = villageName.textContent || villageName.innerText;

          if (nameText.toLowerCase().indexOf(filter) > -1) {
            villageItems[i].style.display = "";
          } else {
            villageItems[i].style.display = "none";
          }
        }
      }