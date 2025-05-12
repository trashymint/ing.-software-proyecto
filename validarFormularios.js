const passwordInput = document.getElementById('inputPassword5');
const emailInput = document.getElementById('correo');
const nombreInput = document.getElementById('username');
const cedulaInput = document.getElementById('cedula');
const idInput = document.getElementById('dato');
const crearButton = document.getElementById('crear');
const rolSelect = document.getElementById('rol');

const contraV = document.getElementById('contra2');
const emailInputV = document.getElementById('correo2');
const nombreInputV = document.getElementById('username2');
const cedulaInputV = document.getElementById('cedula2');
const idInputV = document.getElementById('dato2');
const crearButtonV = document.getElementById('crear2');
const rolSelectV = document.getElementById('rol2');

function validateForm() {
    if (
        rolSelect.value !== 'Seleccione su tipo de perfil' &&
        passwordInput.value.trim().length >= 6 &&
        cedulaInput.value.trim() !== '' &&
        emailInput.value.trim() !== '' &&
        nombreInput.value.trim() !== '' &&
        idInput.value.trim() !== ''
    ) {
        crearButton.disabled = false;
    } else {
        crearButton.disabled = true;
    }
}

function validateFormVendedor() {
    if (
        rolSelectV.value !== 'Seleccione su tipo de perfil' &&
        contraV.value.trim().length >= 6 &&
        cedulaInputV.value.trim() !== '' &&
        emailInputV.value.trim() !== '' &&
        nombreInputV.value.trim() !== '' &&
        idInputV.value.trim() !== ''
    ) {
        crearButtonV.disabled = false;
    } else {
        crearButtonV.disabled = true;
    }
}

emailInput.addEventListener('input', validateForm);
passwordInput.addEventListener('input', validateForm);
nombreInput.addEventListener('input', validateForm);
cedulaInput.addEventListener('input', validateForm);
idInput.addEventListener('input', validateForm);
rolSelect.addEventListener('change', validateForm);

// Vendedor
emailInputV.addEventListener('input', validateFormVendedor);
contraV.addEventListener('input', validateFormVendedor);
nombreInputV.addEventListener('input', validateFormVendedor);
cedulaInputV.addEventListener('input', validateFormVendedor);
idInputV.addEventListener('input', validateFormVendedor);
rolSelectV.addEventListener('change', validateFormVendedor);