// Selecciona todos los botones "Add Cart"
const addCartButtons = document.querySelectorAll('.btn-add');
const cartCount = document.getElementById('cartCount');
const contentProducts = document.getElementById('contentProducts');
const totalSpan = document.getElementById('total');

// Array para almacenar los productos en el carrito
let cart = [];

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
}

// Escucha los clics en los botones "Add Cart"
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

// Inicializa el carrito vacío
renderCart();