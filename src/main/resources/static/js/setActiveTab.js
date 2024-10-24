    document.querySelectorAll('.nav-link').forEach(tab => {
    tab.addEventListener('click', function() {
        localStorage.setItem('activeTab', this.id);
    });
});

window.onload = function() {
    const activeTab = localStorage.getItem('activeTab');
    if (activeTab) {
        const tabToActivate = document.getElementById(activeTab);
        if (tabToActivate) {
            tabToActivate.click();
        }
    }
};