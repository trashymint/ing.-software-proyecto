document.getElementById('productImages').addEventListener('change', function(event) {
    const gallery = document.getElementById('gallery');
    gallery.innerHTML = '';
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.className = 'img-thumbnail';
                img.style.maxWidth = '120px';
                img.style.maxHeight = '120px';
                gallery.appendChild(img);
            };
            reader.readAsDataURL(file);
        }
    }
});