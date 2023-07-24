       window.addEventListener('DOMContentLoaded', function () {
        const villageElements = document.querySelectorAll('.village');
        const loadMoreButton = document.getElementById('load-more');
        const cardCountElement = document.getElementById('card-count');
        const cardTotalElement = document.getElementById('card-total');

        const totalVillages = villageElements.length;
        let visibleVillages = Math.min(totalVillages, 6);

        function showVillages() {
          for (let i = 0; i < visibleVillages; i++) {
            if (villageElements[i]) {
              villageElements[i].style.display = 'block';
            }
          }
          cardCountElement.textContent = visibleVillages;
        }

        function loadMoreVillages() {
          visibleVillages += 6;
          if (visibleVillages > totalVillages) {
            visibleVillages = totalVillages;
            loadMoreButton.style.display = 'none';
          }
          showVillages();
        }

        showVillages();

        loadMoreButton.addEventListener('click', loadMoreVillages);
        cardTotalElement.textContent = totalVillages;
        });