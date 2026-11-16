package cl.duoc.fotomotion.presentation.validation

object ValidadorFormulario {
    fun esTextoValido(texto: String): Boolean {
        return texto.isNotBlank()
    }

    fun validarFechaReal(fechaStr: String): Boolean {
        val regex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        if (!regex.matches(fechaStr)) return false
        val partes = fechaStr.split("-")
        val anio = partes[0].toIntOrNull() ?: return false
        val mes = partes[1].toIntOrNull() ?: return false
        val dia = partes[2].toIntOrNull() ?: return false
        if (mes !in 1..12) return false
        val diasMes = when (mes) {
            2 -> if (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
        return dia in 1..diasMes
    }
}
