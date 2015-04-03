function mascara(telefone) {
	if (telefone.value.length == 0)
		telefone.value = '(' + telefone.value; // quando começamos a digitar, o
	// script irá inserir um
	// parênteses no começo do
	// campo.
	if (telefone.value.length == 3)
		telefone.value = telefone.value + ') '; // quando o campo já tiver 3
	// caracteres (um parênteses e 2
	// números) o script irá inserir
	// mais um parênteses, fechando
	// assim o código de área.

	if (telefone.value.length == 8)
		telefone.value = telefone.value + '-'; // quando o campo já tiver 8
	// caracteres, o script irá
	// inserir um tracinho, para
	// melhor visualização do
	// telefone.

}

/* Máscaras ER */
function mascara(o, f) {
	v_obj = o
	v_fun = f
	setTimeout("execmascara()", 1)
}
function execmascara() {
	v_obj.value = v_fun(v_obj.value)
}
function mtel(v) {
	v = v.replace(/\D/g, ""); // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/g, "($1) $2"); // Coloca parênteses em volta
	// dos dois primeiros dígitos
	v = v.replace(/(\d)(\d{4})$/, "$1-$2"); // Coloca hífen entre o quarto e o
	// quinto dígitos
	return v;
}

$(document).ready(function() {
    /* Relogio */
    $('.data_hora').jclock({
        format: '%d/%m/%Y %H:%M:%S'
    });
});