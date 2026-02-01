// Selecciona todos los botones "Add Cart"
const addCartButtons = document.querySelectorAll('.btn-add');
const cartCount = document.getElementById('cartCount');
const contentProducts = document.getElementById('contentProducts');
const totalSpan = document.getElementById('total');

// Array para almacenar los productos en el carrito
let cart = [];

// Cargar carrito desde localStorage al iniciar
try {
    cart = JSON.parse(localStorage.getItem('cart')) || [];
} catch (e) { cart = []; }

// Función para guardar el carrito en localStorage
function saveCart() {
    localStorage.setItem('cart', JSON.stringify(cart));
}

// Función para renderizar el carrito
function renderCart() {
    contentProducts.innerHTML = '';
    let total = 0;
    cart.forEach(product => {
        total += product.price * product.quantity;
        contentProducts.innerHTML += `
            <tr>
                <td><img src="${product.img}" alt=""></td>
                <td>${product.name}</td>
                <td>$${product.price}</td>
                <td>
                    <input type="number" min="1" value="${product.quantity}" data-id="${product.id}" class="cart-qty">
                </td>
                <td>
                    <button type="button" class="remove-btn" data-id="${product.id}">X</button>
                </td>
            </tr>
        `;
    });
    cartCount.textContent = cart.reduce((acc, p) => acc + p.quantity, 0);
    totalSpan.textContent = `$${total}`;
    saveCart();
}

// Escucha los clics en los botones "Add Cart"
function addCartButtonListeners() {
    const addCartButtons = document.querySelectorAll('.btn-add');
    addCartButtons.forEach(btn => {
        btn.addEventListener('click', e => {
            const productDiv = e.target.closest('.product');
            const id = btn.getAttribute('data-id');
            const name = productDiv.querySelector('h4').textContent;
            const img = productDiv.querySelector('img').src;
            const price = parseFloat(productDiv.querySelector('#currentPrice').textContent.replace('$', ''));
            const existing = cart.find(p => p.id === id);
            if (existing) {
                existing.quantity += 1;
            } else {
                cart.push({ id, name, img, price, quantity: 1 });
            }
            renderCart();
        });
    });
}

// Vaciar carrito
document.getElementById('emptyCart').addEventListener('click', () => {
    cart = [];
    renderCart();
});

// Eliminar producto individual
contentProducts.addEventListener('click', e => {
    if (e.target.classList.contains('remove-btn')) {
        const id = e.target.getAttribute('data-id');
        cart = cart.filter(p => p.id !== id);
        renderCart();
    }
});

// Cambiar cantidad desde el input
contentProducts.addEventListener('input', e => {
    if (e.target.classList.contains('cart-qty')) {
        const id = e.target.getAttribute('data-id');
        const qty = parseInt(e.target.value);
        const product = cart.find(p => p.id === id);
        if (product && qty > 0) {
            product.quantity = qty;
            renderCart();
        }
    }
});

// Inicializa el carrito desde localStorage
renderCart();

// --- Cargar productos reales desde el backend y mostrarlos en el index ---
document.addEventListener('DOMContentLoaded', function() {
    const productsGrid = document.querySelector('.products-grid');
    if (!productsGrid) return;
    fetch('http://localhost:8081/api/productos/con-imagenes')
        .then(res => res.json())
        .then(productos => {
            productos.forEach((producto, idx) => {
                // Generar carrusel de imágenes
                const carouselId = `carouselProducto${producto.idProducto}`;
                let carouselInner = '';
                (producto.imagenes || []).forEach((img, i) => {
                    carouselInner += `
                        <div class="carousel-item${i === 0 ? ' active' : ''}">
                            <img src="data:image/jpeg;base64,${img}" class="d-block w-100" alt="...">
                        </div>
                    `;
                });
                let carouselControls = '';
                if ((producto.imagenes || []).length > 1) {
                    carouselControls = `
                        <button class="carousel-control-prev" type="button" data-bs-target="#${carouselId}" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#${carouselId}" data-bs-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </button>
                    `;
                }
                productsGrid.innerHTML += `
                <div class="product">
                    <div id="${carouselId}" class="carousel slide">
                        <div class="carousel-inner">
                            ${carouselInner}
                        </div>
                        ${carouselControls}
                    </div>
                    <div class="product-info">
                        <h4>${producto.nombre}</h4>
                        <div class="price">
                            <p id="currentPrice">$${producto.precio}</p>
                        </div>
                        <button type="button" onclick="window.location.href='productoDetallado.html?id=${producto.idProducto}'">Ver detalles</button>
                        <button class="btn-add" type="button" data-id="${producto.idProducto}">Añadir al carrito</button>
                    </div>
                </div>`;
            });
            addCartButtonListeners();
        });
});