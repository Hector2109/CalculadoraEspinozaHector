package espinoza.hector.calculadora_espinozahector

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var resultado: String = ""
    var calculado: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Declaración de los elementos que se encuentran en la vista
        val tvCalculo: TextView = findViewById(R.id.tvCalculo)
        val btnNine: Button = findViewById(R.id.btnNine)
        val btnEight: Button = findViewById(R.id.btnEight)
        val btnSeven: Button = findViewById(R.id.btnSeven)
        val btnSix: Button = findViewById(R.id.btnSix)
        val btnFive: Button = findViewById(R.id.btnFive)
        val btnFour: Button = findViewById(R.id.btnFour)
        val btnThree: Button = findViewById(R.id.btnThree)
        val btnTwo: Button = findViewById(R.id.btnTwo)
        val btnOne: Button = findViewById(R.id.btnOne)
        val btnDecimal: Button = findViewById(R.id.btnDecimal)
        val btnZero: Button = findViewById(R.id.btnZero)
        val btnPlus: Button = findViewById(R.id.btnPlus)
        val btnSum: Button = findViewById(R.id.btnSum)
        val btnRes: Button = findViewById(R.id.btnRes)
        val btnDiv: Button = findViewById(R.id.btnDiv)
        val btnAc: Button = findViewById(R.id.btnAc)
        val btnEqual: Button = findViewById(R.id.btnEqual)

        //Eventos de los botones
        btnNine.setOnClickListener(){
            escribirEnResultado("9")
            tvCalculo.setText(resultado)
        }

        btnEight.setOnClickListener(){
            escribirEnResultado("8")
            tvCalculo.setText(resultado)
        }

        btnSeven.setOnClickListener(){
            escribirEnResultado("7")
            tvCalculo.setText(resultado)
        }

        btnSix.setOnClickListener(){
            escribirEnResultado("6")
            tvCalculo.setText(resultado)
        }

        btnFive.setOnClickListener(){
            escribirEnResultado("5")
            tvCalculo.setText(resultado)
        }

        btnFour.setOnClickListener(){
            escribirEnResultado("4")
            tvCalculo.setText(resultado)
        }

        btnThree.setOnClickListener(){
            escribirEnResultado("3")
            tvCalculo.setText(resultado)
        }

        btnTwo.setOnClickListener(){
            escribirEnResultado("2")
            tvCalculo.setText(resultado)
        }

        btnOne.setOnClickListener(){
            escribirEnResultado("1")
            tvCalculo.setText(resultado)
        }

        btnZero.setOnClickListener(){
            escribirEnResultado("0")
            tvCalculo.setText(resultado)
        }

        btnPlus.setOnClickListener(){
            escribirEnResultado("*")
            tvCalculo.setText(resultado)
        }

        btnSum.setOnClickListener(){
            escribirEnResultado("+")
            tvCalculo.setText(resultado)
        }

        btnDiv.setOnClickListener(){
            escribirEnResultado("/")
            tvCalculo.setText(resultado)
        }

        btnRes.setOnClickListener(){
            escribirEnResultado("-")
            tvCalculo.setText(resultado)
        }

        btnDecimal.setOnClickListener(){
            escribirEnResultado(".")
            tvCalculo.setText(resultado)
        }

        btnAc.setOnClickListener(){
            resultado = "0"
            tvCalculo.setText(resultado)
        }

        btnEqual.setOnClickListener(){
            calcularResultado()
            tvCalculo.setText(resultado)
        }


    }

    fun escribirEnResultado  (caracter: String) : String {

        if (calculado==true){
            resultado = "0"
            calculado = false
        }

        if (resultado=="0"){
            resultado = caracter
            return resultado
        }

        // Condición para evitar agregar más de un punto decimal
        if (resultado.lastOrNull() == '.' && caracter == ".") {
            return resultado
        }

        //Verifica no poner dos operandos de forma seguida
        //Nota: listOf me dio error, no se pudo solucionar, por ello opte por hacerlo de esta forma
        if ((resultado.isNotEmpty() && resultado.contains(Regex("[\\+\\-\\*/]"))
            && caracter.contains(Regex("[\\+\\-\\*/]"))) && resultado.last().toString().contains(Regex("[\\+\\-\\*/]"))) {

            resultado = resultado.dropLast(1) + caracter
            return resultado

        }



        //En caso de ser un numero
        resultado += caracter
        return resultado

    }

    /**
     * Función que calcula el resultado
     */
    fun calcularResultado(): String {

        if (!resultado.contains(Regex("[\\+\\-\\*/]"))) {
            return resultado
        }

        // Se hacen las operaciones división y multiplicación primero, de izquierda a derecha
        resultado = procesarOperaciones(resultado, "*")
        resultado = procesarOperaciones(resultado, "/")

        // Por ultimo las sumas y restas
        resultado = procesarOperaciones(resultado, "+")
        resultado = procesarOperaciones(resultado, "-")

        calculado = true //Lo use para indicar que ya se calculo un resultado
        return resultado
    }

    /**
     * Función para procesar las operaciones de la calculadora
     */
    fun procesarOperaciones(expresion: String, operador: String): String {
        // Corregimos los números antes de procesar la expresión
        var resultado = corregirNumeros(expresion)

        // Expresión regular para dividir la expresión
        val regex = Regex("(-?\\d+(?:\\.\\d+)?)\\s*\\${operador}\\s*(-?\\d+(?:\\.\\d+)?)")

        while (resultado.contains(regex)) {
            resultado = resultado.replace(regex) { match ->
                val operando1 = match.groupValues[1].toDouble()
                val operando2 = match.groupValues[2].toDouble()
                when (operador) {
                    "+" -> (operando1 + operando2).toString()
                    "-" -> (operando1 - operando2).toString()
                    "*" -> (operando1 * operando2).toString()
                    "/" -> if (operando2 != 0.0) (operando1 / operando2).toString() else "Error"
                    else -> match.value
                }
            }
        }
        return resultado
    }

    /**
     * Función para dar formato adecuado a los decimales en caso que esten escritos
     * de una forma que no se puedan procesar adecuadamente en una operacion
     */
    fun corregirNumeros(expresion: String): String {
        var resultado = expresion

        // Si hay un número como ".1", lo cambiamos a "0.1"
        resultado = resultado.replace(Regex("(?<=^|[\\+\\-\\*/\\(\\)])\\.(\\d+)"), "0.$1")

        // Si hay un número como "85.", lo cambiamos a "85.0"
        resultado = resultado.replace(Regex("(\\d+)\\.$"), "$1.0")

        return resultado
    }


}