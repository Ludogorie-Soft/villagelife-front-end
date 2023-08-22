      function myFunction() {

          var input = document.getElementById("myInput");
          var filter = input.value.toLowerCase();

          var villageContainer = document.getElementById("village-container");
          var villageItems = villageContainer.getElementsByClassName("village-item");

          var visibleVillageCount = 0;


          for (var i = 0; i < villageItems.length; i++) {
              var villageName = villageItems[i].getElementsByTagName("h6")[0];
              var nameText = villageName.textContent || villageName.innerText;

              if (nameText.toLowerCase().indexOf(filter) > -1) {

                  villageItems[i].style.display = "block";
                  visibleVillageCount++;
              } else {

                  villageItems[i].style.display = "none";
              }
          }


          if (visibleVillageCount === 0) {

          }
      }
