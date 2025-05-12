const vendedorRadio = document.getElementById('Vendedor');
const clienteRadio = document.getElementById('Cliente');
const vendedorContenido = document.getElementById('vendedorContenido');
const clienteContenido = document.getElementById('clienteContenido');

vendedorRadio.addEventListener('change', () => {
    if (vendedorRadio.checked) {
        vendedorContenido.style.display = 'block'; // Show Vendedor content
        clienteContenido.style.display = 'none';  // Hide Personal content
    }
});

clienteRadio.addEventListener('change', () => {
    if (clienteRadio.checked) {
        clienteContenido.style.display = 'block'; // Show Personal content
        vendedorContenido.style.display = 'none'; // Hide Vendedor content
    }
});