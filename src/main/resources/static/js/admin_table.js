$(document).ready(function () {
  $(".sortable").on("click", function () {
    const sortBy = $(this).data("sort-by");
    const $table = $(this).closest("table");
    const $rows = $table.find("tbody tr").get();

    const alreadyAscending = $(this).hasClass("asc");

    $rows.sort(function (a, b) {
       const aValue = $(a).find("td:eq(" + $(this).index() + ")").text();
       const bValue = $(b).find("td:eq(" + $(this).index() + ")").text();

          if (sortBy === "dateUpload" || sortBy === "dateApproved") {
              return alreadyAscending ? compareDates(aValue, bValue) : compareDates(bValue, aValue);
              } else if (sortBy === "status") {
                  return sortStatus(aValue, bValue);
              } else {
                    return alreadyAscending ? naturalSort(aValue, bValue) : naturalSort(bValue, aValue);
              }
          });

            function compareDates(date1, date2) {
                return new Date(date1) - new Date(date2);
            }

            function sortStatus(aValue, bValue) {
                const statusOrder = { "No": -1, "Yes": 1 };
                return statusOrder[aValue] - statusOrder[bValue];
            }

            function naturalSort(aValue, bValue) {
                const collator = new Intl.Collator(undefined, { numeric: true, sensitivity: 'base' });
                return collator.compare(aValue, bValue);
            }

            if (alreadyAscending) {
                $(this).removeClass("asc");
                $rows.reverse();
            } else {
                $(this).addClass("asc");
            }

            $table.children("tbody").empty();
            $.each($rows, function (index, row) {
                $table.children("tbody").append(row);
            });
        });
    });