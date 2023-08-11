    function handleSortChange() {
        var selectedSort = document.getElementById("sort").value;
        var url = new URL(window.location.href);
        url.searchParams.set("sort", selectedSort);
        window.location.href = url.toString();
    }