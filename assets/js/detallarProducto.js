// Lista de productos 
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
const producto = productos.find(p => p.id === id);

// Si no existe el producto, muestra mensaje
if (!producto) {
    document.body.innerHTML = "<div class='container mt-5'><h2>Producto no encontrado</h2></div>";
} else {
    // Rellena la galeria de miniaturas
    document.querySelector('.gallery-thumbs').innerHTML = producto.imagenes.map((img, i) =>
        `<img src="${img}" alt="Miniatura ${i+1}" class="${i===0?'active':''}" onclick="showImage(this)">`
    ).join('');
    // Imagen principal
    document.getElementById('mainImage').src = producto.imagenes[0];
    // Detalles
    document.querySelector('.product-details h2').textContent = producto.nombre;
    document.querySelector('.product-details p').textContent = producto.descripcion;
    document.querySelector('.product-details h4').textContent = producto.precio;
    document.querySelector('.product-details .vendedor').textContent = "Vendedor: " + producto.vendedor;
}

// Función para cambiar la imagen principal
function showImage(thumb) {
    document.getElementById('mainImage').src = thumb.src;
    document.querySelectorAll('.gallery-thumbs img').forEach(img => img.classList.remove('active'));
    thumb.classList.add('active');
}

document.getElementById('AddToCart').addEventListener('click', function() {
    const id = producto.id || "1"; // Usar id real del producto
    const name = producto.nombre || document.querySelector('.product-details h2').textContent;
    const img = producto.imagenes ? producto.imagenes[0] : document.getElementById('mainImage').src;
    const price = producto.precio ? parseFloat(producto.precio.replace('$', '').replace('.', '')) : 0;

    // Busca si ya esta en el carrito
    let existing = cart.find(p => p.id === id);
    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({ id, name, img, price, quantity: 1 });
    }
    renderCart();
    this.textContent = "¡Agregado!";
    setTimeout(() => this.textContent = "Añadir al carrito", 1000);
});