$(function () {
    function validatePassword() {
        if (document.getElementById("new-password").value != document.getElementById("confirm-password").value) {
            document.getElementById("confirm-password").setCustomValidity("Hasła nie są takie same!");
        } else {
            document.getElementById("confirm-password").setCustomValidity('');
        }
    }
    document.getElementById("new-password").onchange = validatePassword;
    document.getElementById("confirm-password").onkeyup = validatePassword;
});