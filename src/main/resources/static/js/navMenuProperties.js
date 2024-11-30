// Extract the current page from the URL path
    let currentPage = parseInt(location.pathname.split("/").pop(), 10) || 0;
    const totalPages = parseInt(document.getElementById("totalPages").textContent, 10);

    // Updates the URL path to include the new page number
    function changePage(page) {
        // Validate the page number to ensure it's a non-negative number
        if (isNaN(page) || page < 0) {
            console.error("Invalid page number. Page must be a positive number.");
            return; // Exit the function without doing anything
        }

        // Ensure the page number is within valid bounds
        if (page >= totalPages) page = totalPages - 1;

        // Get the current path and check if it contains "/properties"
        const currentPathParts = location.pathname.split("/");
        const isPropertiesPath = currentPathParts.includes("properties");

        // Reconstruct the path, ensuring "/properties" is preserved if present
        let newPath = isPropertiesPath
            ? `/properties/${page}${location.search}` // Keep "/properties" in the path
            : `${currentPathParts.slice(0, -1).join("/")}/${page}${location.search}`; // Default behavior

        location.href = newPath; // Navigate to the new path
    }

    // Handles manual input of page number
    function updatePageValue(event) {
        if (event.key === "Enter") {
            const inputPage = parseInt(event.target.value, 10) - 1; // Convert to zero-based
            // Validate the input page number
            if (isNaN(inputPage) || inputPage < 0) {
                alert("Please enter a valid positive page number.");
                event.target.value = currentPage + 1; // Reset the input field to the current page
                return;
            }
            changePage(inputPage);
        }
    }
    // Update the input field value on page load
    document.getElementById("currentPageInput").value = currentPage + 1; // Convert to one-based