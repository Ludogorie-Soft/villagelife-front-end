      function myFunction() {
          // Get the input value and convert it to lowercase
          var input = document.getElementById("myInput");
          var filter = input.value.toLowerCase();

          // Get the village container and village items
          var villageContainer = document.getElementById("village-container");
          var villageItems = villageContainer.getElementsByClassName("village-item");

          var visibleVillageCount = 0; // Брой видими села

          // Loop through all village items and check if they match the search query
          for (var i = 0; i < villageItems.length; i++) {
              var villageName = villageItems[i].getElementsByTagName("h6")[0];
              var nameText = villageName.textContent || villageName.innerText;

              if (nameText.toLowerCase().indexOf(filter) > -1) {
                  // Ако селото отговаря на търсения критерий, показваме го
                  villageItems[i].style.display = "block";
                  visibleVillageCount++; // Увеличаваме брояча на видимите села
              } else {
                  // Ако селото не отговаря на търсения критерий, скриваме го
                  villageItems[i].style.display = "none";
              }
          }

          // Проверка дали няма видими села
          if (visibleVillageCount === 0) {
              // Ако няма видими села, може да покажем някакво съобщение, например:
              // document.getElementById("noResultsMessage").style.display = "block";
          }
      }
