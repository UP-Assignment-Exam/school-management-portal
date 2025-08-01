// Toggle Sidebar
document.getElementById('toggle-sidebar')?.addEventListener('click', function () {
    const sidebar = document.getElementById('sidebar');
    const mainContent = document.getElementById('main-content');

    sidebar?.classList.toggle('collapsed');
    mainContent?.classList.toggle('expanded');

    // Mobile Sidebar Toggle
    if (window.innerWidth <= 768) {
        sidebar?.classList.toggle('show');
    }
});

// Toggle User Dropdown
function toggleUserDropdown() {
    const dropdown = document.getElementById('userDropdown');
    if (dropdown) {
        dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
    }
}
window.toggleUserDropdown = toggleUserDropdown; // Expose globally for inline HTML use

// Close dropdown when clicking outside
document.addEventListener('click', function (event) {
    const dropdown = document.getElementById('userDropdown');
    const userInfo = document.querySelector('.user-info');

    if (dropdown && userInfo && !userInfo.contains(event.target)) {
        dropdown.style.display = 'none';
    }
});

// Add hover effects to dropdown items
document.querySelectorAll('#userDropdown a').forEach(item => {
    item.addEventListener('mouseenter', function () {
        this.style.backgroundColor = '#f8f9fa';
    });
    item.addEventListener('mouseleave', function () {
        this.style.backgroundColor = 'transparent';
    });
});

// (Optional) Search functionality placeholder
// document.querySelector('.search-bar input')?.addEventListener('keypress', function(e) {
//     if (e.key === 'Enter') {
//         const searchTerm = this.value;
//         console.log('Searching for:', searchTerm);
//         // Implement search functionality here
//     }
// });
