const searchInput = document.getElementById('searchFaculty');
const facultyList = document.getElementById('facultyList');
const facultyItems = facultyList.getElementsByTagName('li');

searchInput.addEventListener('input', () => {
    const filter = searchInput.value.toLowerCase();
    for (let i = 0; i < facultyItems.length; i++) {
        const text = facultyItems[i].textContent.toLowerCase();
        facultyItems[i].style.display = text.includes(filter) ? '' : 'none';
    }
});

facultyList.addEventListener('click', (event) => {
    if (event.target.tagName === 'LI') {
        const selectedFaculty = event.target.textContent;
        const facultyButton = document.querySelector('[data-bs-target="#facultyModal"]');
        facultyButton.textContent = selectedFaculty;
        facultyButton.classList.add('btn-success');
        facultyButton.classList.remove('btn-outline-primary');
        // Close the modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('facultyModal'));
        modal.hide();
    }
});