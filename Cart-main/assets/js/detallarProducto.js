// Lista de productos de ejemplo
const productos = [
    {
        id: "1",
        nombre: "Teclado",
        descripcion: "Teclado mecánico retroiluminado, ideal para gaming y trabajo de oficina.",
        precio: "$18.000",
        imagenes: [
            "/assets/img/products/product-01.jpg",
            "/assets/img/products/product-01b.jpg",
            "/assets/img/products/product-01c.png"
        ],
        vendedor: "Jose Vega"
    },
    {
        id: "2",
        nombre: "Audífonos",
        descripcion: "Audífonos inalámbricos con cancelación de ruido.",
        precio: "$18.000",
        imagenes: [
            "/assets/img/products/product-02.jpg",
            "/assets/img/products/product-02b.jpg"
        ],
        vendedor: "Jose Vega"
    },
    {
        id: "3",
        nombre: "Mouse",
        descripcion: "Mouse ergonómico con sensor óptico de alta precisión.",
        precio: "$18.000",
        imagenes: [
            "/assets/img/products/product-03.jpg",
            "/assets/img/products/product-03b.jpg",
            "/assets/img/products/product-03c.jpg"
        ],
        vendedor: "Jose Vega"
    },
    {
        id: "4",
        nombre: "Reloj",
        descripcion: "Reloj inteligente con monitor de ritmo cardíaco.",
        precio: "$18.000",
        imagenes: [
            "/assets/img/products/product-04.jpg",
            "/assets/img/products/product-04b.jpg"
        ],
        vendedor: "Jose Vega"
    },
    {
        id: "5",
        nombre: "Router",
        descripcion: "Router WiFi de alta velocidad para toda la casa.",
        precio: "$18.000",
        imagenes: [
            "/assets/img/products/product-05a.jpg",
            "/assets/img/products/product-05b.jpg"
        ],
        vendedor: "Jose Vega"
    }
];

// Obtener el id de la URL
const params = new URLSearchParams(window.location.search);
const id = params.get('id');
let producto = productos.find(p => p.id === id);

function renderDetalles(producto) {
    // Galería de miniaturas
    document.querySelector('.gallery-thumbs').innerHTML = (producto.imagenes || []).map((img, i) =>
        `<img src="${img}" alt="Miniatura ${i+1}" class="${i===0?'active':''}" onclick="showImage(this)">`
    ).join('');
    // Imagen principal
    document.getElementById('mainImage').src = producto.imagenes && producto.imagenes[0] ? producto.imagenes[0] : '';
    // Detalles
    document.querySelector('.product-details h2').textContent = producto.nombre;
    document.querySelector('.product-details p').textContent = producto.descripcion;
    document.querySelector('.product-details h4').textContent = producto.precio ? (typeof producto.precio === 'string' ? producto.precio : `$${producto.precio}`) : '';
    document.querySelector('.product-details .vendedor').textContent = producto.vendedor ? `Vendedor: ${producto.vendedor}` : '';
}

if (!producto) {
    // Si no está en los de ejemplo, buscar en el backend
    fetch(`http://localhost:8081/api/productos/con-imagenes`)
        .then(res => res.json())
        .then(productosBD => {
            const prod = productosBD.find(p => String(p.idProducto) === id);
            if (!prod) {
                document.body.innerHTML = "<div class='container mt-5'><h2>Producto no encontrado</h2></div>";
            } else {
                // Adaptar formato de imágenes base64
                prod.imagenes = (prod.imagenes || []).map(img => `data:image/jpeg;base64,${img}`);
                renderDetalles(prod);
            }
        });
} else {
    renderDetalles(producto);
}

// Función para cambiar la imagen principal
function showImage(thumb) {
    document.getElementById('mainImage').src = thumb.src;
    document.querySelectorAll('.gallery-thumbs img').forEach(img => img.classList.remove('active'));
    thumb.classList.add('active');
}

// --- Añadir al carrito para productos de ejemplo y reales ---
document.getElementById('AddToCart').addEventListener('click', function() {
    // Obtener datos del producto mostrado
    const nombre = document.querySelector('.product-details h2').textContent;
    const precioStr = document.querySelector('.product-details h4').textContent;
    const precio = parseFloat(precioStr.replace('$', '').replace('.', ''));
    const img = document.getElementById('mainImage').src;
    // Usar el id de la URL o del producto
    let prodId = id || (producto && producto.id) || (producto && producto.idProducto) || '';

    // Obtener carrito del localStorage
    let cart = [];
    try {
        cart = JSON.parse(localStorage.getItem('cart')) || [];
    } catch (e) {}
    let existing = cart.find(p => String(p.id) === String(prodId));
    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({ id: prodId, name: nombre, img, price: precio, quantity: 1 });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    // Actualizar contador si existe
    updateCartCount();
    this.textContent = "¡Agregado!";
    setTimeout(() => this.textContent = "Añadir al carrito", 1000);
});

// Actualizar el contador del carrito al cargar la página
document.addEventListener('DOMContentLoaded', updateCartCount);

function updateCartCount() {
    const cartCount = document.getElementById('cartCount');
    let cart = [];
    try {
        cart = JSON.parse(localStorage.getItem('cart')) || [];
    } catch (e) {}
    if (cartCount) {
        cartCount.textContent = cart.reduce((acc, p) => acc + p.quantity, 0);
    }
}